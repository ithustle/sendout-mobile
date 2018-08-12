package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.handler.MyHandler;
import com.celiogarcia.SendOut.helper.DataHelper;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.task.TempoDasChamadasTask;

import java.util.Timer;
import java.util.TimerTask;

import static com.celiogarcia.SendOut.activity.ChamadaActivity.activeConnection;
import static com.celiogarcia.SendOut.activity.ChamadaActivity.muteMicrophone;
import static com.celiogarcia.SendOut.activity.ChamadaActivity.speakerPhone;


public class ChamadaEmCurso extends Activity implements SensorEventListener {

    private static TextView tvTempo;
    private static Context context;
    public static ChamadaEmCurso chamada;

    private AudioManager myAudioManager;
    private TextView tvChamadaId;
    private static Configuracao configuracao;

    private Boolean mudo = true;
    public static boolean CHAMADA_ATENDIDA = false;
    private AsyncTask<Object, Object, String> tempo;
    private static Contacto contacto;
    private static TimerTask timerTask;
    public static Timer timer;
    private static String para;
    private static String dataHoje;
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private static Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada_em_curso);

        context = this;
        chamada = this;

        context.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        context.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        tvChamadaId = (TextView) findViewById(R.id.chamada_destinatario);
        tvTempo = (TextView) findViewById(R.id.chamada_tempo);
        configuracao = new Configuracao(this);
        contacto = new Contacto();
        timer = new Timer();
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        contacto.setNumeroDeTelefone(configuracao.pegaNumeroDoRemetente());

        chronometer = (Chronometer) findViewById(R.id.chronometer);

        Intent intent = getIntent();
        para = intent.getStringExtra("numeroParaChamar");
        tvChamadaId.setText(para);

        new MyHandler(this);        // O meu handler serve para fazer a ponte da Activity ChamadaEmCursoActivity e a classe do FCM

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        context.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        context.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));
    }

    public static void contagem(){
        iniciaTaxacao();
        timer.schedule(timerTask, 57000, 57000);
    }

    public static void contador(){
        chronometer.setVisibility(View.VISIBLE);
        tvTempo.setVisibility(View.GONE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public static void pararContagem(){
        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }

        //Permite taxar a chamada de forma pré-paga (Atencipadamente) depois de terminada a chamada
        if (extractTimeElapsed() < (1 + extractTimeElapsed())){
            if (configuracao.minutosDeVoz() != 0) {     //Executa apenas se o crédito for diferente de zero.
                new TempoDasChamadasTask(context, configuracao, contacto, para, dataHoje).execute();
            }
        }
    }

    public static void iniciaTaxacao(){
        dataHoje = String.valueOf(DataHelper.hoje());
        timerTask = new TimerTask(){
            @Override
            public void run() {
                new TempoDasChamadasTask(context, configuracao, contacto, para, dataHoje).execute();
            }
        };
    }

    public void speakerOn(View view) {

        speakerPhone = !speakerPhone;

        myAudioManager.setSpeakerphoneOn(speakerPhone);

        if (speakerPhone) {
            ((ImageView) view).setImageResource(0);
            ((ImageView) view).setImageResource(R.drawable.ic_high_volume_104);
        }else {
            ((ImageView) view).setImageResource(0);
            ((ImageView) view).setImageResource(R.drawable.ic_high_volume_96);
        }
    }

    public void desligarChamada(View view){
        ChamadaActivity.disconnect();
        wakeLock.release();
        wakeLock = null;
        finish();
    }

    public void mute(View view){
        muteMicrophone = !muteMicrophone;
        if (activeConnection != null) {
            activeConnection.setMuted(muteMicrophone);
        }
        if (muteMicrophone) {
            ((ImageView) view).setImageResource(0);
            ((ImageView) view).setImageResource(R.drawable.ic_no_microphone_filled_100);
        } else {
            ((ImageView) view).setImageResource(0);
            ((ImageView) view).setImageResource(R.drawable.ic_microphone_filled_100);
        }
    }

    public static int extractTimeElapsed(){
        long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        int hours = (int) (timeElapsed / 3600000);
        int minutes = (int) (timeElapsed - hours * 3600000) / 60000;

        return minutes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(this, RateActivity.class));
        if ((wakeLock != null) && (!wakeLock.isHeld())){
            wakeLock.acquire();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LUZ", "O que temos: " + wakeLock);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "FUMO");
            wakeLock.acquire();
        }

        Log.d("LUZ", "O que temos: " + wakeLock);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i("SENSOR", "Boutro");
    }

}
