package compatibility.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class TabDetail
{
    private String tag;
    private Class<?> clss;
    private Bundle args;
    private Fragment fragment;

    public TabDetail(String tag, Class<?> clazz, Bundle args)
    {
        this.tag = tag;
        this.clss = clazz;
        this.args = args;
    }

    public String getTag()
    {
        return tag;
    }
}
