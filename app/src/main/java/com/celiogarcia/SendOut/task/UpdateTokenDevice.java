package com.celiogarcia.SendOut.task;

import android.content.Context;
import android.os.AsyncTask;

import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

/**
 * Created by 92178 on 20/12/2016.
 */

public class UpdateTokenDevice extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final Configuracao configuracao;
    private String token;

    public UpdateTokenDevice(Context context, String token){
        this.context = context;
        this.token = token;
        configuracao = new Configuracao(this.context);
    }

    @Override
    protected String doInBackground(Object... params) {

        WebService webService = new WebService();
        String resposta = webService.updateToken(configuracao.pegaNumeroDoRemetente(), token);

        if (resposta != null){
            return resposta;
        }

        return null;
    }
}
