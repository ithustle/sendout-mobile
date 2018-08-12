package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.activity.ChamadaActivity;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.celiogarcia.SendOut.activity.ConfirmarCallerIdActivity.codigo;
import static com.celiogarcia.SendOut.activity.EscolheRegistoActivity.registoActivity;

/**
 * Created by 92178 on 13/11/2016.
 */

public class RegistaCallerIdTask extends AsyncTask<Object, Object, String> {

    private final int codigoZero;
    private String codigoDeRegisto;
    private Configuracao configuracao;
    private Context context;
    private ProgressDialog dialog;

    public RegistaCallerIdTask(Context context, int i){
        this.context = context;
        this.codigoZero = i;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.registo_caller_status), context.getString(R.string.dialogo_caller_id), false, true);
    }

    @Override
    protected String doInBackground(Object... params) {

        Contacto contacto = new Contacto();
        configuracao = new Configuracao(context);
        contacto.setNumeroDeTelefone(configuracao.pegaNumeroDoRemetente());

        WebService webService = new WebService();
        String resposta = webService.registaCallerId(contacto, codigoZero);

        if (resposta != null) {
            try {

                JSONObject jsonObject = new JSONObject(resposta);
                codigoDeRegisto = jsonObject.getString("codigo");

                Log.v("CHAMADAS", "Código:" + codigoDeRegisto);

                if (!codigoDeRegisto.equals("null") || !codigoDeRegisto.equals("0")) {
                    configuracao.registerCallerId(codigoDeRegisto);
                    return codigoDeRegisto;
                }else{
                    return codigoDeRegisto;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if (s != null) {

            if (!s.equals("0")) {       // Se o código for diferente de zero
                codigo.setText(s);
            }else{
                configuracao.setSharedNumber(true);
                context.startActivity(new Intent(context, ChamadaActivity.class));
                registoActivity.finish();
            }
        }else{
            return;
        }
    }
}
