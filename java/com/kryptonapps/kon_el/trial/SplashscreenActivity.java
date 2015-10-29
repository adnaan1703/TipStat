package com.kryptonapps.kon_el.trial;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kryptonapps.kon_el.trial.api.TipstatRestClient;

public class SplashscreenActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);
        context = SplashscreenActivity.this;


        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();

        fillDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    void fillDatabase() {
        TipstatRestClient.getHits(context, null);
        TipstatRestClient.getList(context, HomeActivity.class);
    }
}
