package com.celiogarcia.SendOut.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.modelo.Pais;
import com.celiogarcia.SendOut.modelo.UserDevice;
import com.celiogarcia.SendOut.task.RegistaTelefoneTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText mostraPaises;
    private EditText mostraIndicativo;
    private Contacto contacto;
    private EditText numeroParaRegistar;
    private EditText nomeDoRemetente;
    private Configuracao configuracao;
    public static Activity register;
    private TelephonyManager manager;
    private String uuid;
    private UserDevice device;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        register = this;

        configuracao = new Configuracao(this);

        device = new UserDevice(this);

        mostraIndicativo = (EditText) findViewById(R.id.registar_indicativo);
        mostraPaises = (EditText) findViewById(R.id.registar_seleciona_pais);

        mostraPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMostraPaises = new Intent(RegisterActivity.this, CountryActivity.class);
                startActivityForResult(intentMostraPaises, 1);
            }
        });

        numeroParaRegistar = (EditText) findViewById(R.id.registar_numero_de_telefone);
        nomeDoRemetente = (EditText) findViewById(R.id.registar_nome);

        final Button registar = (Button) findViewById(R.id.registar_botao_continuar);
        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TipoDePerfilActivity.activityTipoPerfil.finish();
                if (Build.VERSION.SDK_INT < 23) {
                    uuid = idDoDispositivo();
                }

                //if (manager.getLine1Number() != null) {
                    //if (manager.getLine1Number().equals(numeroParaRegistar.getText().toString())) {
                        contacto = new Contacto();
                        String sender = nomeDoRemetente.getText().toString();

                        if (mostraIndicativo.getText().toString().matches("") || mostraPaises.getText().toString().matches("")) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.toast_registar_pais), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (numeroParaRegistar.getText().toString().matches("")) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.toast_registar_telefone), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (sender.matches("")){
                            return;
                        }

                        //Verifica se o nome digitado contém espaço
                        Pattern pattern = Pattern.compile("\\s");
                        Matcher matcher = pattern.matcher(sender);
                        boolean found = matcher.find();
                        if (!found) {

                            if (sender.length() > 11) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.toast_registar_telefone_tamanho), Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        String fullNumber = mostraIndicativo.getText().toString() + numeroParaRegistar.getText().toString();

                        contacto.setNome(nomeDoRemetente.getText().toString());
                        contacto.setNumeroDeTelefone(fullNumber);
                        contacto.setUiid(uuid);
                Log.i("UUID", uuid);
                        contacto.setIndicativo(mostraIndicativo.getText().toString());
                        contacto.setPais(mostraPaises.getText().toString());

                        new RegistaTelefoneTask(RegisterActivity.this, contacto, configuracao, device).execute();
                    /*} else {
                        Toast.makeText(RegisterActivity.this, getString(R.string.toast_registar_aviso_numero), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, getString(R.string.toast_registar_aviso_numero_null), Toast.LENGTH_LONG).show();
                }*/
            }
        });
        //idDoDispositivo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Pais indicativo = (Pais) data.getSerializableExtra("pais");

                if (indicativo != null) {
                    mostraPaises.setText(indicativo.getNome());
                    mostraIndicativo.setText(indicativo.getIndicativo());
                }
            }
        }
    }

    private String idDoDispositivo(){
        TelephonyManager manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    uuid = idDoDispositivo();
                }else{
                    Toast.makeText(this, getString(R.string.toast_pemissao), Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }
}
