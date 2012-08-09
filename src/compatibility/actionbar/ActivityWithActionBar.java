package compatibility.actionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public abstract class ActivityWithActionBar extends Activity
{
    final private ActionBarHelper mActionBarHelper = ActionBarHelper.createInstance(this);

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

        onActionBarCreated(mActionBarHelper);
        if (!mActionBarHelper.isIcs())
        {
            onActionBarMenuCreated(mActionBarHelper);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean retValue = false;
        retValue |= mActionBarHelper.onCreateOptionsMenu(menu);
        retValue |= super.onCreateOptionsMenu(menu);
        if (mActionBarHelper.isIcs())
        {
            onActionBarMenuCreated(mActionBarHelper);
        }
        return retValue;
    }

    public void onActionBarMenuCreated(ActionBarHelper actionBarHelper)
    {

    }

    public void onActionBarCreated(ActionBarHelper actionBarHelper)
    {

    }

    public ActionBarHelper getActionBarHelper()
    {
        return mActionBarHelper;
    }
}
