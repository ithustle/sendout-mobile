package com.celiogarcia.SendOut.modelo;

import android.content.Context;

import com.celiogarcia.SendOut.R;

import java.util.ArrayList;

/**
 * Created by 92178 on 16/12/2016.
 */
public class Tarifa {

    private final Context context;
    private ArrayList<String> descricao;
    private ArrayList<String> tarifario;

    public Tarifa(Context context){
        this.context = context;
    }

    public ArrayList<String> descricao(boolean isSms){
        descricao = new ArrayList<>();
        if (isSms){
            descricao.add(context.getString(R.string.tarifas_sms_nacional));
            descricao.add(context.getString(R.string.tarifas_sms_internacional));
        }else {
            descricao.add(context.getString(R.string.tarifas_caller_id));
            descricao.add(context.getString(R.string.tarifas_chamada_nacional));
            descricao.add(context.getString(R.string.tarifas_chamada_internacional));
            descricao.add(context.getString(R.string.tarifas_chamada));
        }

        return descricao;
    }

    public ArrayList<String> tarifario(boolean isSms){
        tarifario = new ArrayList<>();
        if (isSms){
            tarifario.add(context.getString(R.string.tarifas_sms));
            tarifario.add(context.getString(R.string.tarifas_sms));
        }else {
            tarifario.add(context.getString(R.string.tarifas));
            tarifario.add(context.getString(R.string.tarifas_minuto));
            tarifario.add(context.getString(R.string.tarifas_minuto));
            tarifario.add(context.getString(R.string.tarifas_detalhe));
        }

        return tarifario;
    }
}
