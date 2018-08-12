package com.celiogarcia.SendOut.webservice;

import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.modelo.UserDevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class WebService {

    private static URL url;
    private static URL urlSms;
    private static URL urlActivacao;
    private static URL urlResgate;
    private static URL urlCarregar;
    private static URL urlPagaSms;
    private static URL urlPagaVoz;
    private static URL urlBilling;
    private static URL urlBillingVoz;
    private static URL urlRegisterCallerId;
    private static URL urlTempoChamada;
    private static URL urlConsultaSaldo;
    private static URL urlEnviaMail;
    private static URL urlRateCall;
    private static URL urlCarteira;
    private static URL urlUpdateInfo;

    private static URL urlCampanhas;
    private static URL urlSmsEmpresa;
    private static URL urlLogin;
    private static URL urlPropagar;
    private static URL urlUpdateToken;

    static {
        try {
            url = new URL("https://www.sendoutapp.com/app-mobile/regista");
            urlSms = new URL("https://www.sendoutapp.com/app-mobile/api");
            urlActivacao = new URL("https://www.sendoutapp.com/app-mobile/activacao");
            urlResgate = new URL("https://www.kwanzaonline.co.ao/controller/carregar");
            urlCarregar = new URL("https://www.sendoutapp.com/app-mobile/bridge");
            urlPagaSms = new URL("https://www.sendoutapp.com/app-mobile/paga");
            urlPagaVoz = new URL("https://www.sendoutapp.com/app-mobile/paga-voz");
            urlBilling = new URL("https://www.sendoutapp.com/app-mobile/billing");
            urlBillingVoz = new URL("https://www.sendoutapp.com/app-mobile/billing-voz");
            urlRegisterCallerId = new URL("https://www.sendoutapp.com/app-mobile/register-caller-id");
            urlTempoChamada = new URL("https://www.sendoutapp.com/app-mobile/update-minute");
            urlConsultaSaldo = new URL("https://www.sendoutapp.com/app-mobile/saldo-voz");
            urlEnviaMail = new URL("https://www.sendoutapp.com/controller/controllerEnviarEmail.php");
            urlUpdateToken = new URL("https://www.sendoutapp.com/app-mobile/update-token");
            urlRateCall = new URL("https://www.sendoutapp.com/app-mobile/rate");
            urlCarteira = new URL("https://www.sendoutapp.com/app-mobile/actualiza-carteira");
            urlUpdateInfo = new URL("https://www.sendoutapp.com/app-mobile/actualiza-info");

            //URL de empresas
            urlSmsEmpresa = new URL("https://www.sendoutapp.com/controller/controllerEnviarSMS.php");
            urlCampanhas = new URL("https://www.sendoutapp.com/controller/controllerVerCampanhas.php");
            urlLogin = new URL("https://www.sendoutapp.com/controller/controllerLogin.php");
            urlPropagar = new URL("https://www.sendoutapp.com/controller/controllerPropagar.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String enviaSMS(Contacto contacto, Configuracao configuracao){
        HttpURLConnection connection;

        try {
            if (configuracao.isPessoal()) {
                connection = (HttpURLConnection) urlSms.openConnection();
            }else{
                connection = (HttpURLConnection) urlSmsEmpresa.openConnection();
            }

            String numeroDeTelefone = URLEncoder.encode(contacto.getNumeroDeTelefone().replaceAll("\\W", ""), "UTF-8");
            String sms = URLEncoder.encode(contacto.getMensagem(), "UTF-8");
            String dono = URLEncoder.encode(configuracao.pegaNumeroDoRemetente(), "UTF-8");
            String remete = URLEncoder.encode(configuracao.pegaRemetente(), "UTF-8");
            String api = URLEncoder.encode("y", "UTF-8");

            String postNumero = "numeros=" + numeroDeTelefone;
            String postSms = "&sms=" + sms;
            String postDono = "&de=" + dono;          //alterar a variável de url no doc PHP para "de"
            String postRemete = "&remetente=" + remete;
            String postApi = "&app=" + api;

            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(postNumero.getBytes());
            os.write(postSms.getBytes());
            os.write(postDono.getBytes());
            os.write(postRemete.getBytes());
            os.write(postApi.getBytes());

            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response.append(br.readLine());

            br.close();
            os.close();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String regista(Contacto contacto, UserDevice device){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String numeroDeTelefone = URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");
            String identificaTelefone = URLEncoder.encode(contacto.getUiid(), "UTF-8");
            String pais = URLEncoder.encode(contacto.getPais(), "UTF-8");
            String prefixo = URLEncoder.encode(contacto.getIndicativo(), "UTF-8");

            //DEVICE INFORMATION
            String marca = URLEncoder.encode(device.MARCA, "UTF-8");
            String modelo = URLEncoder.encode(device.MODELO, "UTF-8");
            String aparelho = URLEncoder.encode(device.APARELHO, "UTF-8");
            String fabricante = URLEncoder.encode(device.FABRICANTE, "UTF-8");
            String android_os = URLEncoder.encode(device.ANDROID_SO, "UTF-8");
            String versao_app = URLEncoder.encode(device.APP_VERSAO, "UTF-8");
            String codigo_versao = URLEncoder.encode(String.valueOf(device.APP_VERSAO_CODIGO), "UTF-8");
            String tamanho_ecra = URLEncoder.encode(device.screenSizeDevice(), "UTF-8");
            String dpi = URLEncoder.encode(String.valueOf(device.dpi()), "UTF-8");

            String postNumero = "numero=" + numeroDeTelefone;
            String postUiid = "&uiid=" + identificaTelefone;
            String postPais = "&pais=" + pais;
            String postPrefixo = "&prefixo=" + prefixo;

            //DEVICE INFORMATION
            String postMarca = "&marca=" + marca;
            String postModelo = "&modelo=" + modelo;
            String postAparelho = "&aparelho=" + aparelho;
            String postFabricante = "&fabricante=" + fabricante;
            String postAndroid = "&android=" + android_os;
            String postAppVersao = "&versao=" + versao_app;
            String postCodigoVersao = "&codigo=" + codigo_versao;
            String postEcra = "&ecra=" + tamanho_ecra;
            String postDpi = "&dpi=" + dpi;


            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(postNumero.getBytes());
            os.write(postUiid.getBytes());
            os.write(postPais.getBytes());
            os.write(postPrefixo.getBytes());

            //DEVICE INFO
            os.write(postMarca.getBytes());
            os.write(postModelo.getBytes());
            os.write(postAparelho.getBytes());
            os.write(postFabricante.getBytes());
            os.write(postAndroid.getBytes());
            os.write(postAppVersao.getBytes());
            os.write(postCodigoVersao.getBytes());
            os.write(postEcra.getBytes());
            os.write(postDpi.getBytes());

            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response.append(br.readLine());

            br.close();
            os.close();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String activacao(Contacto contacto, String codigo){
        try {
            HttpURLConnection connection = (HttpURLConnection) urlActivacao.openConnection();

            String code = "codigo=" + URLEncoder.encode(codigo, "UTF-8");
            String telefone = "&numero=" + URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(code.getBytes());
            os.write(telefone.getBytes());

            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response.append(br.readLine());

            br.close();
            os.close();


            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String resgate(String codigo){
        try {
            HttpURLConnection connection = (HttpURLConnection) urlResgate.openConnection();

            String code = "codigo=" + URLEncoder.encode(codigo, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(code.getBytes());

            StringBuilder valorDoCartao = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            valorDoCartao.append(br.readLine());

            br.close();
            os.close();

            return String.valueOf(valorDoCartao);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String carregaConta(String valor, String telefone){
        try {
            HttpURLConnection connection = (HttpURLConnection) urlCarregar.openConnection();

            String urlValor = "valor=" + URLEncoder.encode(valor, "UTF-8");
            String urlTelefone = "&telefone=" + URLEncoder.encode(telefone, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlValor.getBytes());
            os.write(urlTelefone.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String pagaSms(Contacto contacto, int pacote, int tipo){
        try {

            HttpURLConnection connection;
            if (tipo == 0) {
                connection = (HttpURLConnection) urlPagaSms.openConnection();
            }else{
                connection = (HttpURLConnection) urlPagaVoz.openConnection();
            }

            String urlTelefone = "telefone=" + URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");
            String urlPacote = "&pacote=" + URLEncoder.encode(String.valueOf(pacote), "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlTelefone.getBytes());
            os.write(urlPacote.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String billing(Contacto contacto, int pacote, int tipo, String ID){
        try {
            HttpURLConnection connection;
            if (tipo == 0) {
                connection = (HttpURLConnection) urlBilling.openConnection();
            }else{
                connection = (HttpURLConnection) urlBillingVoz.openConnection();
            }

            String urlTelefone = "telefone=" + URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");
            String urlPacote = "&pacote=" + URLEncoder.encode(String.valueOf(pacote), "UTF-8");
            String urlId = "&id=" + URLEncoder.encode(ID, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlTelefone.getBytes());
            os.write(urlPacote.getBytes());
            os.write(urlId.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String registaCallerId(Contacto contacto, int codigo){
        try {

            HttpURLConnection connection = (HttpURLConnection) urlRegisterCallerId.openConnection();

            String numeroDeTelefone = URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");
            String codigoUrl = URLEncoder.encode(String.valueOf(codigo), "UTF-8");

            String postNumero = "numero=" + numeroDeTelefone;
            String postCodigo = "&codigo=" + codigoUrl;

            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(postNumero.getBytes());
            os.write(postCodigo.getBytes());

            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response.append(br.readLine());

            br.close();
            os.close();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String tempoDeChamada(Contacto contacto, String para, String data){
        try {
            HttpURLConnection connection = (HttpURLConnection) urlTempoChamada.openConnection();

            String urlNumero = "numero=" + URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");
            String urlPara = "&para=" + URLEncoder.encode(para, "UTF-8");
            String urlData = "&data=" + URLEncoder.encode(data, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());
            os.write(urlPara.getBytes());
            os.write(urlData.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String verificaSaldo(String contacto) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlConsultaSaldo.openConnection();

            String urlNumero = "numero=" + URLEncoder.encode(contacto, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String enviarMail(String contacto, String nome, String email, String assunto, String mensagem) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlEnviaMail.openConnection();

            String urlNumero = "telf_contacto=" + URLEncoder.encode(contacto, "UTF-8");
            String urlNome = "&nome_contacto=" + URLEncoder.encode(nome, "UTF-8");
            String urlEmail = "&email_contacto=" + URLEncoder.encode(email, "UTF-8");
            String urlAssunto = "&assunto_contacto=" + URLEncoder.encode(assunto, "UTF-8");
            String urlMensagem = "&mensagem_contacto=" + URLEncoder.encode(mensagem, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());
            os.write(urlNome.getBytes());
            os.write(urlEmail.getBytes());
            os.write(urlAssunto.getBytes());
            os.write(urlMensagem.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String login(String numero, String senha){
        try {
            HttpURLConnection connection = (HttpURLConnection) urlLogin.openConnection();
            String app = "so";

            String urlValor = "username=" + URLEncoder.encode(numero, "UTF-8");
            String urlTelefone = "&senhadeacesso=" + URLEncoder.encode(senha, "UTF-8");
            String urlApp = "&app=" + URLEncoder.encode(app, "UTF-8");

            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlValor.getBytes());
            os.write(urlTelefone.getBytes());
            os.write(urlApp.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String buscaCampanhas(String contacto) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlCampanhas.openConnection();

            String urlNumero = "telefone=" + URLEncoder.encode(contacto, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String propagarCampanha(String contacto, String id) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlPropagar.openConnection();

            String urlNumero = "telefone=" + URLEncoder.encode(contacto, "UTF-8");
            String urlId = "&id=" + URLEncoder.encode(id, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());
            os.write(urlId.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String updateToken(String numeroDoRemetente, String token) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlUpdateToken.openConnection();

            String urlNumero = "numero=" + URLEncoder.encode(numeroDoRemetente, "UTF-8");
            String urlId = "&device=" + URLEncoder.encode(token, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());
            os.write(urlId.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String rateACall(float rate) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlRateCall.openConnection();

            String urlRate = "rate=" + URLEncoder.encode(String.valueOf(rate), "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlRate.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String actualizaCarteira(String contacto) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlCarteira.openConnection();

            String urlNumero = "numero=" + URLEncoder.encode(contacto, "UTF-8");

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(urlNumero.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder.append(br.readLine());

            br.close();
            os.close();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String actualizaInfo(Contacto contacto, UserDevice device){
        try {
            HttpURLConnection connection = (HttpURLConnection) urlUpdateInfo.openConnection();

            String numeroDeTelefone = URLEncoder.encode(contacto.getNumeroDeTelefone(), "UTF-8");

            //DEVICE INFORMATION
            String marca = URLEncoder.encode(device.MARCA, "UTF-8");
            String modelo = URLEncoder.encode(device.MODELO, "UTF-8");
            String aparelho = URLEncoder.encode(device.APARELHO, "UTF-8");
            String fabricante = URLEncoder.encode(device.FABRICANTE, "UTF-8");
            String android_os = URLEncoder.encode(device.ANDROID_SO, "UTF-8");
            String versao_app = URLEncoder.encode(device.APP_VERSAO, "UTF-8");
            String codigo_versao = URLEncoder.encode(String.valueOf(device.APP_VERSAO_CODIGO), "UTF-8");
            String tamanho_ecra = URLEncoder.encode(device.screenSizeDevice(), "UTF-8");
            String dpi = URLEncoder.encode(String.valueOf(device.dpi()), "UTF-8");

            String postNumero = "numero=" + numeroDeTelefone;

            //DEVICE INFORMATION
            String postMarca = "&marca=" + marca;
            String postModelo = "&modelo=" + modelo;
            String postAparelho = "&aparelho=" + aparelho;
            String postFabricante = "&fabricante=" + fabricante;
            String postAndroid = "&android=" + android_os;
            String postAppVersao = "&versao=" + versao_app;
            String postCodigoVersao = "&codigo=" + codigo_versao;
            String postEcra = "&ecra=" + tamanho_ecra;
            String postDpi = "&dpi=" + dpi;


            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = connection.getOutputStream();
            os.write(postNumero.getBytes());

            //DEVICE INFO
            os.write(postMarca.getBytes());
            os.write(postModelo.getBytes());
            os.write(postAparelho.getBytes());
            os.write(postFabricante.getBytes());
            os.write(postAndroid.getBytes());
            os.write(postAppVersao.getBytes());
            os.write(postCodigoVersao.getBytes());
            os.write(postEcra.getBytes());
            os.write(postDpi.getBytes());

            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response.append(br.readLine());

            br.close();
            os.close();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
