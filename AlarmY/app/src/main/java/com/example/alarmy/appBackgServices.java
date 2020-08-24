package com.example.alarmy;

import android.app.Application;
import android.content.Intent;

public class appBackgServices extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this,SERVICES.class));
    }
}
