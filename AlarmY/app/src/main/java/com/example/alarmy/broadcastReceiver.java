package com.example.alarmy;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

public class broadcastReceiver extends BroadcastReceiver {

    public static boolean alarmRepeater;
    public  static Ringtone ringtone;
    public static boolean isRingtoneOn;

    public static MediaPlayer RingtonePlayer;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

isRingtoneOn=true;
        Intent i=new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        int ringtonenumber = intent.getIntExtra("ringtoneNumber",0);

        Log.i("working", ""+ringtonenumber);


        Vibrator vibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.alarmnoticon)
                //example for large icon
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("my title")
                .setContentText("my message")
                .setOngoing(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (ringtonenumber==0){
            RingtonePlayer = MediaPlayer.create(context,R.raw.pikachu_latest);
        }
        else if(ringtonenumber==1){
            RingtonePlayer = MediaPlayer.create(context,R.raw.kgf_ringtone);
        }
        else if (ringtonenumber==2){
            RingtonePlayer = MediaPlayer.create(context,R.raw.alai_payuthey);
        }
        else {
            RingtonePlayer = MediaPlayer.create(context,R.raw.i_am_rider);
        }

        RingtonePlayer.start();


        //Uri notificationRING= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        //ringtone=RingtoneManager.getRingtone(context,notificationRING);
        //ringtone.play();

        if (SERVICES.is_repeated)
        {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Date date = new Date();

            Calendar alarm_cal = Calendar.getInstance();
            Calendar now_cal = Calendar.getInstance();


            if (alarm_cal.before(now_cal)) {
                alarm_cal.add(Calendar.DATE, 1);
            }

            Intent intents = new Intent(context, broadcastReceiver.class);
            intent.putExtra("ringtoneNumber", ringtonenumber);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 24444, intents, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_cal.getTimeInMillis(), pendingIntent);
        }


    }
}
