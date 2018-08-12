package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.activity.ConfirmaRegistoActivity;
import com.celiogarcia.SendOut.activity.ContactosActivity;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivacaoDeContaTask extends AsyncTask<Object, Object, String> {

    private final String codigo;
    private final Context contexto;
    private Configuracao configuracao;
    public static ProgressDialog dialog;
    private Contacto contacto;
    private String status;

    public ActivacaoDeContaTask(Context context, Configuracao configuracao, String codigo) {
        this.configuracao = configuracao;
        this.codigo = codigo;
        this.contexto = context;
        this.contacto = new Contacto();
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(contexto, contexto.getString(R.string.dialogo_activar_conta_titulo), contexto.getString(R.string.dialogo_activar_conta_texto), false, true);
    }

    @Override
    protected String doInBackground(Object... params) {
        String activacao;
        if (codigo.isEmpty()){
            status = "No";
        }else{
            contacto.setNumeroDeTelefone(configuracao.pegaNumeroDoRemetente());
            activacao = new WebService().activacao(contacto, codigo);

            try {
                JSONObject jsonObject = new JSONObject(activacao);
                status = jsonObject.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    @Override
    protected void onPostExecute(String resposta) {

        if (resposta.equals("Ok")){

            new Configuracao(contexto).contaConfirmada();

            contexto.startActivity(new Intent(contexto, ContactosActivity.class));
            ConfirmaRegistoActivity.confirma.finish();
        }else{
            dialog.dismiss();
            Toast.makeText(contexto, contexto.getString(R.string.toast_codigo_inserido_falhou), Toast.LENGTH_LONG).show();
        }
    }
}
