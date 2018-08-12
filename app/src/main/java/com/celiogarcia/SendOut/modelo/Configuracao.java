package com.celiogarcia.SendOut.modelo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by celiogarcia on 17/07/16.
 */
public class Configuracao {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREFERENCES = "Dados";

    public Configuracao(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void registaConta(Contacto contacto){
        editor = sharedPreferences.edit();
        editor.putString("numero", contacto.getNumeroDeTelefone());
        editor.putString("indicativo", contacto.getIndicativo());
        editor.putString("nome", contacto.getNome());           //Remetente
        editor.putBoolean("registo", true);
        editor.commit();
    }

    public String indicativo(){
        editor = sharedPreferences.edit();
        return sharedPreferences.getString("indicativo", null);
    }

    public boolean registoFeito() {
        editor = sharedPreferences.edit();
        return sharedPreferences.getBoolean("registo", false);
    }

    public void contaConfirmada() {
        editor = sharedPreferences.edit();
        editor.putBoolean("confirmacao", true);
        editor.commit();
    }

    public boolean confirmacao(){
        editor = sharedPreferences.edit();
        return sharedPreferences.getBoolean("confirmacao", false);
    }

    public String pegaRemetente(){
        editor = sharedPreferences.edit();
        String remetente = sharedPreferences.getString("nome", null);

        return remetente;
    }

    public String pegaNumeroDoRemetente(){
        editor = sharedPreferences.edit();
        String remetente = sharedPreferences.getString("numero", null);

        return remetente;
    }

    public void guardaFotoDePerfil(String caminho){
        editor = sharedPreferences.edit();
        editor.putString("foto", caminho);
        editor.commit();
    }

    public String verFotoDePerfil(){
        editor = sharedPreferences.edit();
        return sharedPreferences.getString("foto", null);
    }

    public void carregaCarteira(double valor){
        editor = sharedPreferences.edit();

        double saldo = sharedPreferences.getFloat("saldo", 0);
        saldo += valor;

        editor.putFloat("saldo", (float) saldo);
        editor.commit();
    }

    public void descontaCarteira(double valor){
        editor = sharedPreferences.edit();

        double saldo = sharedPreferences.getFloat("saldo", 0);
        saldo -= valor;

        editor.putFloat("saldo", (float) saldo);
        editor.commit();
    }

    public double carteira(){
        editor = sharedPreferences.edit();
        return sharedPreferences.getFloat("saldo", 0);
    }

    public void actualizaCarteira(String carteira) {
        editor = sharedPreferences.edit();
        editor.putFloat("saldo", (float) Double.parseDouble(carteira));
        editor.commit();
    }

    public void adicionaSms(int sms){
        editor = sharedPreferences.edit();

        int smsNaConta = sharedPreferences.getInt("sms", 0);
        smsNaConta += sms;

        editor.putInt("sms", smsNaConta);
        editor.commit();
    }

    public void actualizaSms(int sms){
        editor = sharedPreferences.edit();
        editor.putInt("sms", sms);
        editor.commit();
    }

    public int creditoSms(){
        return (sharedPreferences.getInt("sms", 0) == 0) ? 0 : sharedPreferences.getInt("sms", 0);
    }

    // CRÉDITOS DE VOZ / MINUTOS
    public void adicionaMinutosDeVoz(int minuto){
        editor = sharedPreferences.edit();

        int minutos = sharedPreferences.getInt("minuto", 0);
        minutos += minuto;

        editor.putInt("minuto", minutos);
        editor.commit();
    }

    public int minutosDeVoz(){
        return sharedPreferences.getInt("minuto", 0);
    }

    public void actualizaMinutoDeVoz(int tempoDeChamada) {
        editor = sharedPreferences.edit();
        editor.putInt("minuto", tempoDeChamada);
        editor.commit();
    }

    public void ultimoNumeroChamado(String numero){
        editor = sharedPreferences.edit();
        editor.putString("ultimonumero", numero);
        editor.commit();
    }

    public String pegaUltimoNumeroChamado(){
        return sharedPreferences.getString("ultimonumero", null);
    }

    public boolean hasCallerIdRegistered() {
        return sharedPreferences.getBoolean("callerid", false);
    }

    public void registerCallerId(String codigo){
        editor = sharedPreferences.edit();
        editor.putString("calleridcode", codigo);
        editor.putBoolean("callerid", true);
        editor.putBoolean("shared", false);
        editor.commit();
    }

    public String getCodeCallerId(){
        return sharedPreferences.getString("calleridcode", null);
    }

    public void tipoDePerfil(int tipo){
        editor = sharedPreferences.edit();
        // 0: pessoal ... 1: empresa

        if (tipo == 0){
            editor.putBoolean("pessoal", true);
        }else{
            editor.putBoolean("empresa", true);
        }
        editor.commit();
    }

    public Boolean isPessoal(){
        return sharedPreferences.getBoolean("pessoal", false);
    }

    public Boolean isEmpresa(){
        return sharedPreferences.getBoolean("empresa", false);
    }

    public void validadeDeChamadaDeVoz(String valido) {
        editor = sharedPreferences.edit();
        editor.putString("valido", valido);
        editor.commit();
    }

    public String chamadaDeVozValida(){
        return sharedPreferences.getString("valido", null);
    }

    public void setTokenDevice(String token) {
        editor = sharedPreferences.edit();
        editor.putString("device", token);
        editor.commit();
    }

    public String getTokenDevice() {
        return sharedPreferences.getString("device", null);
    }

    public void setOldTokenDevice(String token){
        editor = sharedPreferences.edit();
        editor.putString("oldtoken", token);
        editor.commit();
    }

    public String getOldTokenDevice(){
        return sharedPreferences.getString("oldtoken", null);
    }

    public void setSharedNumber(boolean b) {
        editor = sharedPreferences.edit();
        editor.putBoolean("shared", b);
        editor.putBoolean("callerid", false);
        editor.commit();
    }

    public boolean isSharedNumber() {
        return sharedPreferences.getBoolean("shared", false);
    }

    public void setInfoActualizada(String s) {
        editor = sharedPreferences.edit();
        editor.putString("good", s);
        editor.putBoolean("infoActualizada", true);
        editor.commit();
    }

    public boolean getInfoActualizada(){
        return sharedPreferences.getBoolean("infoActualizada", false);
    }

    public String getVersaoApp() {
        return sharedPreferences.getString("good", null); // Versão da app
    }
}
