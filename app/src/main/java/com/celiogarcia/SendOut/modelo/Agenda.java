package com.celiogarcia.SendOut.modelo;

import java.io.Serializable;

public class Agenda implements Serializable {
    private String nome;
    private String numero;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String addIndicativo(Configuracao configuracao, String numero) {

        if (numero == null) {
            return null;
        }

        numero = numero.replaceAll("\\s+", "");

        String indicativo = configuracao.indicativo();

        if (numero.substring(0, 1).equals("+") || numero.equals("00")) {
            return numero;
        } else if (numero.substring(0, 1).equals(indicativo.substring(1,2))) {
            return numero;
        } else {
            return indicativo + numero;
        }

    }
}
