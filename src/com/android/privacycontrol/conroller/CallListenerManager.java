package com.android.privacycontrol.conroller;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallListenerManager extends PhoneStateListener
{
    @Override
    public void onCallStateChanged (int state, String incomingNumber)
    {
        if(TelephonyManager.CALL_STATE_RINGING == state)
        {
            //Incoming call handling
        }
        if(TelephonyManager.CALL_STATE_OFFHOOK == state)
        {
            //Outgoing call handling
        }
        if(TelephonyManager.CALL_STATE_IDLE == state)
        {
            //Device back to normal state (not in a call)
        }
    }
}
