package compatibility.actionbar;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TabHost;
import com.android.privacycontrol.R;
import compatibility.tabs.TabFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class ActionBarWithFragments extends FragmentActivity
{
    final private ActionBarHelper mActionBarHelper = ActionBarHelper.createInstance(this);
    Map<String, View> tabViews = new HashMap<String, View>();

    public ActionBarHelper getActionBarHelper()
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

    protected TabHost.TabSpec getTabSpec(TabHost tabHost, String tag, String text, View tabView)
    {
        TabHost.TabSpec tabSpec;
        if (mActionBarHelper.isIcs())
        {
            tabSpec = tabHost.newTabSpec(tag).setIndicator(text).setContent(new TabFactory(this));
        }
        else
        {
            setTabView(tag, tabView);
            tabSpec = tabHost.newTabSpec(tag).setIndicator(tabView).setContent(new TabFactory(this));
        }
        return tabSpec;
    }

    protected void updateTabUi(String selectedTab)
    {
        if (!mActionBarHelper.isIcs())
        {
            for (String key : tabViews.keySet())
            {
                View view = tabViews.get(key);
                View viewById = view.findViewById(R.id.title);
                if (viewById != null)
                {
                    viewById.setBackgroundResource(R.drawable.tab_normal);
                }
            }

            View view = tabViews.get(selectedTab);
            view.findViewById(R.id.title).setBackgroundResource(R.drawable.tab_active);
        }
    }

    private void setTabView(String tag, View tabView)
    {
        View view = tabViews.get(tag);
        if (view == null)
        {
            tabViews.put(tag, tabView);
        }
    }
}
