package com.example.alarmy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private static  long START_TIME_IN_MILLI_SEC;


    TextView timertext;
    ImageButton playbutton,setbutton,resetbutton,backbutton;
    EditText timeinput;

    int timersetTime;

    CountDownTimer countDownTimer;
    boolean Timeruuning;

    private long getStartTimeInMilliSec=START_TIME_IN_MILLI_SEC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //declaration
        timertext=findViewById(R.id.timertext);
        playbutton=findViewById(R.id.playbuttontimer);
        setbutton=findViewById(R.id.setbuttontimer);
        resetbutton=findViewById(R.id.resetbuttontimer);
        timeinput=findViewById(R.id.timeinput);
        backbutton=findViewById(R.id.backbuttontimer);

        //timerInput

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainMenu.class);
                startActivity(i);
                finish();
            }
        });

        setbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timeinput.getText().toString().matches("")){


                setbutton.setVisibility(View.GONE);
                timeinput.setVisibility(View.GONE);
                playbutton.setVisibility(View.VISIBLE);
                timertext.setVisibility(View.VISIBLE);

                START_TIME_IN_MILLI_SEC= Long.parseLong(timeinput.getText().toString())*1000;
                getStartTimeInMilliSec=START_TIME_IN_MILLI_SEC;
                Log.i("",""+START_TIME_IN_MILLI_SEC);

            }
                else {
                    Toast.makeText(Timer.this, "Enter the number of seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Timeruuning){
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

    updateCountdownText();
    }

    private void startTimer(){
        countDownTimer= new CountDownTimer(getStartTimeInMilliSec,1000) {
            @Override
            public void onTick(long l) {
               getStartTimeInMilliSec=l;
               updateCountdownText();
            }

            @Override
            public void onFinish() {

                Timeruuning=false;
                playbutton.setImageDrawable(getResources().getDrawable(R.drawable.playtimer));
                playbutton.setVisibility(View.INVISIBLE);
                setbutton.setVisibility(View.VISIBLE);
                timeinput.setVisibility(View.VISIBLE);
                timertext.setVisibility(View.GONE);
                resetbutton.setVisibility(View.GONE);

            }
        }.start();
    Timeruuning=true;
    playbutton.setImageDrawable(getResources().getDrawable(R.drawable.pausetimer));

    }

    private void updateCountdownText(){
        int minutes=(int) (getStartTimeInMilliSec/1000)/60;
        int seconds=(int) (getStartTimeInMilliSec/1000)%60;

        String timeLeftFormatted= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        timertext.setText(timeLeftFormatted);


    }

    private void pauseTimer(){
        countDownTimer.cancel();
        Timeruuning=false;
        playbutton.setImageDrawable(getResources().getDrawable(R.drawable.playtimer));
        resetbutton.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        getStartTimeInMilliSec=START_TIME_IN_MILLI_SEC;
        updateCountdownText();
        resetbutton.setVisibility(View.INVISIBLE);
    }







}

