package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.dao.MensagemDao;
import com.celiogarcia.SendOut.modelo.CarregaConta;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.celiogarcia.SendOut.activity.ContactosActivity.sdb;
import static com.celiogarcia.SendOut.activity.SmsContactoActivity.refreshListaSmsContacto;
import static com.celiogarcia.SendOut.activity.SmsHistorico.refreshListaSmsHistorico;

/**
 * Created by celiogarcia on 16/07/16.
 */
public class EnviaSmsTask extends AsyncTask<Object, Object, String> {

    private Context context;
    private Contacto contacto;
    private ProgressDialog dialog;
    private MensagemDao dao;
    private String status;
    private String smsCredito;
    private String numeroDeTelefone;
    private String mensagem;
    private CarregaConta conta;
    private Configuracao configuracao;
    private int flag;

    public EnviaSmsTask(Context context, String numero, String smsTexto, int flag) {
        this.context = context;
        this.numeroDeTelefone = numero;
        this.mensagem = smsTexto;
        dao = new MensagemDao(sdb);
        configuracao = new Configuracao(context);
        conta = new CarregaConta();
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,  context.getString(R.string.dialogo_enviar_sms), context.getString(R.string.dialogo_titulo_enviar_sms), true, true);
    }

    @Override
    protected String doInBackground(Object... objects) {

        contacto = new Contacto();
        contacto.setNumeroDeTelefone(numeroDeTelefone);
        contacto.setMensagem(mensagem);

        WebService webService = new WebService();
        String resposta = webService.enviaSMS(contacto, new Configuracao(context));

        if (resposta != null) {

            if (configuracao.isPessoal()) {
                try {
                    JSONObject jsonObject = new JSONObject(resposta);
                    status = jsonObject.getString("status");
                    smsCredito = jsonObject.getString("credito");

                    conta.setSms(Integer.parseInt(smsCredito));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                status = resposta;
            }

            return status;
        }else{
            return resposta;
        }
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        cancel(true);
        int status;

        if (resposta != null) {
            resposta = resposta.replaceAll("\\\\s+", "");
        }else{
            Toast.makeText(context, context.getString(R.string.toast_internet_falhou), Toast.LENGTH_LONG).show();
            return;
        }

        if (resposta.equals("110")){
            Toast.makeText(context, context.getString(R.string.toast_enviar_sms_validade), Toast.LENGTH_LONG).show();
            status = 1;
        }else{
            if (resposta.equals("0")) {
                Toast.makeText(context, context.getString(R.string.toast_enviar_sms_enviada), Toast.LENGTH_LONG).show();
                configuracao.actualizaSms(conta.getSms());
                status = 0;
            } else {
                Toast.makeText(context, context.getString(R.string.toast_enviar_sms_falhou), Toast.LENGTH_LONG).show();
                status = 1;
            }
        }

        dao.registaMensagem(contacto, status, flag);
        sdb.close();
        refreshListaSmsHistorico = true;
        refreshListaSmsContacto = true;
    }
}
