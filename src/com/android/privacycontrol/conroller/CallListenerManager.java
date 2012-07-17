package com.android.privacycontrol.conroller;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.privacycontrol.utils.Logging;

public class CallListenerManager extends PhoneStateListener
{
    @Override
    public void onCallStateChanged (int state, String incomingNumber)
    {
        if(TelephonyManager.CALL_STATE_RINGING == state)
        {
            Logging.debug("CallListener: Incoming call from " + incomingNumber);
        }
        if(TelephonyManager.CALL_STATE_OFFHOOK == state)
        {
            Logging.debug("CallListener: Outgoing");
        }
        if(TelephonyManager.CALL_STATE_IDLE == state)
        {
            Logging.debug("CallListener: Idle");
        }
    }
}
