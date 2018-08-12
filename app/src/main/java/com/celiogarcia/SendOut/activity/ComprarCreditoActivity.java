package com.celiogarcia.SendOut.activity;

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
import com.celiogarcia.SendOut.util.IabHelper;
import com.celiogarcia.SendOut.util.IabResult;
import com.celiogarcia.SendOut.util.Inventory;
import com.celiogarcia.SendOut.util.Purchase;

import java.util.List;

import static com.celiogarcia.SendOut.modelo.Constants.PACOTE_100;
import static com.celiogarcia.SendOut.modelo.Constants.PACOTE_25;
import static com.celiogarcia.SendOut.modelo.Constants.PACOTE_50;

public class ComprarCreditoActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private Context context;
    private TextView pacote100;
    private TextView pacote200;
    private TextView pacote500;
    private Configuracao configuracao;
    private AlertDialog.Builder dialog;
    private TextView smsNaConta;

    private static final String TAG = "compras_in_app";
    IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_credito);

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
                                smsNaConta.setText(configuracao.creditoSms() + " SMS");
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

        pacote100 = (TextView) findViewById(R.id.botao_pacote_100);
        pacote100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {getString(R.string.dialogo_google_wallet), getString(R.string.dialogo_credito_carteira)};

                dialog = new AlertDialog.Builder(context);
                dialog.setTitle(getString(R.string.dialogo_titulo_metodo_pagamento));
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(getString(R.string.dialogo_google_wallet))){

                            //API de pagamento do Google Play

                            mHelper.launchPurchaseFlow(ComprarCreditoActivity.this, PACOTE_25, 10001, mPurchaseFinishedListener, "mypurchasetoken100");

                        }else if (items[which].equals(getString(R.string.dialogo_credito_carteira))){
                            int pacote = 25;
                            new KwanzaOnlineDialog(context, 0, pacote, PACOTE_25);
                        }
                    }
                });
                dialog.show();
            }
        });

        //************************************************************** PACOTE DE 50 SMS

        pacote200 = (TextView) findViewById(R.id.botao_pacote_200);
        pacote200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {getString(R.string.dialogo_google_wallet), getString(R.string.dialogo_credito_carteira)};

                dialog = new AlertDialog.Builder(context);
                dialog.setTitle(getString(R.string.dialogo_titulo_metodo_pagamento));
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(getString(R.string.dialogo_google_wallet))){

                            mHelper.launchPurchaseFlow(ComprarCreditoActivity.this, PACOTE_50, 10002, mPurchaseFinishedListener, "mypurchasetoken200");

                        }else if (items[which].equals(getString(R.string.dialogo_credito_carteira))){
                            int pacote = 50;
                            new KwanzaOnlineDialog(context, 1, pacote, PACOTE_50);

                        }
                    }
                });
                dialog.show();
            }
        });

        //************************************************************** PACOTE DE 100 SMS

        pacote500 = (TextView) findViewById(R.id.botao_pacote_500);
        pacote500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {getString(R.string.dialogo_google_wallet), getString(R.string.dialogo_credito_carteira)};

                dialog = new AlertDialog.Builder(context);
                dialog.setTitle(getString(R.string.dialogo_titulo_metodo_pagamento));
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(getString(R.string.dialogo_google_wallet))){

                            mHelper.launchPurchaseFlow(ComprarCreditoActivity.this, PACOTE_100, 10003, mPurchaseFinishedListener, "mypurchasetoken500");

                        }else if (items[which].equals(getString(R.string.dialogo_credito_carteira))){
                            int pacote = 100;
                            new KwanzaOnlineDialog(context, 2, pacote, PACOTE_100);

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
                        startActivity(new Intent(ComprarCreditoActivity.this, PagarCartaoKoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ComprarCreditoActivity.this, TarifasSmsActivity.class));
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
            } else if (purchase.getSku().equals(PACOTE_25)) {
                consumeItem();
            }else if (purchase.getSku().equals(PACOTE_50)) {
                consumeItem();
            }else if (purchase.getSku().equals(PACOTE_100)) {
                consumeItem();
            }
        }
    };

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            Purchase purchasePacote100 = inventory.getPurchase(PACOTE_25);
            Purchase purchasePacote200 = inventory.getPurchase(PACOTE_50);
            Purchase purchasePacote500 = inventory.getPurchase(PACOTE_100);

            if (purchasePacote100 != null){
                mHelper.consumeAsync(purchasePacote100, mConsumeFinishedListener);
            }else if (purchasePacote200 != null){
                mHelper.consumeAsync(purchasePacote200, mConsumeFinishedListener);
            }else{
                mHelper.consumeAsync(purchasePacote500, mConsumeFinishedListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {

            String compraDePacote = purchase.getSku();
            Log.i("COMPRA_A", "Aqui =>" + compraDePacote);


            if (compraDePacote.equals(PACOTE_25)) {
                if (result.isSuccess()) {
                    int pacote = 25;
                    new CompraSmsCarteiraTask(context, pacote, 0, 1, PACOTE_25).execute();
                } else {
                    Toast.makeText(context, context.getString(R.string.toast_compra_cancelada), Toast.LENGTH_LONG).show();
                }
            }else if (compraDePacote.equals(PACOTE_50)) {
                if (result.isSuccess()) {
                    int pacote = 50;
                    new CompraSmsCarteiraTask(context, pacote, 0, 1, PACOTE_50).execute();
                } else {
                    Toast.makeText(context, context.getString(R.string.toast_compra_cancelada), Toast.LENGTH_LONG).show();
                }
            }else {
                if (result.isSuccess()) {
                    int pacote = 100;
                    new CompraSmsCarteiraTask(context, pacote, 0, 1, PACOTE_100).execute();
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

        /*Log.d(TAG, "" + resultCode);

        if (resultCode != 0){
            if (requestCode == 10001){
                int pacote = 25;
                new CompraSmsCarteiraTask(context, pacote, 0, 1).execute();
            }else if (requestCode == 10002){
                int pacote = 50;
                new CompraSmsCarteiraTask(context, pacote, 0, 1).execute();
            }else{
                int pacote = 100;
                new CompraSmsCarteiraTask(context, pacote, 0, 1).execute();
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
