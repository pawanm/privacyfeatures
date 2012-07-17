package com.android.privacycontrol.conroller;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.privacycontrol.utils.Logging;

public class CallListenerManager extends PhoneStateListener
{
    AudioStateManager audioStateManager;
    private Context mContext;

    public CallListenerManager(Context context)
    {
        mContext=context;
    }

    @Override
    public void onCallStateChanged (int state, String incomingNumber)
    {
        audioStateManager = new AudioStateManager();
        if(TelephonyManager.CALL_STATE_RINGING == state)
        {
            Logging.debug("CallListener: Incoming call from " + incomingNumber);
            if(incomingNumber.contains("9015980883"))
            {
                audioStateManager.setPhoneState(mContext,0);
            }
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
