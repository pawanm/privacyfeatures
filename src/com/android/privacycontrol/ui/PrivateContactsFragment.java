package com.android.privacycontrol.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import com.android.privacycontrol.adapters.AllContactsAdapter;
import com.android.privacycontrol.device.DeviceManager;

public class PrivateContactsFragment extends Fragment
{
    private AllContactsAdapter allContactsAdapter;
    private ListView listView;
    private View tabAllContacts;
    DeviceManager deviceManager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void init()
    {

    }


}

