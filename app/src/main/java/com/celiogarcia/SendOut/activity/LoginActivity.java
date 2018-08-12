package com.celiogarcia.SendOut.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.task.LoginTask;

public class LoginActivity extends AppCompatActivity {

    public static LoginActivity activityLogin;
    private TextView numeroTelefoneLogin;
    private TextView senhaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activityLogin = this;

        numeroTelefoneLogin = (TextView) findViewById(R.id.numero_telefone_login);
        senhaLogin = (TextView) findViewById(R.id.senha_login);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        }
    }

    public void entrar(View view){
        Contacto contacto = new Contacto();
        TipoDePerfilActivity.activityTipoPerfil.finish();
        contacto.setNumeroDeTelefone(numeroTelefoneLogin.getText().toString());
        new LoginTask(this, contacto, senhaLogin.getText().toString()).execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    idDoDispositivo();
                }else{
                    Toast.makeText(this, getString(R.string.toast_pemissao), Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    private String idDoDispositivo(){

        TelephonyManager manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        return manager.getDeviceId();

    }
}
