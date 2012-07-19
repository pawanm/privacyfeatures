package com.android.phoneagent.utils;

import android.util.Log;

public class Logging
{
    public static void debug(String msg)
    {
        Log.d("PhoneAgent-Debug",msg);
    }

    public static void error(String msg)
    {
        Log.e("PhoneAgent-Error",msg);
    }
}
