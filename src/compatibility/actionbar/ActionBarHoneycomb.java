package compatibility.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import compatibility.view.ActionModeWrapper;

public class ActionBarHoneycomb extends ActionBarHelper
{
    private ActionBar actionBar;

    protected ActionBarHoneycomb(Activity activity)
    {
        super(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        actionBar = mActivity.getActionBar();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState)
    {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public MenuInflater getMenuInflater(MenuInflater superMenuInflater)
    {
        return superMenuInflater;
    }

    @Override
    public void hideMenuItem(MenuItem item)
    {
        item.setVisible(false);
    }

    @Override
    public void showMenuItem(MenuItem item)
    {
        item.setVisible(true);
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean flag)
    {
        actionBar.setDisplayHomeAsUpEnabled(flag);
    }

    public void setDisplayShowTitleEnabled(boolean flag)
    {
        actionBar.setDisplayShowTitleEnabled(flag);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean flag)
    {
        actionBar.setDisplayShowHomeEnabled(flag);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        actionBar.setTitle(title);
    }

    @Override
    public void setSubtitle(CharSequence subtitle)
    {
        actionBar.setSubtitle(subtitle);
    }

    @Override
    public void setIcon(Drawable drawable)
    {
        actionBar.setIcon(drawable);
    }

    @Override
    public void setIcon(int resId)
    {
        actionBar.setIcon(resId);
    }

    @Override
    public void startActionMode(ActionModeWrapper.Callback actionModeCallback)
    {
    }

    protected Context getActionBarThemedContext()
    {
        return actionBar.getThemedContext();
    }
}
