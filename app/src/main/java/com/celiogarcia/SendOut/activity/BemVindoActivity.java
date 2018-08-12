package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;

public class BemVindoActivity extends AppCompatActivity {

    public static Activity welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new Configuracao(this).registoFeito()){
            startActivity(new Intent(this, ConfirmaRegistoActivity.class));
            finish();
        }

        setContentView(R.layout.activity_welcome);

        welcome = this;

        Button irParaRegistar = (Button) findViewById(R.id.welcome_botao_continuar);
        irParaRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTipoPerfil = new Intent(BemVindoActivity.this, TipoDePerfilActivity.class);
                startActivity(intentTipoPerfil);
            }
        });
    }
}
