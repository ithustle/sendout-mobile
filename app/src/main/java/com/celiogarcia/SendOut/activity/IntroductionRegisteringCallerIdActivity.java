package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;

public class IntroductionRegisteringCallerIdActivity extends AppCompatActivity {

    private Configuracao configuracao;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_registering_caller_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
    }

    public void continuar(View view){
        configuracao = new Configuracao(context);
        if (configuracao.minutosDeVoz() > 1) {
            startActivity(new Intent(this, CallerIdWarningActivity.class));
            finish();
        }else{
            Toast.makeText(this, context.getString(R.string.toast_toast_sem_credito_voz), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
