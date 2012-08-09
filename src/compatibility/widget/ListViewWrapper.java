package compatibility.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ListView;
import compatibility.actionbar.ActionBarHelper;
import compatibility.actionbar.ActivityWithActionBar;
import compatibility.view.ActionModeWrapper;

@TargetApi(15)
public class ListViewWrapper extends ListView
{
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    private MultiChoiceModeListener mMultiChoiceModeListener;
    private Context mContext;
    private ActionBarHelper mActionBarHelper;
    private OnItemClickListener mOnItemClickListener;
    private boolean mIsIcs;
    private int mCheckedItemCount;

    public ListViewWrapper(Context context)
    {
        super(context);
        init(context);
    }

    public ListViewWrapper(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public ListViewWrapper(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        mActionBarHelper = ((ActivityWithActionBar) mContext).getActionBarHelper();
        mIsIcs = mActionBarHelper.isIcs();
    }

    public void setChoiceMode(int choiceMode)
    {
        if (!mIsIcs && choiceMode == CHOICE_MODE_MULTIPLE_MODAL)
        {
            clearChoices();
            setLongClickable(true);
            super.setChoiceMode(CHOICE_MODE_MULTIPLE);
            setOnItemLongClickListener(new OnItemLongClickListener()
            {
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    toggleCheckedState((Checkable) view);
                    setupActionMode();
                    return true;
                }
            });
        }
        else
        {
            super.setChoiceMode(choiceMode);
        }
    }

    public static interface MultiChoiceModeListener extends ActionModeWrapper.Callback
    {
        void onItemCheckedStateChanged(ActionModeWrapper actionModeWrapper, int i, long l, boolean b);
    }

    public void setMultiChoiceModeListener(MultiChoiceModeListener listener)
    {
        mMultiChoiceModeListener = listener;

        if (mIsIcs)
        {
            super.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()
            {
                public void onItemCheckedStateChanged(android.view.ActionMode actionMode, int i, long l, boolean b)
                {
                    mMultiChoiceModeListener.onItemCheckedStateChanged(new ActionModeWrapper(actionMode), i, l, b);
                }

                public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu)
                {
                    return mMultiChoiceModeListener.onCreateActionMode(new ActionModeWrapper(actionMode), menu);
                }

                public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu)
                {
                    return mMultiChoiceModeListener.onPrepareActionMode(new ActionModeWrapper(actionMode), menu);
                }

                public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem)
                {
                    return mMultiChoiceModeListener.onActionItemClicked(new ActionModeWrapper(actionMode), menuItem);
                }

                public void onDestroyActionMode(android.view.ActionMode actionMode)
                {
                    mMultiChoiceModeListener.onDestroyActionMode(new ActionModeWrapper(actionMode));
                }
            });
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        super.setOnItemClickListener(listener);
        mOnItemClickListener = listener;
    }

    private void setupActionMode()
    {
        setLongClickable(false);
        final ActionModeWrapper actionModeWrapper = mActionBarHelper.startActionMode(mMultiChoiceModeListener);
        mMultiChoiceModeListener.onItemCheckedStateChanged(actionModeWrapper, 0, 0, false);

        super.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toggleCheckedState((Checkable) view);
                mMultiChoiceModeListener.onItemCheckedStateChanged(actionModeWrapper, i, l, isItemChecked(i));
            }
        });
    }

    private void toggleCheckedState(Checkable view)
    {
        view.toggle();
        mCheckedItemCount += view.isChecked() ? 1 : -1;
    }

    @Override
    public int getCheckedItemCount()
    {
        if (mIsIcs)
        {
            return super.getCheckedItemCount();
        }
        else
        {
            return mCheckedItemCount;
        }
    }
}
