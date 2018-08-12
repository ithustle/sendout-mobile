package com.celiogarcia.SendOut.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.celiogarcia.SendOut.activity.ChamadaActivity;
import com.celiogarcia.SendOut.activity.ChamadaEmCurso;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.celiogarcia.SendOut.activity.ChamadaEmCurso.pararContagem;

/**
 * Created by 92178 on 13/11/2016.
 */

public class TempoDasChamadasTask extends AsyncTask<Object, Object, String> {

    private final Contacto contacto;
    private final String para;
    private final String dataChamada;
    private final Context context;

    private Configuracao configuracao;

    public TempoDasChamadasTask(Context context, Configuracao configuracao, Contacto contacto, String para, String dataChamada){
        this.configuracao = configuracao;
        this.contacto = contacto;
        this.para = para;
        this.dataChamada = dataChamada;
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {

        if (dataChamada != null) {
            WebService webService = new WebService();
            String resposta = webService.tempoDeChamada(contacto, para, dataChamada);

            if (resposta != null) {
                try {
                    JSONObject jsonObject = new JSONObject(resposta);
                    String tempoDeChamada = jsonObject.getString("saldo_voz");

                    return tempoDeChamada;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String minutos) {

        if (minutos != null){
            configuracao.actualizaMinutoDeVoz(Integer.parseInt(minutos));
            if (Integer.parseInt(minutos) == 0){
                ChamadaActivity.disconnect();
                pararContagem();
                ChamadaEmCurso.chamada.finish();
            }
            Log.i("TENTA", "Saldo: " + minutos + " min");
        }else{
            return;
        }
    }
}
