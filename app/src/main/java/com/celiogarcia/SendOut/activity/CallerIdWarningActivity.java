package com.celiogarcia.SendOut.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.celiogarcia.SendOut.R;

public class CallerIdWarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_id_warning);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void registar(View view){
        startActivity(new Intent(this, ConfirmarCallerIdActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
