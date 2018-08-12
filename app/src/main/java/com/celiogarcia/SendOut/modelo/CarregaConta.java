package com.celiogarcia.SendOut.modelo;

/**
 * Created by 92178 on 10/10/2016.
 */

public class CarregaConta {
    private String codigo;
    private double valor;
    private int sms;

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getSms() {
        return sms;
    }
}
