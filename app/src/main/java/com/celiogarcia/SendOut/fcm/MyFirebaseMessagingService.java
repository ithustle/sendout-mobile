package com.celiogarcia.SendOut.fcm;

import android.util.Log;

import com.celiogarcia.SendOut.activity.ChamadaActivity;
import com.celiogarcia.SendOut.activity.ChamadaEmCurso;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.celiogarcia.SendOut.activity.ChamadaEmCurso.contador;
import static com.celiogarcia.SendOut.activity.ChamadaEmCurso.contagem;
import static com.celiogarcia.SendOut.activity.ChamadaEmCurso.pararContagem;
import static com.celiogarcia.SendOut.handler.MyHandler.runOnUiThread;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0){
            if (data.get("estado_chamada").equals("no-answer") || data.get("estado_chamada").equals("completed") ||
                    data.get("estado_chamada").equals("busy") || data.get("estado_chamada").equals("canceled") ||
                    data.get("estado_chamada").equals("failed")){
                ChamadaActivity.disconnect();
                pararContagem();
                ChamadaEmCurso.chamada.finish();
                Log.d(TAG, "ESTADO: " + data.get("estado_chamada"));
            }

            if (data.get("estado_chamada").equals("in-progress")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contador();
                        contagem();
                    }
                });
            }
        }

    }


}
