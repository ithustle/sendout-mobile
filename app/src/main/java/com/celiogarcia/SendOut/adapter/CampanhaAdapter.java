package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Campanha;

import java.util.ArrayList;

/**
 * Created by 92178 on 14/12/2016.
 */

public class CampanhaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Campanha> campanhas;

    public CampanhaAdapter(Context context, ArrayList campanhas){
        this.context = context;
        this.campanhas = campanhas;
    }

    @Override
    public int getCount() {
        return campanhas.size();
    }

    @Override
    public Object getItem(int position) {
        return campanhas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Campanha campanhas = this.campanhas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.layout_campanhas_lista, parent, false);
        }

        TextView nomeDaCampanha = (TextView) view.findViewById(R.id.nome_campanha);
        nomeDaCampanha.setText(campanhas.getCampanha());

        TextView texto = (TextView) view.findViewById(R.id.campanha_id);
        texto.setText(campanhas.getCodigo());

        return view;
    }
}
