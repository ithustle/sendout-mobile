package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.activity.SmsContactoActivity;
import com.celiogarcia.SendOut.modelo.Contacto;

import java.util.ArrayList;

/**
 * Created by celiogarcia on 21/07/16.
 */
public class SmsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Contacto> sms;

    public SmsAdapter(Context context, ArrayList<Contacto> sms) {
        this.context = context;
        this.sms = sms;
    }

    @Override
    public int getCount() {
        return sms.size();
    }

    @Override
    public Object getItem(int i) {
        return sms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Contacto mensagem = sms.get(i);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = view;

        if (v == null){
            v = inflater.inflate(R.layout.sms_list, viewGroup, false);
        }

        TextView mensagens = (TextView) v.findViewById(R.id.lista_mensagem_contacto);
        mensagens.setText(mensagem.getMensagem());

        ImageView estadoSms = (ImageView) v.findViewById(R.id.sms_status);
        if (mensagem.getEstado().equals("1")){
            estadoSms.setImageResource(R.drawable.ic_fail);
        }else{
            estadoSms.setImageResource(R.drawable.ic_done_black_36dp);
        }


        return v;
    }
}
