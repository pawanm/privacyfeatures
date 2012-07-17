package compatibility.listeners;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class ActionBarTabListener implements ActionBar.TabListener
{
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class mClass;

    public ActionBarTabListener(Activity activity, String tag, Class clz)
    {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }


    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
    {
        if (mFragment == null)
        {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        } else
        {
            ft.attach(mFragment);
        }
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
    {
        if (mFragment != null)
        {
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
    {
    }
}


