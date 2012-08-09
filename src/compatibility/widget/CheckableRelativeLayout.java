package compatibility.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

public class CheckableRelativeLayout extends RelativeLayout implements Checkable
{
    private boolean mIsChecked;

    public CheckableRelativeLayout(Context context)
    {
        super(context);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void setChecked(boolean b)
    {
        mIsChecked = b;
    }

    public boolean isChecked()
    {
        return mIsChecked;
    }

    public void toggle()
    {
        setChecked(!mIsChecked);
    }
}
