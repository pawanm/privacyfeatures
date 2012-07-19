package com.android.phoneagent.services;

import android.app.Application;
import android.content.Intent;


public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        this.startService(new Intent(this, AppService.class));
    }
}

