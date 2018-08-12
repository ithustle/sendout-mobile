package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.CampanhaAdapter;
import com.celiogarcia.SendOut.modelo.Campanha;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CampanhasTask extends AsyncTask<Object, Object, ArrayList> {

    private ListView lista;
    private Context context;
    private CampanhaAdapter adapter;
    private ProgressDialog dialog;

    public CampanhasTask(Context context, ListView lista_campanha){
        this.context = context;
        this.lista = lista_campanha;
    }

    @Override
    protected ArrayList doInBackground(Object... params) {
        WebService webService = new WebService();
        String resposta = webService.buscaCampanhas(new Configuracao(context).pegaNumeroDoRemetente());

        ArrayList<Campanha> arrayList = new ArrayList<>();

        if (resposta != null){
            try {
                JSONArray array = new JSONArray(resposta);

                for (int i=0; i<array.length(); i++){

                    Campanha campanha = new Campanha();
                    JSONObject jsonObject = array.getJSONObject(i);
                    campanha.setCampanha(jsonObject.getString("nome"));
                    campanha.setCodigo(jsonObject.getString("codigo"));

                    arrayList.add(campanha);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_title_campanha), context.getString(R.string.dialogo_texto_campanha), true, true);
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        dialog.dismiss();
        if (arrayList != null){
            adapter = new CampanhaAdapter(context, arrayList);
            lista.setAdapter(adapter);
        }
    }
}
