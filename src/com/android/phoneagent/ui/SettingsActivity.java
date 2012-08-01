package com.android.phoneagent.ui;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.phoneagent.R;
import com.android.phoneagent.conroller.ContactManager;
import com.android.phoneagent.listeners.ICallBack;
import compatibility.actionbar.ActionBarHelper;
import compatibility.actionbar.ActionBarPreferenceActivity;

public class SettingsActivity extends ActionBarPreferenceActivity
{
    private ActionBarHelper actionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_activity);
        registerListeners();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarHelper = getActionBarHelper();
        actionBarHelper.setDisplayHomeAsUpEnabled(true);
        actionBarHelper.setDisplayShowHomeEnabled(false);

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
            case android.R.id.home:
                startActivity(new Intent(this, ContactsActivity.class));
                return true;
            default:
                return false;
        }
    }

    protected Dialog onCreateDialog(int id)
    {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Refreshing Contact List");
        dialog.setMessage("Please wait while synchronizing your contact list");
        if(id==-1)
        {
            dialog.setButton("Close", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {

                }
            });
        }
        return dialog;
    }

    private void registerListeners()
    {
        appRunningNotificationListener();
        resetContactListener();
    }

    private void appRunningNotificationListener()
    {
        final Preference app_running_status = findPreference("settings_show_app_running_status");
        app_running_status.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
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

    private void resetContactListener()
    {
        final Preference refresh_contact_list = findPreference("refresh_contact_list");
        refresh_contact_list.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                showDialog(0);
                ContactManager contactManager = ContactManager.getInstance(getApplicationContext());
                contactManager.updateContactList(new ICallBack<String, String>()
                {
                    public void success(String result)
                    {
                        dismissDialog(0);
                    }

                    public void failure(String result)
                    {
                        dismissDialog(0);
                        showDialog(-1);
                    }
                });

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
        notification.setLatestEventInfo(this, "PhoneAgent", "Phone Agent Is Running", pi);


        notificationManager.notify(0, notification);
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