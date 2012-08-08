package com.android.phoneagent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.android.phoneagent.R;
import compatibility.actionbar.ActionBarActivity;
import compatibility.actionbar.ActionBarHelper;

public class HelpActivity extends ActionBarActivity
{
    private ActionBarHelper actionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
