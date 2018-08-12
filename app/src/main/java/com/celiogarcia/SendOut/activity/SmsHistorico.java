package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.SmsAdapter;
import com.celiogarcia.SendOut.dao.MensagemDao;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.task.EnviaSmsTask;

import java.util.ArrayList;

import static com.celiogarcia.SendOut.activity.ContactosActivity.sdb;

public class SmsHistorico extends AppCompatActivity {

    private Context context;
    private Configuracao configuracao;
    private MensagemDao dao;
    private Contacto c;
    private EditText smsTexto;
    public static ListView mensagensEnviadas;
    private String numeroHistorico;
    private Thread t;
    public static SmsAdapter adapter;
    public static boolean refreshListaSmsHistorico = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_historico);

        context = this;

        configuracao = new Configuracao(this);
        dao = new MensagemDao(sdb);
        c = new Contacto();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        smsTexto = (EditText) findViewById(R.id.sms_historico);
        mensagensEnviadas = (ListView) findViewById(R.id.sms_historico_enviadas_ao_contacto);

        Intent intent = getIntent();
        numeroHistorico = (String) intent.getSerializableExtra("numero");

        if (numeroHistorico != null) {
            getSupportActionBar().setTitle(numeroHistorico);
        }

        if (numeroHistorico != null) {
            c.setNumeroDeTelefone(numeroHistorico);
        }

        t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (refreshListaSmsHistorico){
                                    verMensagensDoContacto(numeroHistorico);
                                    refreshListaSmsHistorico = false;
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        Button enviarMensagem = (Button) findViewById(R.id.enviar_sms_historico);
        enviarMensagem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String sms;

                if (configuracao.isPessoal()) {
                    sms = smsTexto.getText().toString() + "\nAss. " + configuracao.pegaNumeroDoRemetente();
                }else{
                    sms = smsTexto.getText().toString();
                }

                // FLAG Para não histórico 1;
                new EnviaSmsTask(context, numeroHistorico, sms, 0).execute();
                smsTexto.setText("");
            }
        });
    }

    public void verMensagensDoContacto(String numero) {

        ArrayList<Contacto> sms = dao.mensagens(numero);
        sdb.close();

        if (sms == null) {
            return;
        }
        adapter = new SmsAdapter(this, sms);
        mensagensEnviadas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verMensagensDoContacto(numeroHistorico);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        t.interrupt();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}