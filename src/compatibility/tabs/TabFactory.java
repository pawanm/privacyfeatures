package compatibility.tabs;

import android.content.Context;
import android.view.View;
import android.widget.TabHost;

/**
 * Created by IntelliJ IDEA.
 * User: pawan
 * Date: 2/15/12
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class TabFactory implements TabHost.TabContentFactory
{

    private final Context mContext;

    public TabFactory(Context context)
    {
        mContext = context;
    }

    public View createTabContent(String tag)
    {
        View v = new View(mContext);
        v.setMinimumWidth(0);
        v.setMinimumHeight(0);
        return v;
    }

}
