package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.activity.ContactosActivity;
import com.celiogarcia.SendOut.modelo.CarregaConta;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.celiogarcia.SendOut.activity.BemVindoActivity.welcome;
import static com.celiogarcia.SendOut.activity.LoginActivity.activityLogin;
import static com.celiogarcia.SendOut.activity.TipoDePerfilActivity.activityTipoPerfil;
import static com.celiogarcia.SendOut.task.ActivacaoDeContaTask.dialog;

/**
 * Created by 92178 on 11/12/2016.
 */

public class LoginTask extends AsyncTask<Object, Object, String> {

    private Context context;
    private Contacto contacto;
    private Configuracao configuracao;
    private CarregaConta conta;

    private String senha;
    private String empresa;
    private String login;
    private String numero;
    private String indicativo;
    private String valor;
    private String sms;
    private String voz;
    private String codigo;

    public LoginTask(Context context, Contacto contacto, String senha){
        this.context = context;
        this.contacto = contacto;
        this.senha = senha;
        this.conta = new CarregaConta();
        this.configuracao = new Configuracao(context);
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_login_title), context.getString(R.string.dialogo_login_texto), false, true);
    }

    @Override
    protected String doInBackground(Object... params) {

        WebService webService = new WebService();
        String resposta = webService.login(contacto.getNumeroDeTelefone(), senha);

        if (resposta != null){
            try {
                JSONObject jsonObject = new JSONObject(resposta);
                empresa = jsonObject.getString("empresa");
                numero = jsonObject.getString("numero");
                indicativo = jsonObject.getString("indicativo");
                login = jsonObject.getString("login");
                valor = jsonObject.getString("saldo");
                sms = jsonObject.getString("sms");
                voz = jsonObject.getString("saldo_voz");
                codigo = jsonObject.getString("caller_id_codigo");

                if (login.equals("0")){

                    double dValor = Double.parseDouble(valor);
                    int iSms = Integer.parseInt(sms);

                    contacto.setNumeroDeTelefone(numero);
                    contacto.setNome(empresa);
                    contacto.setIndicativo(indicativo);
                    configuracao.registaConta(contacto);

                    conta.setSms(iSms);
                    conta.setValor(dValor);

                    return "0";
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

        if (s != null){

            if (s.equals("0")) {
                configuracao.carregaCarteira(conta.getValor());
                configuracao.adicionaSms(conta.getSms());
                configuracao.adicionaMinutosDeVoz(Integer.parseInt(voz));
                configuracao.registerCallerId(codigo);
                configuracao.contaConfirmada();
                activityTipoPerfil.finish();
                activityLogin.finish();
                welcome.finish();
                context.startActivity(new Intent(context, ContactosActivity.class));
            }
        }else{
            Toast.makeText(context, context.getString(R.string.toast_login_falhou), Toast.LENGTH_LONG).show();
        }
    }
}
