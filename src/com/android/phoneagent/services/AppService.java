package com.android.phoneagent.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.phoneagent.conroller.CallListenerManager;

public class AppService extends Service
{
    private final IBinder binder = new LocalBinder();
    TelephonyManager telephonyManager;
    CallListenerManager listenerManager;
    PhoneStateListener listener;

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
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listenerManager = new CallListenerManager(this);
        telephonyManager.listen(listenerManager,PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return AppService.START_STICKY;
    }


}
