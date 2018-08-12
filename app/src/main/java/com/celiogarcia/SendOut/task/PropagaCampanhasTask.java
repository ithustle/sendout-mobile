package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

/**
 * Created by 92178 on 15/12/2016.
 */

public class PropagaCampanhasTask extends AsyncTask<Object, Object, String> {

    private Context context;

    private String campanhaId;
    private ProgressDialog dialog;

    public PropagaCampanhasTask(Context context, String campanhaId){
        this.context = context;
        this.campanhaId = campanhaId;
    }

    @Override
    protected String doInBackground(Object... params) {

        WebService webService = new WebService();
        String resposta = webService.propagarCampanha(new Configuracao(context).pegaNumeroDoRemetente(), campanhaId);

        if (resposta != null){
            return resposta;
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_title_campanha), context.getString(R.string.dialogo_enviar_sms), true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if (s != null){
            if (s.equals("0")) {
                Toast.makeText(context, context.getString(R.string.toast_enviar_sms_campanha), Toast.LENGTH_LONG).show();
            }
        }
    }
}
