package com.android.phoneagent.factory;

import android.content.Context;
import com.android.phoneagent.conroller.ContactManager;

public class AppFactory
{
    public static ContactManager getContactManager(Context context)
    {
       return ContactManager.getInstance(context);
    }
}
