package com.android.phoneagent.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.phoneagent.utils.Logging;

public class CallLogManager
{

    private Context mContext;

    public CallLogManager(Context context)
    {
       mContext=context;
    }

    public void deleteCallLogs(String contactNo)
    {
        String strUriCalls = "content://call_log/calls";
        Uri UriCalls = Uri.parse(strUriCalls);
        String queryString = "NUMBER='" + contactNo + "'";
        Logging.debug(queryString);
        int noofDeletedRows = mContext.getContentResolver().delete(UriCalls, queryString, null);
        Logging.debug("Deleted Rows deleted" + noofDeletedRows + " with the specified number:" + contactNo);
    }

    public void setAirplaneMode(boolean flag)
    {

    }

    private void changeToAirplaneMode(boolean flag)
    {
        int airplaneMode = flag?1:0;
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        android.provider.Settings.System.putInt(mContext.getContentResolver(), android.provider.Settings.System.AIRPLANE_MODE_ON, airplaneMode);
        intent.putExtra("state", airplaneMode);
        mContext.sendBroadcast(intent);
    }


}
