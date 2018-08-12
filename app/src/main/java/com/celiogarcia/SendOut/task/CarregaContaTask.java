package com.celiogarcia.SendOut.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.webservice.WebService;

public class CarregaContaTask extends AsyncTask<Object, Object, String> {

    private String codigo;
    private Context context;
    private ProgressDialog dialog;
    private WebService webService;
    private Configuracao configuracao;
    private EditText codigoKo;

    public CarregaContaTask(Context context, String codigo, Configuracao configuracao, EditText codigoInserido) {
        this.codigo = codigo;
        this.context = context;
        this.configuracao = configuracao;
        this.codigoKo = codigoInserido;
    }

    @Override
    protected String doInBackground(Object... params) {

        String s;
        webService = new WebService();
        String valor = webService.resgate(codigo);

        if (valor != null){

            valor = valor.replaceAll("[\\uFEFF-\\uFFFF]", "");
            valor = valor.replaceAll("\\\\s+","");

            if (valor.equals("Digite o código de recarga.") || valor.equals("falhou") || valor.equals("fail")){
                s = context.getString(R.string.toast_carregamento_falhou);
            }else{
                s = webService.carregaConta(valor, configuracao.pegaNumeroDoRemetente());
                configuracao.carregaCarteira(Double.parseDouble(valor));
            }
        }else{
            s = "net_fail";
        }

        return s;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, context.getString(R.string.dialogo_adicionar_titulo_credito), context.getString(R.string.dialogo_adicionar_credito_texto), false, true);
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        if (new String(resposta).equals("net_fail")){
            Toast.makeText(context, context.getString(R.string.toast_internet_falhou), Toast.LENGTH_LONG).show();
        }else{
            if (new String(resposta).equals("done")){
                Toast.makeText(context, context.getString(R.string.toast_carregamento_sucesso), Toast.LENGTH_SHORT).show();
                codigoKo.setText("");
            }else{
                Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
            }
        }

    }
}
