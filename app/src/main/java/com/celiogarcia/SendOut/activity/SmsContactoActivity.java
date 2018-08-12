package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.SmsAdapter;
import com.celiogarcia.SendOut.dao.MensagemDao;
import com.celiogarcia.SendOut.modelo.Agenda;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.task.EnviaSmsTask;

import java.util.ArrayList;

import static com.celiogarcia.SendOut.activity.ContactosActivity.sdb;

public class SmsContactoActivity extends AppCompatActivity {

    private Context context;
    private EditText mensagem;
    private Contacto contacto;
    private ListView mensagensEnviadas;
    private MensagemDao dao;
    private Configuracao configuracao;
    private Agenda contactoSms;
    private Thread t;
    public static boolean refreshListaSmsContacto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_contacto);

        context = this;

        configuracao = new Configuracao(this);
        dao = new MensagemDao(sdb);
        contacto = new Contacto();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensagem = (EditText) findViewById(R.id.mensagem_contacto);
        mensagensEnviadas = (ListView) findViewById(R.id.mensagens_enviadas_ao_contacto);

        Intent intent = getIntent();
        contactoSms = (Agenda) intent.getSerializableExtra("contacto");

        if (contactoSms != null){
            getSupportActionBar().setTitle(contactoSms.getNome());
            getSupportActionBar().setSubtitle(contactoSms.addIndicativo(configuracao, contactoSms.getNumero()));
        }

        if (contactoSms.getNome() != null && contactoSms.getNumero() != null) {
            contacto.setNome(contactoSms.getNome());
            contacto.setNumeroDeTelefone(contactoSms.addIndicativo(configuracao, contactoSms.getNumero()));
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

                                if (refreshListaSmsContacto){
                                    verMensagensDoContacto(contacto);
                                    refreshListaSmsContacto = false;
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

        Button enviarMensagem = (Button) findViewById(R.id.enviar_mensagem_contacto);
        enviarMensagem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String sms;

                if (configuracao.isPessoal()){
                    sms = mensagem.getText().toString() + "\nAss. " + configuracao.pegaNumeroDoRemetente();
                }else{
                    sms = mensagem.getText().toString();
                }

                String numero = contacto.getNumeroDeTelefone();

                // FLAG Para não histórico 1;
                new EnviaSmsTask(context, numero, sms, 1).execute();
                verMensagensDoContacto(contacto);
                mensagem.setText("");
            }
        });
    }

    public void verMensagensDoContacto(Contacto contacto){
        ArrayList<Contacto> sms = dao.mensagens(contacto.getNumeroDeTelefone());
        sdb.close();

        if (sms == null){
            return;
        }
        SmsAdapter adapter = new SmsAdapter(this, sms);
        mensagensEnviadas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verMensagensDoContacto(contacto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sms_contactos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.make_call) {

            Intent intentLigar;
            if (configuracao.hasCallerIdRegistered()){
                intentLigar = new Intent(SmsContactoActivity.this, ChamadaActivity.class);
                intentLigar.putExtra("numeroParaChamar", contacto.getNumeroDeTelefone());
            }else{
                intentLigar = new Intent(context, IntroductionRegisteringCallerIdActivity.class);
            }
            startActivity(intentLigar);

        }else{
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        t.interrupt();
    }
}
