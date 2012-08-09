package compatibility.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.phoneagent.R;
import com.android.phoneagent.utils.Logging;
import compatibility.widget.MenuCompat;
import compatibility.widget.MenuItemCompat;

import java.util.Set;

public class ActionBarView
{
    private Activity mActivity;
    private View mActionBarView;

    private boolean isIconVisible = true;
    private boolean isTitleVisible = true;

    private TextView mTitleView;
    private TextView mSubtitleView;
    private ImageView mIconView;
    private View mBackTriangle;

    private ViewGroup mMenuContainer;

    private final String LOGTAG = "TAlkTo_" + ActionBarView.class.getSimpleName();

    public ActionBarView(Activity context, View view)
    {
        mActivity = context;
        mActionBarView = view;
        mMenuContainer = (ViewGroup) view.findViewById(R.id.options_menu_container);
        mSubtitleView = (TextView) mActionBarView.findViewById(R.id.action_bar_subtitle);
        mTitleView = (TextView) mActionBarView.findViewById(R.id.action_bar_title);
        mBackTriangle = mActionBarView.findViewById(R.id.back_triangle);
        mIconView = (ImageView) mActionBarView.findViewById(R.id.action_bar_icon);
        setActionBarDefaultView();
    }

    public void hideMenuItem(MenuItem item)
    {
        View view = mMenuContainer.findViewById(item.getItemId());
        view.setVisibility(View.GONE);
        view.setAnimation(null);
    }

    public void showMenuItem(MenuItem item, Set<Integer> animationItemIds)
    {
        View view = mMenuContainer.findViewById(item.getItemId());
        view.setVisibility(View.VISIBLE);
        if (animationItemIds.contains(item.getItemId()))
        {
            Animation rotateAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.linear_rotate);
            view.setAnimation(rotateAnimation);
        }
    }

    public void setSubtitle(CharSequence subtitle)
    {
        mSubtitleView.setText(subtitle);
        mSubtitleView.setVisibility(subtitle == null ? View.GONE : View.VISIBLE);
    }

    public void setDisplayHomeAsUpEnabled(boolean flag)
    {
        if (flag)
        {
            mBackTriangle.setVisibility(View.VISIBLE);
            View.OnClickListener onClickListener = new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    MenuCompat tempMenu = new MenuCompat(mActivity);
                    mActivity.onOptionsItemSelected(new MenuItemCompat(tempMenu, android.R.id.home, 0, null));
                }
            };
            mActionBarView.findViewById(R.id.action_bar_home_view).setClickable(true);
            mActionBarView.findViewById(R.id.action_bar_home_view).setOnClickListener(onClickListener);
        }
        else
        {
            mBackTriangle.setVisibility(View.GONE);
            mActionBarView.findViewById(R.id.action_bar_home_view).setClickable(false);
        }
    }

    public void setDisplayShowTitleEnabled(boolean flag)
    {
        isTitleVisible = flag;
        mTitleView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    public void setDisplayShowHomeEnabled(boolean flag)
    {
        isIconVisible = flag;
        mIconView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    public void setTitle(CharSequence title)
    {
        mTitleView.setText(title);
        boolean visibility = title != null && isTitleVisible;
        mTitleView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void setIcon(Drawable drawable)
    {
        mIconView.setImageDrawable(drawable);
        boolean visibility = drawable != null && isIconVisible;
        mIconView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void setIcon(int resId)
    {
        setIcon(mActivity.getResources().getDrawable(resId));
    }

    private Drawable getActivityIcon()
    {
        PackageManager packageManager = mActivity.getPackageManager();
        ComponentName name = mActivity.getComponentName();

        try
        {
            return packageManager.getActivityIcon(name);
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            Logging.debug("Icon not found: " + name.getClassName());
        }
        return null;
    }

    private void setActionBarDefaultView()
    {
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout root = (LinearLayout) decorView.getChildAt(0);
        FrameLayout frameLayout = (FrameLayout) root.getChildAt(0);
        frameLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.action_bar_background));
        

        Drawable icon = getActivityIcon();
        if (icon == null)
        {
            icon = mActivity.getResources().getDrawable(R.drawable.alert);
        }
        setIcon(icon);

        CharSequence title = mActivity.getTitle();
        if (title == null)
        {
            title = mActivity.getResources().getString(R.string.app_name);
        }
        setTitle(title);

        setDisplayHomeAsUpEnabled(false);
    }

    public void addMenuItem(View menuItemView)
    {
        mMenuContainer.addView(menuItemView);
    }
}
