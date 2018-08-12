package com.celiogarcia.SendOut.task;

import android.os.AsyncTask;

import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.modelo.UserDevice;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 92178 on 11/01/2017.
 */

public class ActualizaInfo extends AsyncTask<Object, Object, String> {

    private UserDevice device;
    private Configuracao configuracao;
    private Contacto contacto;
    private String resposta;

    public ActualizaInfo(Contacto contacto, Configuracao configuracao, UserDevice device) {
        this.contacto = contacto;
        this.configuracao = configuracao;
        this.device = device;
    }

    @Override
    protected String doInBackground(Object... objects) {
        WebService webService = new WebService();
        String retorno = webService.actualizaInfo(contacto, device);

        if (retorno != null) {
            try {

                JSONObject jsonObject = new JSONObject(retorno);
                resposta = jsonObject.getString("good");

                return resposta;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if (s != null){
            configuracao.setInfoActualizada(s);
        }

        return;
    }
}
