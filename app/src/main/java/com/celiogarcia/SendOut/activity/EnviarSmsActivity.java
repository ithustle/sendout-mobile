package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.task.EnviaSmsTask;

public class EnviarSmsActivity extends AppCompatActivity {

    private EditText numeroAEnviar;
    private EditText novaMensagem;
    private Configuracao configuracao;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_sms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        configuracao = new Configuracao(context);

        numeroAEnviar = (EditText) findViewById(R.id.novo_numero_enviar_sms);
        novaMensagem = (EditText) findViewById(R.id.nova_mensagem_enviar);

        Button botaoEnviar = (Button) findViewById(R.id.enviar_nova_sms);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            public String sms;

            @Override
            public void onClick(View view) {

                String numero = numeroAEnviar.getText().toString();

                if (numero.substring(0, 1).equals("+")){
                    if (numero.substring(0, 3).equals("+55")){
                        if (numero.length() < 14 || numero.length() > 14){
                            Toast.makeText(context, getString(R.string.sms_numero_errado), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }else{
                    if (numero.substring(0, 2).equals("55")){
                        if (numero.length() < 13 || numero.length() > 13){
                            Toast.makeText(context, getString(R.string.sms_numero_errado), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                if (configuracao.isPessoal()){
                    sms = novaMensagem.getText().toString() + "\nAss. " + configuracao.pegaNumeroDoRemetente();
                }else{
                    sms = novaMensagem.getText().toString();
                }

                new EnviaSmsTask(context, numero, sms, 0).execute();
                numeroAEnviar.setText("");
                novaMensagem.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
