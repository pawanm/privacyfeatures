package com.android.phoneagent.conroller;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.phoneagent.entities.ContactState;
import com.android.phoneagent.entities.DeviceContact;
import com.android.phoneagent.factory.AppFactory;
import com.android.phoneagent.utils.Logging;

import java.util.List;

public class CallListenerManager extends PhoneStateListener
{
    private AudioStateManager audioStateManager;
    private ContactManager contactManager;
    private CallLogManager callLogManager;
    private int currentRingerMode;
    private boolean ringerModeChangedFlag=false;
    private String lastCallNo;

    public CallListenerManager(Context context)
    {
        audioStateManager = new AudioStateManager(context);
        contactManager = AppFactory.getContactManager(context);
        callLogManager = new CallLogManager(context);
    }


    @Override
    public void onCallStateChanged (int state, String incomingNumber)
    {
        if(TelephonyManager.CALL_STATE_RINGING == state)
        {
            ContactState contactState = getNumberStatus(incomingNumber);
            lastCallNo=incomingNumber;
            Logging.debug("CallListener: Incoming call from " + incomingNumber + ", contactStatus: " + contactState);

            if(contactState.equals(ContactState.NORMAL))
                return;

            currentRingerMode = audioStateManager.getRingerMode();
            audioStateManager.changeRingerMode(contactState);
            ringerModeChangedFlag=true;
        }
        if(TelephonyManager.CALL_STATE_OFFHOOK == state)
        {
            Logging.debug("CallListener: Outgoing");
        }
        if(TelephonyManager.CALL_STATE_IDLE == state && ringerModeChangedFlag)
        {
            audioStateManager.resetRingerMode(currentRingerMode);
            Logging.debug("Ringer reset to: " + currentRingerMode);
            ringerModeChangedFlag=false;
            callLogManager.deleteCallLogs(lastCallNo);
        }
    }

    private ContactState getNumberStatus(String incomingNo)
    {
        List<DeviceContact> list = contactManager.getRestrictedContactList();
        for(DeviceContact contact: list)
        {
            Logging.debug("Restricted: " + contact.getContactNumber());
            if(incomingNo.contains(contact.getContactLastDigitsFromNumber()))
            {
                return ContactState.RESTRICTED;
            }
        }

        list = contactManager.getFavouriteList();
        for(DeviceContact contact: list)
        {
            Logging.debug("Favourite: " + contact.getContactNumber());
            if(incomingNo.contains(contact.getContactLastDigitsFromNumber()))
            {
                return ContactState.FAVOURITE;
            }
        }

        return ContactState.NORMAL;
    }
}
