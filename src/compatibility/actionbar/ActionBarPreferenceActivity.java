package compatibility.actionbar;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;

public class ActionBarPreferenceActivity extends PreferenceActivity
{
    final private ActionBarHelper mActionBarHelper = ActionBarHelper.createInstance(this);

    protected ActionBarHelper getActionBarHelper()
    {
        return mActionBarHelper;
    }

    @Override
    public MenuInflater getMenuInflater()
    {
        return mActionBarHelper.getMenuInflater(super.getMenuInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActionBarHelper.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mActionBarHelper.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean retValue = false;
        retValue |= mActionBarHelper.onCreateOptionsMenu(menu);
        retValue |= super.onCreateOptionsMenu(menu);
        return retValue;
    }
}
