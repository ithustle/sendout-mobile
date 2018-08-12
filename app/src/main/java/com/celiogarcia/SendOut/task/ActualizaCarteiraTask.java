package com.celiogarcia.SendOut.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by 92178 on 05/01/2017.
 */

public class ActualizaCarteiraTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final Configuracao configuracao;
    private String contacto;

    public ActualizaCarteiraTask(Context context, String contacto){
        this.context = context;
        this.contacto = contacto;
        configuracao = new Configuracao(this.context);
    }

    @Override
    protected String doInBackground(Object... params) {

        WebService webService = new WebService();
        String resposta = webService.actualizaCarteira(contacto);

        if (resposta != null){
            try {
                JSONObject jsonObject = new JSONObject(resposta);

                return jsonObject.getString("carteira");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String carteira) {

        if (carteira == null){
            return;
        }else {
            configuracao.actualizaCarteira(carteira);
            Log.i("TENTA", "Saldo: " + carteira + " KZ");
        }
    }
}
