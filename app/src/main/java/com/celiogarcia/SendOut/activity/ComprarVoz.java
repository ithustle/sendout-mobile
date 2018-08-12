package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Compra;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.KwanzaOnlineDialog;
import com.celiogarcia.SendOut.task.CompraSmsCarteiraTask;
import com.celiogarcia.SendOut.task.VerificaSaldoVoz;
import com.celiogarcia.SendOut.util.IabHelper;
import com.celiogarcia.SendOut.util.IabResult;
import com.celiogarcia.SendOut.util.Inventory;
import com.celiogarcia.SendOut.util.Purchase;

import java.util.List;

import static com.celiogarcia.SendOut.modelo.Constants.PACOTE_15;
import static com.celiogarcia.SendOut.modelo.Constants.PACOTE_30;
import static com.celiogarcia.SendOut.modelo.Constants.PACOTE_60;

public class ComprarVoz extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private Context context;
    private TextView pacote15;
    private TextView pacote30;
    private TextView pacote60;
    private Configuracao configuracao;
    private AlertDialog.Builder dialog;
    private TextView smsNaConta;

    private static final String TAG = "compras_in_app";
    IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_voz);

        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        smsNaConta = (TextView) findViewById(R.id.sms_na_conta);

        configuracao = new Configuracao(context);

        //Refresh on real time credit pocket

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                smsNaConta.setText(configuracao.minutosDeVoz() + " Min");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        //************************************************************** PACOTE DE 25 SMS

        pacote15 = (TextView) findViewById(R.id.botao_minuto_15);
        pacote15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {context.getString(R.string.dialogo_google_wallet), context.getString(R.string.dialogo_credito_carteira)};

                dialog = new AlertDialog.Builder(context);
                dialog.setTitle(context.getString(R.string.dialogo_titulo_metodo_pagamento));
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(context.getString(R.string.dialogo_google_wallet))){

                            //API de pagamento do Google Play

                            mHelper.launchPurchaseFlow((Activity) context, PACOTE_15, 12001, mPurchaseFinishedListener, "mypurchasetoken15");

                        }else if (items[which].equals(context.getString(R.string.dialogo_credito_carteira))){
                            int pacote = 15;
                            new KwanzaOnlineDialog(context, 3, pacote, PACOTE_15);
                        }
                    }
                });
                dialog.show();
            }
        });

        //************************************************************** PACOTE DE 30 MINUTOS

        pacote30 = (TextView) findViewById(R.id.botao_minuto_30);
        pacote30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {context.getString(R.string.dialogo_google_wallet), context.getString(R.string.dialogo_credito_carteira)};

                dialog = new AlertDialog.Builder(context);
                dialog.setTitle(context.getString(R.string.dialogo_titulo_metodo_pagamento));
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(context.getString(R.string.dialogo_google_wallet))){

                            mHelper.launchPurchaseFlow((Activity) context, PACOTE_30, 12002, mPurchaseFinishedListener, "mypurchasetoken30");

                        }else if (items[which].equals(context.getString(R.string.dialogo_credito_carteira))){
                            int pacote = 30;
                            new KwanzaOnlineDialog(context, 4, pacote, PACOTE_30);

                        }
                    }
                });
                dialog.show();
            }
        });

        //************************************************************** PACOTE DE 60 MINUTOS

        pacote60 = (TextView) findViewById(R.id.botao_minuto_60);
        pacote60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {context.getString(R.string.dialogo_google_wallet), context.getString(R.string.dialogo_credito_carteira)};

                dialog = new AlertDialog.Builder(context);
                dialog.setTitle(context.getString(R.string.dialogo_titulo_metodo_pagamento));
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(context.getString(R.string.dialogo_google_wallet))){

                            mHelper.launchPurchaseFlow((Activity) context, PACOTE_60, 12003, mPurchaseFinishedListener, "mypurchasetoken60");

                        }else if (items[which].equals(context.getString(R.string.dialogo_credito_carteira))){
                            int pacote = 60;
                            new KwanzaOnlineDialog(context, 5, pacote, PACOTE_60);

                        }
                    }
                });
                dialog.show();
            }
        });

        Compra compra = new Compra();
        list = compra.getInformacao();

        listView = (ListView) findViewById(R.id.lista_info);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(ComprarVoz.this, PagarCartaoKoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ComprarVoz.this, TarifasActivity.class));
                        break;
                    case 2:
                        break;
                }
            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new VerificaSaldoVoz(this, configuracao.pegaNumeroDoRemetente()).execute();
        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwPtHuxgKBgl5EQEOnb1LZh85WSjYmFAILIrQWkMQe2aelsjTJyNyo5w3QwGPQtGSLOO9oLafeM2RsHMm0jJIDq6t+BlQjO+h0a2dNaYJJtYy+VPuijsveUBycLmT+QyeJougkRfoUX8+0LQpUCAhXIn7Da2L+D8ykM2kP16Z9wqa3tDuTpldlAFeYSX/de6Lm5IJoj3gaqMmYEXt46svWA1yG2nu1SwekMIs6A9SThlCEGecOF+8BbtyL0mUAR0ktN4/4tKjbGAK+qVhVMO1YqnfYq/dJuoHVfg/onpdR9nF04d+fCp5KPNMmm0IFt0biy8s4rm35rFh+SA7D8FXxQIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {

                    Log.d(TAG, "In-app Billing setup failed: " + result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });
    }

    /* LÓGICA DO GOOGLE PLAY BILLING */

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                Toast.makeText(context, context.getString(R.string.toast_compra_cancelada), Toast.LENGTH_LONG).show();
                return;
            } else if (purchase.getSku().equals(PACOTE_15)) {
                consumeItem();
            }else if (purchase.getSku().equals(PACOTE_30)) {
                consumeItem();
            }else if (purchase.getSku().equals(PACOTE_60)) {
                consumeItem();
            }
        }
    };

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            Purchase purchasePacote15 = inventory.getPurchase(PACOTE_15);
            Purchase purchasePacote30 = inventory.getPurchase(PACOTE_30);
            Purchase purchasePacote60 = inventory.getPurchase(PACOTE_60);

            if (result.isFailure()) {
                // Handle failure
            } else {

                if (purchasePacote15 != null){
                    mHelper.consumeAsync(purchasePacote15, mConsumeFinishedListener);
                }else if (purchasePacote30 != null){
                    mHelper.consumeAsync(purchasePacote30, mConsumeFinishedListener);
                }else{
                    mHelper.consumeAsync(purchasePacote60, mConsumeFinishedListener);
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {

            String compraDePacote = purchase.getSku();

            if (compraDePacote.equals(PACOTE_15)) {
                if (result.isSuccess()) {
                    int pacote = 15;
                    new CompraSmsCarteiraTask(context, pacote, 1, 1, PACOTE_15).execute();
                } else {
                    Toast.makeText(context, context.getString(R.string.toast_compra_cancelada), Toast.LENGTH_LONG).show();
                }
            }else if (compraDePacote.equals(PACOTE_30)) {
                if (result.isSuccess()) {
                    int pacote = 30;
                    new CompraSmsCarteiraTask(context, pacote, 1, 1, PACOTE_30).execute();
                } else {
                    Toast.makeText(context, context.getString(R.string.toast_compra_cancelada), Toast.LENGTH_LONG).show();
                }
            }else {
                if (result.isSuccess()) {
                    int pacote = 60;
                    new CompraSmsCarteiraTask(context, pacote, 1, 1, PACOTE_60).execute();
                } else {
                    Toast.makeText(context, context.getString(R.string.toast_compra_cancelada), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

        Log.d(TAG, "" + resultCode);

        /*if (resultCode != 0){
            if (requestCode == 12001){
                int pacote = 15;
                new CompraSmsCarteiraTask(context, pacote, 1, 1).execute();
            }else if (requestCode == 12002){
                int pacote = 30;
                new CompraSmsCarteiraTask(context, pacote, 1, 1).execute();
            }else{
                int pacote = 60;
                new CompraSmsCarteiraTask(context, pacote, 1, 1).execute();
            }
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }
}
