package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.TarifaAdapter;
import com.celiogarcia.SendOut.modelo.Tarifa;

public class TarifasSmsActivity extends AppCompatActivity {

    private Context context;
    private ListView listaTarifas;
    private TarifaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifas_sms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        Tarifa tarifas = new Tarifa(context);

        listaTarifas = (ListView) findViewById(R.id.lista_tarifa_sms);
        adapter = new TarifaAdapter(context, tarifas, true);
        listaTarifas.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
