package com.celiogarcia.SendOut.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 92178 on 06/12/2016.
 */

public class DataHelper {

    private static SimpleDateFormat sdf;

    public static String hoje(){
        Calendar c = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    public static String horaActual(){
        Calendar c = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    public static String dataDeHoje(){
        Calendar c = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
}
