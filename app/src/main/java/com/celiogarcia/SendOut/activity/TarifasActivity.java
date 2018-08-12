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
import com.celiogarcia.SendOut.adapter.TarifaAdapter;
import com.celiogarcia.SendOut.modelo.Tarifa;

public class TarifasActivity extends AppCompatActivity {

    private Context context;
    private ListView listaTarifas;
    private TarifaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        Tarifa tarifas = new Tarifa(context);

        listaTarifas = (ListView) findViewById(R.id.lista_tarifas);
        adapter = new TarifaAdapter(context, tarifas, false);
        listaTarifas.setAdapter(adapter);

        listaTarifas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3){
                    startActivity(new Intent(context, MaisDetalhesTarifaActivity.class));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
