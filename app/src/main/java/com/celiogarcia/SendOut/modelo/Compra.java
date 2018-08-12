package com.celiogarcia.SendOut.modelo;

import java.util.ArrayList;

/**
 * Created by 92178 on 08/10/2016.
 */

public class Compra {
    private double preco;

    public ArrayList<String> getInformacao(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Cartão KO");
        /*arrayList.add("Crédito de chamadas");*/
        arrayList.add("Tarifas");

        return arrayList;
    }
}
