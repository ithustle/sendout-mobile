package com.celiogarcia.SendOut.modelo;

import java.io.Serializable;

/**
 * Created by celiogarcia on 18/07/16.
 */
public class Contacto implements Serializable{
    private String nome;
    private String numeroDeTelefone;
    private String mensagem;
    private String uiid;
    private String indicativo;
    private String pais;
    private String estado;
    private int quantidadeDeMensagem;

    public int getQuantidadeDeMensagem() {
        return quantidadeDeMensagem;
    }

    public void setQuantidadeDeMensagem(int quantidadeDeMensagem) {
        this.quantidadeDeMensagem = quantidadeDeMensagem;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }

    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroDeTelefone() {
        return numeroDeTelefone;
    }

    public void setNumeroDeTelefone(String numeroDeTelefone) {
        this.numeroDeTelefone = numeroDeTelefone;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
