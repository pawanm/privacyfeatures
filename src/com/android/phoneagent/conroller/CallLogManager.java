package com.android.phoneagent.conroller;

import android.content.Context;
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
}
