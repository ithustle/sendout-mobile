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
import com.celiogarcia.SendOut.adapter.HistoricoDeChamadaAdapter;
import com.celiogarcia.SendOut.dao.HistoricoDeChamadasDao;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.HistoricoChamadas;

import java.util.ArrayList;

import static com.celiogarcia.SendOut.activity.ContactosActivity.sdb;

public class HistoricoDeChamadas extends AppCompatActivity {

    private ListView lista_historico_chamada;
    private Context context;
    private HistoricoDeChamadaAdapter historicoAdapter;
    private HistoricoDeChamadasDao dao;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_de_chamadas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        configuracao = new Configuracao(context);

        carregaHistorico();

        lista_historico_chamada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HistoricoChamadas historicoChamadas = (HistoricoChamadas) parent.getItemAtPosition(position);
                Intent intentLigar = new Intent(HistoricoDeChamadas.this, ChamadaActivity.class);

                //Regista chamada no histórico
                ChamadaActivity.registaHistoricoDaChamada(historicoChamadas.getNumero());
                configuracao.ultimoNumeroChamado(historicoChamadas.getNumero());

                intentLigar.putExtra("numeroParaChamar", historicoChamadas.getNumero());
                startActivity(intentLigar);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaHistorico();
    }

    private void carregaHistorico(){
        lista_historico_chamada = (ListView) findViewById(R.id.lista_historico_chamada);
        dao = new HistoricoDeChamadasDao(sdb);

        ArrayList<HistoricoChamadas> chamadas = dao.todasAsChamadas();

        historicoAdapter = new HistoricoDeChamadaAdapter(context, chamadas);
        lista_historico_chamada.setAdapter(historicoAdapter);
    }
}
