package com.example.alarmy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class welcomepage extends AppCompatActivity {

    private static int splashtime=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(homeintent);
                finish();
            }
        }, splashtime);
    }




}