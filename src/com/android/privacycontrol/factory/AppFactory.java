package com.android.privacycontrol.factory;

import android.content.Context;
import com.android.privacycontrol.conroller.ContactManager;

public class AppFactory
{
    public static ContactManager getContactManager(Context context)
    {
       return ContactManager.getInstance(context);
    }
}
