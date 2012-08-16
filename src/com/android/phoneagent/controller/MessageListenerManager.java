package com.android.phoneagent.controller;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.android.phoneagent.utils.Logging;

public class MessageListenerManager extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strMessage = "";

        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                strMessage += "From: " + messages[i].getOriginatingAddress();
                strMessage += ",Content: " + messages[i].getMessageBody();
                strMessage += "\n";
            }

            Logging.debug(strMessage);
        }
    }

    public void setAsRead(Context context, String messageId)
    {
        ContentValues values = new ContentValues();
        values.put("read",true);
        context.getContentResolver().update(Uri.parse("content://sms/"), values, "_id=" + messageId, null);
    }

    public void setRead(Context context,String SmsNumber, String smsContent)
    {
        try {
          /*  ContentResolver cr = context.getContentResolver();
            Uri rowUri = Uri.parse("content://sms");
            Cursor c = context.getContentResolver().query(smsUri, null,null,null,null);

            ContentValues values = new ContentValues();
            values.put(c.getColumnName(6),"1");
            cr.update(rowUri, values, " address='"+recnumber+"'and body='"+smsContent+"'", null) ;
*/


        } catch (Exception e)
        {
           // Log.i("exception in setRead:", e.getMessage());
        }
    }
}
