package compatibility.view;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class ActionModeWrapper
{
    private ActionMode mActionModeIcs;
    private ActionModeCompat mActionModePreIcs;
    private boolean mIsIcs;

    public ActionModeWrapper(ActionMode actionMode)
    {
        mActionModeIcs = actionMode;
        mIsIcs = true;
    }

    public ActionModeWrapper(ActionModeCompat actionMode)
    {
        mActionModePreIcs = actionMode;
        mIsIcs = false;
    }

    public void setTitle(CharSequence title)
    {
        if (mIsIcs)
        {
            mActionModeIcs.setTitle(title);
        }
        else
        {
            mActionModePreIcs.setTitle(title);
        }
    }

    public static interface Callback
    {
        boolean onCreateActionMode(ActionModeWrapper actionModeWrapper, Menu menu);

        boolean onPrepareActionMode(ActionModeWrapper actionModeWrapper, Menu menu);

        boolean onActionItemClicked(ActionModeWrapper actionModeWrapper, MenuItem menuItem);

        void onDestroyActionMode(ActionModeWrapper actionModeWrapper);
    }
}
