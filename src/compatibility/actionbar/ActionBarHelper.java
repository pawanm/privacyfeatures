package compatibility.actionbar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import compatibility.view.ActionModeWrapper;

public abstract class ActionBarHelper
{
    protected Activity mActivity;

    public static ActionBarHelper createInstance(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            return new ActionBarICS(activity);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            return new ActionBarHoneycomb(activity);
        }
        else
        {
            return new ActionBarGingerbread(activity);
        }
    }

    protected ActionBarHelper(Activity activity)
    {
        mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState)
    {
    }

    public abstract void onPostCreate(Bundle savedInstanceState);

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    public abstract MenuInflater getMenuInflater(MenuInflater superMenuInflater);

    public abstract void hideMenuItem(MenuItem item);

    public abstract void showMenuItem(MenuItem item);

    public boolean isIcs()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public abstract void setDisplayHomeAsUpEnabled(boolean flag);

    public abstract void setDisplayShowTitleEnabled(boolean flag);

    public abstract void setDisplayShowHomeEnabled(boolean flag);

    public abstract void setTitle(CharSequence title);

    public abstract void setSubtitle(CharSequence subtitle);

    public abstract void setIcon(Drawable drawable);

    public abstract void setIcon(int resId);

    public abstract void startActionMode(ActionModeWrapper.Callback actionModeCallback);


}
