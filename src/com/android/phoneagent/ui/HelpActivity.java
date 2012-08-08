package com.android.phoneagent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.android.phoneagent.R;
import compatibility.actionbar.ActionBarHelper;
import compatibility.actionbar.ActionBarPreferenceActivity;

public class HelpActivity extends ActionBarPreferenceActivity
{
    private ActionBarHelper actionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_help);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return false;
        }
    }
}
