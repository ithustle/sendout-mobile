package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.celiogarcia.SendOut.R;

public class TipoDePerfilActivity extends Activity {

    public static TipoDePerfilActivity activityTipoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_de_perfil);

        activityTipoPerfil = this;
    }

    public void escolhePerfil(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.opt_perfil_pesssoal:
                if (checked)
                    startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.opt_perfil_empresa:
                if (checked)
                    startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
