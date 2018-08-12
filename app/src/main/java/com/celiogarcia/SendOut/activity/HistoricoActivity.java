package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.HistoricoAdapter;
import com.celiogarcia.SendOut.dao.MensagemDao;
import com.celiogarcia.SendOut.modelo.Contacto;

import java.util.ArrayList;

import static com.celiogarcia.SendOut.activity.ContactosActivity.sdb;

public class HistoricoActivity extends AppCompatActivity {

    private ListView listaDeHistorico;
    private ArrayList<Contacto> list;
    private HistoricoAdapter adapter;
    private MensagemDao dao;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new MensagemDao(sdb);

        list = dao.historicoDeSms();

        listaDeHistorico = (ListView) findViewById(R.id.lista_historico);
        adapter = new HistoricoAdapter(context, list);

        listaDeHistorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto agenda = (Contacto) listaDeHistorico.getItemAtPosition(position);

                Intent intentEnviarSms = new Intent(context, SmsHistorico.class);
                intentEnviarSms.putExtra("numero", agenda.getNumeroDeTelefone());
                startActivity(intentEnviarSms);
            }
        });

        listaDeHistorico.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
