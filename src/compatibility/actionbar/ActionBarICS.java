package compatibility.actionbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

@TargetApi(15)
public class ActionBarICS extends ActionBarHoneycomb
{
    protected ActionBarICS(Activity activity)
    {
        super(activity);
    }

    @Override
    protected Context getActionBarThemedContext()
    {
        return mActivity.getActionBar().getThemedContext();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState)
    {
        mActivity.getActionBar().setHomeButtonEnabled(false);
    }

}
