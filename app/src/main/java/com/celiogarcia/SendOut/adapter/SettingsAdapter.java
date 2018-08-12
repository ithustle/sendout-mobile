package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Menus;

import java.util.List;

/**
 * Created by Hackersys on 03/08/2016.
 */
public class SettingsAdapter extends BaseAdapter {

    private final List<Integer> listaSettings;
    private Context context;

    public SettingsAdapter(Context context, List<Integer> listaSettings){
        this.listaSettings = listaSettings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaSettings.size();
    }

    @Override
    public Object getItem(int position) {
        return listaSettings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Menus men = new Menus();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.settings_lista, parent, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.img_settings);
        imageView.setImageResource(men.getIconPath().get(position));

        TextView textView = (TextView) view.findViewById(R.id.txt_settings);
        textView.setText(men.getSettings().get(position));

        return view;

    }
}
