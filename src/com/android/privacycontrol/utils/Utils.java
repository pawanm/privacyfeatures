package com.android.privacycontrol.utils;

import com.android.privacycontrol.R;

public class Utils
{
    public static int getColorForContactState(int contactState)
    {
        switch (contactState)
        {
            case 1:
                return R.color.favouriteContact;
            case -1:
                return R.color.restrictedContact;
            default:
                return R.color.normalContact;
        }
    }
}
