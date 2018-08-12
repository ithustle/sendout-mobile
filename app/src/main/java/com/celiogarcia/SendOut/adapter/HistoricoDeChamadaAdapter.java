package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.HistoricoChamadas;

import java.util.ArrayList;

public class HistoricoDeChamadaAdapter extends BaseAdapter {

    private final ArrayList<HistoricoChamadas> chamadas;
    private final Context context;

    public HistoricoDeChamadaAdapter(Context context, ArrayList<HistoricoChamadas> chamadas){
        this.context = context;
        this.chamadas = chamadas;
    }

    @Override
    public int getCount() {
        return chamadas.size();
    }

    @Override
    public Object getItem(int position) {
        return chamadas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HistoricoChamadas historico = chamadas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.layout_historico_chamadas, parent, false);
        }

        TextView numero = (TextView) view.findViewById(R.id.historico_chamada_numero);
        numero.setText(historico.getNumero());

        TextView dataChamada = (TextView) view.findViewById(R.id.historico_chamada_data);
        dataChamada.setText(historico.getDataChamada());

        TextView tempo = (TextView) view.findViewById(R.id.historico_chamada_tempo);
        tempo.setText(historico.getTempoDeChamada());

        return view;
    }
}
