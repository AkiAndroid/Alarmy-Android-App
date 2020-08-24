package com.example.alarmy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class MainMenu extends AppCompatActivity {

    ImageButton alarmbutton,stopwatchbutton,timerbuttton;
    VideoView videoview;
    MediaPlayer mplayer;
    int currentvideoposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        alarmbutton=findViewById(R.id.alarmbutton);
        stopwatchbutton=findViewById(R.id.stopwatchbutton);
        timerbuttton=findViewById(R.id.timrbutton);

        videoview=(VideoView) findViewById(R.id.videovView2);
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background_video);
        videoview.setVideoURI(uri);
        videoview.start();

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

            @Override
            public void onPrepared(MediaPlayer mp) {
                mplayer=mp;

                mplayer.setLooping(true);
                if (currentvideoposition!=0){
                    mplayer.seekTo(currentvideoposition);
                    mplayer.start();
                }
            }
        });

        alarmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        stopwatchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),StopWatch.class);
                startActivity(i);
                finish();
            }
        });

        timerbuttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Timer.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        currentvideoposition= mplayer.getCurrentPosition();
        videoview.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoview.start();
    }
}