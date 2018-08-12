package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.CarregaConta;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class CompraSmsCarteiraTask extends AsyncTask<Object, Object, String> {

    private final int pacote;
    private static ProgressDialog dialog;
    private final int pac;
    private Context context;
    private Configuracao configuracao;
    private Contacto contacto;
    private CarregaConta compra;
    private String status;
    private String valor;
    private int pos;
    private String retorno;
    private String ID;

    public CompraSmsCarteiraTask(Context context, int pacote, int x, int y, String ID) {
        this.context = context;
        this.pacote = pacote;
        this.compra = new CarregaConta();
        this.pos = x;     //opção de escolha se é pelo KO ou GP
        this.pac = y;     // Opção para compra de voz KO ou GP
        this.ID = ID;
    }

    @Override
    protected String doInBackground(Object... params) {

        configuracao = new Configuracao(context);
        contacto = new Contacto();
        contacto.setNumeroDeTelefone(configuracao.pegaNumeroDoRemetente());

        if (pos == 0){    //SMS
            switch (pac){
                case 0:
                    retorno = new WebService().pagaSms(contacto, pacote, 0);        //Pagamento com o KO
                    break;
                case 1:
                    retorno = new WebService().billing(contacto, pacote, 0, ID);        //Pagamento com o GP
                    break;
            }

        }else {         //VOZ
            switch (pac){
                case 0:
                    retorno = new WebService().pagaSms(contacto, pacote, 1);        //Pagamento com o KO
                    break;
                case 1:
                    retorno = new WebService().billing(contacto, pacote, 1, ID);        //Pagamento com o GP
                    break;
            }
        }


        if (retorno != null) {
            try {
                JSONObject jsonObject = new JSONObject(retorno);
                status = jsonObject.getString("status");
                if ((pos == 0 && pac == 0) || (pos == 1 && pac == 0)){
                    valor = jsonObject.getString("saldo");
                    compra.setValor(Double.parseDouble(valor));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return status;

        }else{
            return retorno;
        }
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_carregar_titulo_sms), context.getString(R.string.dialogo_carregar_texto), false, true);
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if (s != null) {
            if (s.equals("Ok")) {
                if ((pos == 0 && pac == 0) || (pos == 1 && pac == 0)){
                    configuracao.descontaCarteira(compra.getValor());
                }

                if (pos == 0) {
                    configuracao.adicionaSms(pacote);
                }

                if (pos == 1){
                    configuracao.adicionaMinutosDeVoz(pacote);
                }

                Toast.makeText(context, context.getString(R.string.toast_compra_sucesso), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, context.getString(R.string.toast_compra_falhou), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context, context.getString(R.string.toast_internet_falhou), Toast.LENGTH_LONG).show();
        }
    }
}
