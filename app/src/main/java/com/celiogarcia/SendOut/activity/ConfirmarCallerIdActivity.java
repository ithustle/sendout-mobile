package com.celiogarcia.SendOut.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.task.RegistaCallerIdTask;

public class ConfirmarCallerIdActivity extends AppCompatActivity {

    public static TextView codigo;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_caller_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codigo = (TextView) findViewById(R.id.codigo_de_caller_id);
        configuracao = new Configuracao(this);

        if (configuracao.hasCallerIdRegistered()){
            codigo.setText(configuracao.getCodeCallerId());
        }else {
            new RegistaCallerIdTask(this, 1).execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
