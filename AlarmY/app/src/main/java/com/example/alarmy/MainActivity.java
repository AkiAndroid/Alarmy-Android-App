package com.example.alarmy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    TimePicker time_picker;
    TextView text_view;
    ImageButton set_button,offbutton,cancelbutton;
    public static AlarmManager alarmManager;
    ImageButton repeatbutton;
    public static PendingIntent pendingIntent;
    ImageButton ringtonebutton,backbutton;
    public int ringtonenumber=0;


    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancelbutton=findViewById(R.id.cancelbutton);
        time_picker = (TimePicker) findViewById(R.id.timer);
        text_view = (TextView) findViewById(R.id.timetext);
        set_button = (ImageButton) findViewById(R.id.setbutton);
        repeatbutton = (ImageButton) findViewById(R.id.repeatbutton);
        ringtonebutton=findViewById(R.id.musicbutton);
        offbutton = (ImageButton) findViewById(R.id.offbutton);
        backbutton=findViewById(R.id.backbuttonalarm);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainMenu.class);
                startActivity(i);
                finish();
            }
        });


        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelbutton.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, broadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 24444, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                Toast.makeText(MainActivity.this, "Alarm has been Cancelled", Toast.LENGTH_SHORT).show();

            }
        });

        if (SERVICES.alarmSetted == true)
        {
            cancelbutton.setVisibility(View.VISIBLE);
        }
        else
        {
            cancelbutton.setVisibility(View.GONE);
        }

        if (broadcastReceiver.isRingtoneOn)
        {
            offbutton.setVisibility(View.VISIBLE);
            time_picker.setVisibility(View.GONE);
            text_view.setVisibility(View.GONE);
            set_button.setVisibility(View.GONE);
            repeatbutton.setVisibility(View.GONE);
            ringtonebutton.setVisibility(View.GONE);

        }
        else
        {
            offbutton.setVisibility(View.GONE);

        }

        offbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Alarm turned Off", Toast.LENGTH_SHORT).show();

                if(!SERVICES.is_repeated)
                {
                    SERVICES.alarmSetted = false;
                }

                broadcastReceiver.RingtonePlayer.stop();
                broadcastReceiver.isRingtoneOn= false;

                MainActivity.this.finish();

            }


        });

        ringtonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.ringtone_layout);

                List<String> ringtoneList =new ArrayList<>();

                ringtoneList.add("PIKACHU");
                ringtoneList.add("KGF");
                ringtoneList.add("PACHAI NIRAME");
                ringtoneList.add("I M RIDER");

                ListView ringtonelictview=dialog.findViewById(R.id.ringtonelit);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1,ringtoneList);
                ringtonelictview.setAdapter(adapter);

                ringtonelictview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ringtonenumber = i;
                        Log.i("work",""+ringtonenumber);
                        dialog.dismiss();
                    }
                });

                dialog.show();







            }
        });

        time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int ahour, int aminute) {

                hour = ahour;
                minute = aminute;

                text_view.setText("TIME:" + " " + " " + hour + ":" + minute);
            }
        });

        repeatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SERVICES.is_repeated){
                    SERVICES.is_repeated = true;
                    Toast.makeText(MainActivity.this, "It will repeat every 24 hrs", Toast.LENGTH_SHORT).show();
                }
                else {
                    SERVICES.is_repeated=false;
                    Toast.makeText(MainActivity.this, "Alarm won't repeat", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void setAlarm(View v) {

        if (SERVICES.alarmSetted == false) {

            SERVICES.alarmSetted = true;
            Toast.makeText(this, "Alarm has been Set", Toast.LENGTH_SHORT).show();
            cancelbutton.setVisibility(View.VISIBLE);

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Date date = new Date();

            Calendar alarm_cal = Calendar.getInstance();
            Calendar now_cal = Calendar.getInstance();

            //alarm_cal.setTime(date);
            //now_cal.setTime(date);

            alarm_cal.set(Calendar.HOUR_OF_DAY, hour);
            alarm_cal.set(Calendar.MINUTE, minute);
            alarm_cal.set(Calendar.SECOND, 0);

            if (alarm_cal.before(now_cal)) {
                alarm_cal.add(Calendar.DATE, 1);
            }

            Intent intent = new Intent(MainActivity.this, broadcastReceiver.class);
            intent.putExtra("ringtoneNumber", ringtonenumber);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 24444, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_cal.getTimeInMillis(), pendingIntent);
        }
        else {
            Toast.makeText(this, "Alarm has already been SET", Toast.LENGTH_SHORT).show();
        }
    }





}


