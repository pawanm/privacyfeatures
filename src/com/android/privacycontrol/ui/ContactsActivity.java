package com.android.privacycontrol.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.android.privacycontrol.R;
import compatibility.actionbar.ActionBarHelper;
import compatibility.actionbar.ActionBarWithFragments;
import compatibility.adapters.PagerAdapter;
import compatibility.tabs.TabDetail;

import java.util.List;
import java.util.Vector;

public class ContactsActivity extends ActionBarWithFragments implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener
{
    private TabHost mTabHost;
    private ViewPager mViewPager;
    List<Fragment> fragments = new Vector<Fragment>();
    private ActionBarHelper actionBarHelper;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);

        initTabHost(savedInstanceState);
        initViewPager();

        if (savedInstanceState != null)
        {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("currentTab"));
        }

        actionBarHelper = getActionBarHelper();
        onPageSelected(0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        //actionBarHelper.setDisplayShowTitleEnabled(false);
    }

    private void initTabHost(Bundle args)
    {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        populateTabs(args);
        mTabHost.setOnTabChangedListener(this);
    }

    private void populateTabs(Bundle args)
    {
        TabDetail activeChats = new TabDetail(AllContactsFragment.class.toString(), AllContactsFragment.class, args);
        TabDetail allContacts = new TabDetail(PrivateContactsFragment.class.toString(), PrivateContactsFragment.class, args);

        addTab("All", activeChats);
        addTab("Private", allContacts);
    }

    private View getTabView(String text)
    {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
        TextView viewById = (TextView) view.findViewById(R.id.title);
        if (viewById != null)
        {
            viewById.setText(text);
        }
        return view;
    }

    private void addTab(String text, TabDetail tabDetail)
    {
        final View tabView = getTabView(text);
        TabHost.TabSpec tabSpec = getTabSpec(mTabHost, tabDetail.getTag(), text, tabView);
        this.mTabHost.addTab(tabSpec);
    }

    private void initViewPager()
    {
        fragments.add(Fragment.instantiate(this, AllContactsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PrivateContactsFragment.class.getName()));
        PagerAdapter mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }


    public void onPageScrolled(int i, float v, int i1)
    {
    }

    public void onPageScrollStateChanged(int i)
    {
    }

    public void onPageSelected(int i)
    {
        this.mTabHost.setCurrentTab(i);
    }

    public void onTabChanged(String s)
    {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
        updateTabUi(s);
    }
}
