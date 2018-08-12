package com.celiogarcia.SendOut.handler;

import android.content.Context;
import android.os.Handler;

/**
 * Created by 92178 on 28/12/2016.
 */

public class MyHandler {
    private static Handler handler;

    public MyHandler(Context context) {
        this.handler = new Handler(context.getMainLooper());
    }

    public static void runOnUiThread(Runnable r){
        handler.post(r);
    }
}
