package com.celiogarcia.SendOut.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.celiogarcia.SendOut.activity.ChamadaActivity.dialog;
import static com.celiogarcia.SendOut.receiver.ConnectivityReceiver.HAS_CONNECTION;

/**
 * Created by 92178 on 07/12/2016.
 */

public class VerificaSaldoVoz extends AsyncTask<Object, Object, Integer> {

    private final Context context;
    private final Configuracao configuracao;
    private String contacto;

    public VerificaSaldoVoz(Context context, String contacto){
        this.context = context;
        this.contacto = contacto;
        configuracao = new Configuracao(this.context);
    }

    @Override
    protected Integer doInBackground(Object... params) {

        WebService webService = new WebService();
        String resposta = webService.verificaSaldo(contacto);

        if (resposta != null){
            try {
                JSONObject jsonObject = new JSONObject(resposta);
                String tempoDeChamada = jsonObject.getString("saldo_voz");
                String valido = jsonObject.getString("valido");

                int timeCall;
                if (!tempoDeChamada.equals("null")){
                    timeCall = Integer.parseInt(tempoDeChamada);
                }else{
                    timeCall = 0;
                }

                configuracao.validadeDeChamadaDeVoz(valido);

                return timeCall;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Integer minutos) {

        if (minutos == null){
            dialog.dismiss();
            HAS_CONNECTION = false;
        }else {
            configuracao.actualizaMinutoDeVoz(minutos);
            Log.i("TENTA", "Saldo: " + minutos + " min");
        }
    }
}
