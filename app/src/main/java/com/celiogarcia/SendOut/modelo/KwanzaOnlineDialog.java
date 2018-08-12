package com.celiogarcia.SendOut.modelo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.task.CompraSmsCarteiraTask;

public class KwanzaOnlineDialog extends Dialog {

    private Context context;
    private Button button;
    private TextView tvProduto;
    private TextView tvPreco;

    private int position;
    private int pacote;
    private String produtoId;

    public KwanzaOnlineDialog(Context context, int position, int pacote, String produtoId) {
        super(context);
        this.context = context;
        this.position = position;
        this.pacote = pacote;
        this.produtoId = produtoId;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_kwanzaonline);
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvProduto = (TextView) findViewById(R.id.nome_produto);
        tvPreco = (TextView) findViewById(R.id.preco_produto);

        customComponents();

        button = (Button) findViewById(R.id.dialog_ko_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                switch (position){
                    case 0:
                        new CompraSmsCarteiraTask(context, pacote, 0, 0, produtoId).execute();
                        break;

                    case 1:
                        new CompraSmsCarteiraTask(context, pacote, 0, 0, produtoId).execute();
                        break;

                    case 2:
                        new CompraSmsCarteiraTask(context, pacote, 0, 0, produtoId).execute();
                        break;

                    case 3:
                        new CompraSmsCarteiraTask(context, pacote, 1, 0, produtoId).execute();
                        break;

                    case 4:
                        new CompraSmsCarteiraTask(context, pacote, 1, 0, produtoId).execute();
                        break;

                    case 5:
                        new CompraSmsCarteiraTask(context, pacote, 1, 0, produtoId).execute();
                        break;
                }
            }
        });
    }

    private void customComponents(){
        switch (position){
            case 0:
                tvProduto.setText(context.getString(R.string.dialog_ko_pacote_25));
                tvPreco.setText("350,00 KZ");
                break;

            case 1:
                tvProduto.setText(context.getString(R.string.dialog_ko_pacote_50));
                tvPreco.setText("550,00 KZ");
                break;

            case 2:
                tvProduto.setText(context.getString(R.string.dialog_ko_pacote_100));
                tvPreco.setText("980,00 KZ");
                break;

            case 3:
                tvProduto.setText(context.getString(R.string.dialog_ko_pacote_15));
                tvPreco.setText("660,00 KZ");
                break;

            case 4:
                tvProduto.setText(context.getString(R.string.dialog_ko_pacote_30));
                tvPreco.setText("1.305,00 KZ");
                break;

            case 5:
                tvProduto.setText(context.getString(R.string.dialog_ko_pacote_60));
                tvPreco.setText("2.625,00 KZ");
                break;
        }
    }
}
