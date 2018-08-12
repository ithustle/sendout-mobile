package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Contacto;

import java.util.ArrayList;

/**
 * Created by Hackersys on 03/08/2016.
 */
public class HistoricoAdapter extends BaseAdapter {

    private final ArrayList<Contacto> historicos;
    private Context context;

    public HistoricoAdapter(Context context, ArrayList<Contacto> historicos){
        this.context = context;
        this.historicos = historicos;
    }

    @Override
    public int getCount() {
        return historicos.size();
    }

    @Override
    public Object getItem(int position) {
        return historicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contacto historico = historicos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.layout_historico_sms, parent, false);
        }

        TextView numero = (TextView) view.findViewById(R.id.historico_numero);
        numero.setText(historico.getNumeroDeTelefone());

        TextView dataChamada = (TextView) view.findViewById(R.id.sms_historico_preview);
        dataChamada.setText(historico.getMensagem());
/*
        TextView tempo = (TextView) view.findViewById(R.id.quantidade_sms_historico);
        tempo.setText(historico.getMensagem().length());*/

        return view;

    }
}
