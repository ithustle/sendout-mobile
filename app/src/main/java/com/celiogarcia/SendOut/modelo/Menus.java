package com.celiogarcia.SendOut.modelo;

import com.celiogarcia.SendOut.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hackersys on 03/08/2016.
 */
public class Menus {

    public Menus(){
        //this.view = view;
    }


    public List<Integer> getSettings() {

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.string.settings_registar);
        arrayList.add(R.string.settings_credito);
        arrayList.add(R.string.settings_numero);
        arrayList.add(R.string.contacts);
        arrayList.add(R.string.settings_acerca);

        return arrayList;
    }

    public List<Integer> getMenus() {

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.string.menu_nova_mensagem);
        arrayList.add(R.string.menu_campanha);
        arrayList.add(R.string.menu_historico);
        arrayList.add(R.string.settings_comprar);
        arrayList.add(R.string.settings_comprar_voz);
        arrayList.add(R.string.settings);

        return arrayList;
    }

    public ArrayList<Integer> getIconPath() {

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.ic_caller_id);
        arrayList.add(R.drawable.ic_sms_info);
        arrayList.add(R.drawable.ic_profile);
        arrayList.add(R.drawable.support);
        arrayList.add(R.drawable.ic_about);

        return arrayList;
    }

    public ArrayList<Integer> getIconMenuPath() {

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.ic_sms);
        arrayList.add(R.drawable.ic_campaigning);
        arrayList.add(R.drawable.ic_history);
        arrayList.add(R.drawable.ic_buy_sms);
        arrayList.add(R.drawable.ic_buy_call);
        arrayList.add(R.drawable.ic_action_more);

        return arrayList;
    }
}
