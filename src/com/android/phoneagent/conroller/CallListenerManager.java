package com.android.phoneagent.conroller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    SharedPreferences sharedPrefs;

    public CallListenerManager(Context context)
    {
        audioStateManager = new AudioStateManager(context);
        contactManager = AppFactory.getContactManager(context);
        callLogManager = new CallLogManager(context);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Override
    public void onCallStateChanged (int state, String incomingNumber)
    {
        if(sharedPrefs.getBoolean("settings_disable_app",false))
        {
            Logging.debug("returning");
            return;
        }

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
        else if (ringerModeChangedFlag)
        {
            audioStateManager.resetRingerMode(currentRingerMode);
            ringerModeChangedFlag=false;
            callLogManager.deleteCallLogs(lastCallNo);
        }
    }

    private ContactState getNumberStatus(String incomingNo)
    {
        List<DeviceContact> list = contactManager.getRestrictedContactList();
        if (contactExists(incomingNo, list))
            return ContactState.RESTRICTED;

        list = contactManager.getFavouriteList();
        if (contactExists(incomingNo, list))
            return ContactState.FAVOURITE;

        return ContactState.NORMAL;
    }

    private boolean contactExists(String incomingNo, List<DeviceContact> list)
    {
        for(DeviceContact contact: list)
        {
            Logging.debug(contact.getContactState() + ": " + contact.getContactNumber());
            if(incomingNo.contains(contact.getContactLastDigitsFromNumber()))
            {
                return true;
            }
        }
        return false;
    }
}
