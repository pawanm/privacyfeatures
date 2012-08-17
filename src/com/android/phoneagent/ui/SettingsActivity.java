package com.android.phoneagent.ui;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.MenuItem;
import android.view.Window;
import com.android.phoneagent.R;
import com.android.phoneagent.config.AppSettings;
import com.android.phoneagent.controller.ContactManager;
import com.android.phoneagent.listeners.ICallBack;
import compatibility.actionbar.ActionBarHelper;
import compatibility.actionbar.ActionBarPreferenceActivity;

public class SettingsActivity extends ActionBarPreferenceActivity
{

    ActionBarHelper mActionBarHelper;
    AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mActionBarHelper = getActionBarHelper();
        if (!mActionBarHelper.isIcs())
        {
            requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_activity);
        registerListeners();
        appSettings = new AppSettings(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mActionBarHelper = getActionBarHelper();
        mActionBarHelper.setDisplayHomeAsUpEnabled(true);
        mActionBarHelper.setDisplayShowHomeEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(this, ContactsActivity.class));
                return true;
            default:
                return false;
        }
    }

    protected Dialog onCreateDialog(int id)
    {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Refreshing ...");
        dialog.setCancelable(false);
        dialog.setMessage("Please wait while synchronizing your contact list");
        if (id == -1)
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
        disableApplicationListener();
        appRunningNotificationListener();
        resetContactListener();
        startHelpActivityListener();
        startFeedbackActivityListener();
    }

    private void disableApplicationListener()
    {
        final Preference settings_disable_app = findPreference(AppSettings.DISABLE_APP_PREFERENCE);
        settings_disable_app.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                handleNotification();
                return true;
            }
        });
    }

    private void appRunningNotificationListener()
    {
        final Preference app_running_status = findPreference(AppSettings.APP_RUNNING_PREFERENCE);
        app_running_status.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                handleNotification();
                return true;
            }
        });
    }

    private void handleNotification()
    {
        if (appSettings.showAppRunningStatus())
        {
            showNotification();
        }
        else
        {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(0);
        }
    }

    private void resetContactListener()
    {
        final Preference refresh_contact_list = findPreference(AppSettings.REFRESH_ACTIVITY_PREFERENCE);
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
                        showErrorDialog();
                    }
                });

                return true;
            }
        });
    }

    private void showErrorDialog()
    {
        try
        {
            dismissDialog(0);
            showDialog(-1);
        } catch (Exception ex)
        {

        }
    }

    private void startHelpActivityListener()
    {
        final Preference app_running_status = findPreference(AppSettings.HELP_ACTIVITY_PREFERENCE);
        app_running_status.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                return true;
            }
        });
    }

    private void startFeedbackActivityListener()
    {
        final Preference feedback_activity = findPreference(AppSettings.FEEDBACK_ACTIVITY_PREFERENCE);
        feedback_activity.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                showFeedbackActivity();
                return true;
            }
        });
    }

    private void showNotification()
    {
        String notificationMsg = "... is active and running.";

        if (appSettings.disableAppStatus())
        {
            notificationMsg = "... is de-activated.";
        }


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.alert, notificationMsg, System.currentTimeMillis());
        notification.flags |= Notification.FLAG_NO_CLEAR;

        PendingIntent pi = PendingIntent.getActivity(this, 0, getIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(this, "PhoneAgent", notificationMsg, pi);


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