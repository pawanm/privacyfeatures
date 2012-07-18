package com.android.privacycontrol.conroller;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.privacycontrol.entities.DeviceContact;
import com.android.privacycontrol.utils.Logging;

import java.util.List;

public class CallListenerManager extends PhoneStateListener
{
    AudioStateManager audioStateManager;
    ContactManager contactManager;
    private Context mContext;
    List<DeviceContact> favouriteList;

    public CallListenerManager(Context context)
    {
        mContext=context;
        audioStateManager = new AudioStateManager();
        contactManager = new ContactManager(mContext);
    }


    @Override
    public void onCallStateChanged (int state, String incomingNumber)
    {
        if(TelephonyManager.CALL_STATE_RINGING == state)
        {
            Logging.debug("CallListener: Incoming call from " + incomingNumber);
            audioStateManager.setPhoneState(mContext,getNumberStatus(incomingNumber));
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

    private int getNumberStatus(String incomingNo)
    {
        List<DeviceContact> list = contactManager.getRestrictedContactList();
        for(DeviceContact contact: list)
        {
            if(incomingNo.contains(contact.getContactNumber()))
            {
                return -1;
            }
        }

        list = contactManager.getFavouriteList();
        for(DeviceContact contact: list)
        {
            if(incomingNo.contains(contact.getContactNumber()))
            {
                return 1;
            }
        }

        return 0;
    }
}
