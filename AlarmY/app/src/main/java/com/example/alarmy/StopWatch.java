package com.example.alarmy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class StopWatch extends AppCompatActivity {
 Chronometer chronometer;
 ImageButton playbutton,lapbutton,stopbutton,backbutton;
 Handler handler;
 private boolean isResume;
 long tMillisec,tStart,tbuff,tupdate=0L;
 int sec,min,millisec;

TextView laptimingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        chronometer= findViewById(R.id.chronometer);
        playbutton=findViewById(R.id.playbutton);
        lapbutton=findViewById(R.id.lapbutton);
        stopbutton=findViewById(R.id.stopbutton);
        laptimingText=findViewById(R.id.laptiming);
        backbutton=findViewById(R.id.backbuttonstopwatch);


        handler=new Handler();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainMenu.class);
                startActivity(i);
                finish();
            }
        });

        lapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laptimingText.setText(laptimingText.getText() + "\n" + laptimingText.getText().toString().split("\n").length + ". " + chronometer.getText());
            }
        });

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    tStart= SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    isResume=true;
                    playbutton.setImageDrawable(getResources().getDrawable(R.drawable.pausebutton));
                    lapbutton.setVisibility(View.VISIBLE);
                    stopbutton.setVisibility(View.VISIBLE);
                }
                else {
                    tbuff+=tMillisec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume=false;
                    playbutton.setImageDrawable(getResources().getDrawable(R.drawable.playbutton));

                }
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResume){
                    stopbutton.setVisibility(View.GONE);
                    playbutton.setImageDrawable(getResources().getDrawable(R.drawable.playbutton));
                    lapbutton.setVisibility(View.GONE);
                    tMillisec=0;
                    tbuff=0;
                    tStart=0;
                    tupdate=0;
                    millisec=0;
                    sec=0;
                    min=0;
                    chronometer.setText("00:00:00");

                }
            }
        });

    }

    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            tMillisec= SystemClock.uptimeMillis() -tStart;
            tupdate=tbuff + tMillisec;
            sec=(int) (tupdate/1000);
            min=sec/60;
            //sec=sec/60;
            millisec= (int) (tupdate%100);
            chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",millisec));
            handler.postDelayed(this,60);
        }
    };
}