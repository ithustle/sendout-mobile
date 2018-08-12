package com.celiogarcia.SendOut.activity;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.PaisesAdapter;
import com.celiogarcia.SendOut.modelo.Pais;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {

    private ListView listaDePaises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Pais> paises = getPaises();

        listaDePaises = (ListView) findViewById(R.id.country_lista_de_paises);

        PaisesAdapter adapter = new PaisesAdapter(this, paises);
        listaDePaises.setAdapter(adapter);

        listaDePaises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Pais p = (Pais) listaDePaises.getItemAtPosition(i);

                Intent intentVoltarParaRegisto = new Intent(CountryActivity.this, RegisterActivity.class);
                intentVoltarParaRegisto.putExtra("pais", p);
                setResult(RESULT_OK, intentVoltarParaRegisto);
                finish();
            }
        });
    }

    private List<Pais> getPaises(){
        List<Pais> paises = new ArrayList<>();
        String json;
        try {
            InputStream inputStream = getAssets().open("paises.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("paises");

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jo_inside = jsonArray.getJSONObject(i);
                Pais pais = new Pais();
                pais.setIndicativo(jo_inside.getString("code"));
                pais.setNome(jo_inside.getString("name"));
                paises.add(pais);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return paises;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
