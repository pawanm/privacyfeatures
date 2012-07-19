package compatibility.actionbar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.phoneagent.R;
import com.android.phoneagent.utils.Utils;
import compatibility.view.ActionBarView;
import compatibility.view.ActionModeCompat;
import compatibility.view.ActionModeWrapper;
import compatibility.widget.MenuCompat;

import java.util.HashSet;
import java.util.Set;

public class ActionBarGingerbread extends ActionBarHelper
{
    protected Set<Integer> mActionItemIds = new HashSet<Integer>();
    protected Set<Integer> mAnimationItemIds = new HashSet<Integer>();

    private ActionModeWrapper.Callback mActionModeCallback;
    private ActionBarView mActionBarView;

    protected ActionBarGingerbread(Activity activity)
    {
        super(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        mActivity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState)
    {
        mActivity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.action_bar_view);
        mActionBarView = new ActionBarView(mActivity, mActivity.findViewById(R.id.action_bar_normal_view));
        setupMenuItemsOnActionBar();
        Utils.overrideFontToRobotoBold(mActivity.findViewById(R.layout.action_bar_view));
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {

        for (Integer id : mActionItemIds)
        {
            menu.findItem(id).setVisible(false);
        }
        return true;
    }

    @Override
    public void hideMenuItem(MenuItem item)
    {
        mActionBarView.hideMenuItem(item);
    }

    @Override
    public void showMenuItem(MenuItem item)
    {
        mActionBarView.showMenuItem(item, mAnimationItemIds);
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean flag)
    {
        mActionBarView.setDisplayHomeAsUpEnabled(flag);
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean flag)
    {
        mActionBarView.setDisplayShowTitleEnabled(flag);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean flag)
    {
        mActionBarView.setDisplayShowHomeEnabled(flag);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        mActionBarView.setTitle(title);
    }

    @Override
    public void setIcon(Drawable drawable)
    {
        mActionBarView.setIcon(drawable);
    }

    @Override
    public void setIcon(int resId)
    {
        mActionBarView.setIcon(resId);
    }

    @Override
    public void setSubtitle(CharSequence subtitle)
    {
        mActionBarView.setSubtitle(subtitle);
    }

    @Override
    public void startActionMode(ActionModeWrapper.Callback actionModeCallback)
    {
        mActivity.findViewById(R.id.action_bar_normal_view).setVisibility(View.GONE);
        View cabView = mActivity.findViewById(R.id.action_bar_action_view);
        cabView.setVisibility(View.VISIBLE);
        mActionModeCallback = actionModeCallback;
        MenuCompat menuItems = new MenuCompat(mActivity);
        ActionModeWrapper actionModeWrapper = new ActionModeWrapper(new ActionModeCompat(cabView));
        mActionModeCallback.onCreateActionMode(actionModeWrapper, menuItems);
    }

    public MenuInflater getMenuInflater(MenuInflater superMenuInflater)
    {
        return new SimpleMenuInflator(mActivity, superMenuInflater, mActionItemIds, mAnimationItemIds);
    }

    private void addMenuItemToActionBar(final android.view.MenuItem item)
    {
        View menuItemView = createMenuItemView(item);
        menuItemView.setId(item.getItemId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        menuItemView.setLayoutParams(layoutParams);
        menuItemView.setPadding(12, 0, 12, 0);
        menuItemView.setBackgroundResource(R.drawable.action_bar_item_background_selector);
        menuItemView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                mActivity.onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, item);
            }
        });
        mActionBarView.addMenuItem(menuItemView);
    }

    private View createMenuItemView(android.view.MenuItem item)
    {
        if (item.getIcon() != null)
        {
            ImageButton actionButton = new ImageButton(mActivity, null, R.attr.actionbarCompatItemStyle);
            actionButton.setImageDrawable(item.getIcon());
            return actionButton;
        }
        else
        {
            TextView actionButtonText = new TextView(mActivity, null, R.attr.actionbarCompatTitleStyle);
            actionButtonText.setText(item.getTitle());
            actionButtonText.setGravity(Gravity.CENTER_VERTICAL);
            return actionButtonText;
        }

    }

    private void setupMenuItemsOnActionBar()
    {
        MenuCompat menu = new MenuCompat(mActivity);
        mActivity.onCreatePanelMenu(Window.FEATURE_OPTIONS_PANEL, menu);
        mActivity.onPrepareOptionsMenu(menu);
        for (int i = 0; i < menu.size(); i++)
        {
            android.view.MenuItem item = menu.getItem(i);
            if (mActionItemIds.contains(item.getItemId()))
            {
                addMenuItemToActionBar(item);
            }
        }
    }
}
