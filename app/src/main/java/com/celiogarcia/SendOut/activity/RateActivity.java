package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.task.ClassificaChamadasTask;

public class RateActivity extends Activity {

    private RatingBar rateCall;
    public static RateActivity rateActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rateActivity = this;
        rateCall = (RatingBar) findViewById(R.id.rate_id);
    }

    public void rateYourCall(View view){
        new ClassificaChamadasTask(rateCall.getRating()).execute();
    }

    public void rateLater(View view){
        finish();
    }
}
