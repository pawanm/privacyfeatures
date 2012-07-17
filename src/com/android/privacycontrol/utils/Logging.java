package com.android.privacycontrol.utils;

import android.util.Log;

public class Logging
{
    public static void debug(String msg)
    {
        Log.d("privacy-control-debug",msg);
    }

    public static void error(String msg)
    {
        Log.e("privacy-control-error",msg);
    }
}
