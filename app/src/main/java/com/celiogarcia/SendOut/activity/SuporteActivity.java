package com.celiogarcia.SendOut.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.task.EnviaEmailTask;

public class SuporteActivity extends AppCompatActivity {

    public static SuporteActivity sp;
    private EditText nome;
    private EditText assunto;
    private EditText email;
    private EditText mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suporte);

        sp = this;

        nome = (EditText) findViewById(R.id.suporte_nome);
        assunto = (EditText) findViewById(R.id.suporte_assunto);
        email = (EditText) findViewById(R.id.suporte_email);
        mensagem = (EditText) findViewById(R.id.suporte_mensagem);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_suporte, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.menu_enviar_email:
                new EnviaEmailTask(sp, nome.getText().toString(), email.getText().toString(),
                        assunto.getText().toString(), mensagem.getText().toString()).execute();
                break;

            default:
                super.onBackPressed();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
