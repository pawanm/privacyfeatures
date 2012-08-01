package com.android.phoneagent.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.phoneagent.R;

public class SettingsActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_activity);
        final Preference pref = findPreference("settings_show_app_running_status");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                boolean showNotification = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("settings_show_app_running_status", false);
                if (showNotification)
                {
                    showNotification();
                }
                else
                {
                    NotificationManager notificationManager = (NotificationManager)
                            getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.cancel(0);
                }
                return true;
            }
        });
    }

    private void showNotification()
    {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.alert, "Phone Agent Is Running", System.currentTimeMillis());
        notification.flags |= Notification.FLAG_NO_CLEAR;

        PendingIntent pi = PendingIntent.getActivity(this, 0, getIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(this, "AppAgent", "Phone Agent Is Running", pi);


        notificationManager.notify(0, notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_feedback:
                showFeedbackActivity();
                return true;

            default:
                return false;
        }
    }

    private void showFeedbackActivity()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pawan.sns@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PhoneAgent Feedback");
        emailIntent.setType("plain/text");

        startActivity(emailIntent);
    }

}