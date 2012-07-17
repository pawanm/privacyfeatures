package com.android.privacycontrol.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.android.privacycontrol.conroller.CallListenerManager;

public class AppService extends Service
{
    private final IBinder binder = new LocalBinder();
    private static CallListenerManager callListener;

    private class LocalBinder extends Binder
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }


    @Override
    public void onCreate()
    {
        callListener = new CallListenerManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return AppService.START_STICKY;
    }


}
