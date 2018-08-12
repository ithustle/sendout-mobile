package com.celiogarcia.SendOut.modelo;

import java.io.Serializable;

/**
 * Created by Hackersys on 23/07/2016.
 */
public class Pais implements Serializable {
    private String indicativo;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }
}
