package com.android.phoneagent.config;

import android.content.Context;
import android.preference.PreferenceManager;

public class AppSettings
{
    private boolean disableAppStatus;
    private boolean appRunningStatus;
    private Context mContext;
    public static String HELP_ACTIVITY_PREFERENCE = "help_activity";
    public static String FEEDBACK_ACTIVITY_PREFERENCE = "feedback_activity";
    public static String REFRESH_ACTIVITY_PREFERENCE = "refresh_contact_list";
    public static String APP_RUNNING_PREFERENCE = "settings_show_app_running_status";
    public static String DISABLE_APP_PREFERENCE = "settings_disable_app";

    public AppSettings(Context context)
    {
       mContext=context;
    }

    public boolean showAppRunningStatus()
    {
        appRunningStatus = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(APP_RUNNING_PREFERENCE, false);
        return appRunningStatus;
    }

    public boolean disableAppStatus()
    {
        disableAppStatus = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(DISABLE_APP_PREFERENCE, false);
        return disableAppStatus;
    }

}
