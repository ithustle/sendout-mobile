package com.celiogarcia.SendOut.task;

import android.os.AsyncTask;

import com.celiogarcia.SendOut.activity.RateActivity;
import com.celiogarcia.SendOut.webservice.WebService;

public class ClassificaChamadasTask extends AsyncTask<Object, Object, String> {

    private final float rate;

    public ClassificaChamadasTask(float rate) {
        this.rate = rate;
    }

    @Override
    protected String doInBackground(Object... objects) {

        WebService webService = new WebService();
        String resposta = webService.rateACall(rate);

        return resposta;
    }

    @Override
    protected void onPostExecute(String s) {
        RateActivity.rateActivity.finish();
    }
}