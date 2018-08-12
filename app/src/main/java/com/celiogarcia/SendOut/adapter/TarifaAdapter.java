package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Tarifa;

/**
 * Created by 92178 on 16/12/2016.
 */
public class TarifaAdapter extends BaseAdapter{

    private final Context context;
    private final Tarifa tarifas;
    private final boolean isSms;

    public TarifaAdapter(Context context, Tarifa tarifas, boolean isSms){
        this.context = context;
        this.tarifas = tarifas;
        this.isSms = isSms;
    }

    @Override
    public int getCount() {
        return tarifas.descricao(isSms).size();
    }

    @Override
    public Object getItem(int position) {
        return tarifas.descricao(isSms).get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.layout_tarifas, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.tarifas_descricao);
        textView.setText(tarifas.descricao(isSms).get(position));

        TextView textView1 = (TextView) view.findViewById(R.id.tarifas_limb);
        textView1.setText(tarifas.tarifario(isSms).get(position));

        return view;
    }
}
