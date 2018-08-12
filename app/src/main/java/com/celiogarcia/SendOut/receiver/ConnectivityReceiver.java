package com.celiogarcia.SendOut.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.celiogarcia.SendOut.activity.ChamadaActivity.mobileSignalStrength;

/**
 * Created by 92178 on 08/01/2017.
 */

public class ConnectivityReceiver extends BroadcastReceiver {

    private ConnectivityManager connectivityManager;
    public static boolean HAS_CONNECTION = true;
    private NetworkInfo networkInfo;
    public static boolean isDADOS = false;

    public ConnectivityReceiver(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null) {
                HAS_CONNECTION = true;

                if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {

                    isDADOS = true;
                }
            } else {

                HAS_CONNECTION = false;
            }
        }else{
            return;
        }
    }

    public static void sinalDeDados(int x){
        if (isDADOS) {
            mobileSignalStrength(x);
        }
    }
}
