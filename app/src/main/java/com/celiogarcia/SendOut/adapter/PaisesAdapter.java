package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Pais;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Hackersys on 24/07/2016.
 */
public class PaisesAdapter extends BaseAdapter {
    private final List<Pais> paises;
    private Context context;

    public PaisesAdapter(Context context, List<Pais> paises) {
        this.paises = paises;
        this.context = context;
    }

    @Override
    public int getCount() {
        return paises.size();
    }

    @Override
    public Object getItem(int position) {
        return paises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pais pais = paises.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.paises_lista_indicativos, parent, false);
        }

        TextView nomeDoPais = (TextView) view.findViewById(R.id.paises_pais);
        nomeDoPais.setText(pais.getNome());

        TextView indicativoDoPais = (TextView) view.findViewById(R.id.paises_indicativo);
        indicativoDoPais.setText("(" + pais.getIndicativo() + ")");

        return view;
    }
}
