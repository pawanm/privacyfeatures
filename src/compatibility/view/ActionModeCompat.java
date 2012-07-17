package compatibility.view;

import android.view.View;
import android.widget.TextView;
import com.android.privacycontrol.R;


public class ActionModeCompat
{
    private View mActionModeView;

    public ActionModeCompat(View view)
    {
        mActionModeView = view;
    }

    public void setTitle(CharSequence title)
    {
        ((TextView) mActionModeView.findViewById(R.id.cab_title)).setText(title);
    }
}
