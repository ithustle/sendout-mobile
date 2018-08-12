package com.celiogarcia.SendOut.listener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;

import static com.celiogarcia.SendOut.activity.ChamadaActivity.STUCK_CALL;
import static com.celiogarcia.SendOut.activity.ChamadaActivity.mobileSignalStrength;
import static com.celiogarcia.SendOut.receiver.ConnectivityReceiver.sinalDeDados;

public class PhoneListener extends PhoneStateListener {

    private static Context context;
    public static int SINAL_DE_DADOS;
    private static int nivel;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public PhoneListener(Context context){
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);

        if (signalStrength.isGsm()) {
            if (signalStrength.getGsmSignalStrength() != 99)
                SINAL_DE_DADOS = signalStrength.getGsmSignalStrength() * 2 - 113;
            else
                SINAL_DE_DADOS = signalStrength.getGsmSignalStrength();
        } else {
            SINAL_DE_DADOS = signalStrength.getCdmaDbm();
        }

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    onSignalLevelWifi();
                }

                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                    sinalDeDados(SINAL_DE_DADOS);
                }
            }
        }

        return;
    }

    public static int onSignalLevelWifi(){
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            nivel = wifiInfo.getRssi();
            mobileSignalStrength(nivel);

        }catch (SecurityException e){
            Toast.makeText(context, context.getString(R.string.toast_no_location_given), Toast.LENGTH_LONG).show();
            STUCK_CALL = true;
        }
        return -113;
    }
}
