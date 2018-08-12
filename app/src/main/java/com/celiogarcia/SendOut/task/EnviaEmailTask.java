package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.activity.SuporteActivity;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

/**
 * Created by 92178 on 09/12/2016.
 */

public class EnviaEmailTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final String nome;
    private final String email;
    private final String assunto;
    private final String mensagem;

    private static ProgressDialog dialog;
    private final Configuracao configuracao;

    public EnviaEmailTask(Context context, String nome, String email, String assunto, String mensagem){
        this.context = context;
        this.nome = nome;
        this.email = email;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.configuracao = new Configuracao(context);
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_enviar_email), context.getString(R.string.dialogo_enviar_texto), false, true);
    }

    @Override
    protected String doInBackground(Object... params) {

        WebService webService = new WebService();
        String resposta = webService.enviarMail(configuracao.pegaNumeroDoRemetente(), nome, email, assunto, mensagem);

        if (resposta != null){
            return resposta;
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();

        if (s != null){

            if (s.equals("Ok")) {
                Toast.makeText(context, context.getString(R.string.toast_envio_email), Toast.LENGTH_LONG).show();
                SuporteActivity.sp.finish();
            }else{
                Toast.makeText(context, context.getString(R.string.toast_envio_email_falhou), Toast.LENGTH_LONG).show();
            }
        }else{
            return;
        }
    }
}
