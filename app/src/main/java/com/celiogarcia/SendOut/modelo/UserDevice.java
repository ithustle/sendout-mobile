package com.celiogarcia.SendOut.modelo;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import static com.celiogarcia.SendOut.BuildConfig.VERSION_CODE;
import static com.celiogarcia.SendOut.BuildConfig.VERSION_NAME;

/**
 * Created by 92178 on 11/01/2017.
 */

public class UserDevice {

    public static String MARCA = Build.BRAND;
    public static String MODELO = Build.MODEL;
    public static String APARELHO = Build.DEVICE;
    public static String APP_VERSAO = VERSION_NAME;
    public static int APP_VERSAO_CODIGO = VERSION_CODE;
    public static String FABRICANTE = Build.MANUFACTURER;
    public static String ANDROID_SO = "Android " + Build.VERSION.RELEASE;
    public  Activity activity;

    public UserDevice(Activity activity){
        this.activity = activity;
    }

    public String screenSizeDevice(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x +" x " + size.y;
    }

    public int dpi(){
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        return (int) (metrics.density * 160f);
    }
}
