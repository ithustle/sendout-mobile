package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.activity.BemVindoActivity;
import com.celiogarcia.SendOut.activity.ConfirmaRegistoActivity;
import com.celiogarcia.SendOut.activity.ContactosActivity;
import com.celiogarcia.SendOut.activity.RegisterActivity;
import com.celiogarcia.SendOut.modelo.CarregaConta;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.modelo.UserDevice;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistaTelefoneTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private final Contacto contacto;
    private Configuracao configuracao;
    private ProgressDialog dialog;
    private String status;
    private String valor;
    private String sms;
    private String codigo;
    private CarregaConta conta;
    private String voz;
    private UserDevice device;
    private String verApp;

    public RegistaTelefoneTask(Context context, Contacto contacto, Configuracao configuracao, UserDevice device){
        this.context = context;
        this.contacto = contacto;
        this.configuracao = configuracao;
        this.device = device;
        this.conta = new CarregaConta();
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_registar_titulo_conta), context.getString(R.string.dialogo_registar_texto), false, true);
    }

    @Override
    protected String doInBackground(Object... params) {

        WebService webService = new WebService();
        String regista = webService.regista(contacto, device);

        if (regista != null){
            try {
                JSONObject jsonObject = new JSONObject(regista);
                status = jsonObject.getString("status");
                valor = jsonObject.getString("saldo");
                sms = jsonObject.getString("sms");
                voz = jsonObject.getString("saldo_voz");
                codigo = jsonObject.getString("caller_id_codigo");
                verApp = jsonObject.getString("ver_app");

                double dValor = Double.parseDouble(valor);
                int iSms = 0;
                if (sms != null) {
                    iSms = Integer.parseInt(sms);
                }

                if (status.equals("Go")){
                    conta.setValor(dValor);
                }

                conta.setSms(iSms);

                return status;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String resposta) {
        configuracao = new Configuracao(context);

        if (resposta == null){
            dialog.dismiss();
            Toast.makeText(context, context.getString(R.string.toast_internet_falhou), Toast.LENGTH_LONG).show();
        }else{
            resposta = resposta.replaceAll("\\\\s+","");
            if (resposta.equals("Ok")){
                configuracao.setInfoActualizada(verApp);
                Intent intentConfirmarRegisto = new Intent(context, ConfirmaRegistoActivity.class);
                //intentConfirmarRegisto.putExtra("codigo", codigo);
                context.startActivity(intentConfirmarRegisto);

            }else{
                configuracao.carregaCarteira(conta.getValor());
                configuracao.contaConfirmada();
                Intent intentGoHome = new Intent(context, ContactosActivity.class);
                context.startActivity(intentGoHome);
            }
            dialog.dismiss();
            configuracao.registaConta(contacto);
            configuracao.adicionaSms(conta.getSms());
            configuracao.adicionaMinutosDeVoz(Integer.parseInt(voz));
            configuracao.tipoDePerfil(0);

            if (Integer.parseInt(codigo) != 0){
                configuracao.registerCallerId(codigo);
            }

            RegisterActivity.register.finish();
            BemVindoActivity.welcome.finish();
        }
    }
}
