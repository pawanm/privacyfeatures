package compatibility.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter
{

    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position)
    {
        return this.fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return this.fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        View view = fragments.get(position).onCreateView(null, container, null);
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeView(view);
        super.destroyItem(container, position, object);
    }
}
