package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.CarregaConta;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.task.ActualizaCarteiraTask;
import com.celiogarcia.SendOut.task.CarregaContaTask;

public class PagarCartaoKoActivity extends AppCompatActivity {

    private EditText codigoInserido;
    private CarregaConta carregaConta;
    private String codigo;
    private TextView saldoDeConta;
    private Configuracao configuracao;
    private Context context;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_cartao_ko);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configuracao = new Configuracao(this);
        carregaConta = new CarregaConta();
        context = this;

        saldoDeConta = (TextView) findViewById(R.id.saldo_de_conta);
        codigoInserido = (EditText) findViewById(R.id.code_ko);

        //Refresh on real time credit pocket

        t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                saldoDeConta.setText(String.format("%,.2f KZ", configuracao.carteira()));
                                Log.v("saldo", "Corre");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        Button resgate = (Button) findViewById(R.id.carregar_carteira);
        resgate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregaConta.setCodigo(codigoInserido.getText().toString());
                codigo = carregaConta.getCodigo().toString();

                new CarregaContaTask(context, codigo, configuracao, codigoInserido).execute();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        t.interrupt();

    }

    @Override
    protected void onStart() {
        super.onStart();
        new ActualizaCarteiraTask(this, configuracao.pegaNumeroDoRemetente()).execute();
    }
}
