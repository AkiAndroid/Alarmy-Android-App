package com.example.alarmy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.security.Provider;

public class SERVICES extends Service {
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    public static boolean alarmSetted;
    public static boolean is_repeated;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // do your jobs here

        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }
    private  void alarmIntimation(){

    }

    private void startForeground() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID, "Bg Services", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

       startForeground(NOTIF_ID, new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.alarmnoticon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Alarmy in running background")
                .setContentIntent(pendingIntent)
                .build());
    }




}
