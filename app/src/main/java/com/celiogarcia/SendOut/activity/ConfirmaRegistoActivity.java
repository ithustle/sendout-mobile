package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.task.ActivacaoDeContaTask;

public class ConfirmaRegistoActivity extends AppCompatActivity {

    private Configuracao contacto;
    private EditText codigoDeConfirmacao;
    public static Activity confirma;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new Configuracao(this).confirmacao()){
            startActivity(new Intent(this, ContactosActivity.class));
            finish();
        }

        setContentView(R.layout.activity_confirma_registo);

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                code = extras.getString("codigo");
            }
        }else{
            code = (String) savedInstanceState.getSerializable("codigo");
        }

        confirma = this;

        codigoDeConfirmacao = (EditText) findViewById(R.id.activacao_codigo_activacao);
        codigoDeConfirmacao.setText(code);

        Button activar = (Button) findViewById(R.id.activacao_botao_continuar);
        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacto = new Configuracao(confirma);
                new ActivacaoDeContaTask(confirma, contacto, codigoDeConfirmacao.getText().toString()).execute();
            }
        });

    }
}
