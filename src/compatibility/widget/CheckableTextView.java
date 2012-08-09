package compatibility.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

public class CheckableTextView extends TextView implements Checkable
{
    private boolean mIsChecked;

    public CheckableTextView(Context context)
    {
        super(context);
    }

    public CheckableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CheckableTextView(Context context, AttributeSet attrs, int defStyle)
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
        mIsChecked = !mIsChecked;
    }
}
