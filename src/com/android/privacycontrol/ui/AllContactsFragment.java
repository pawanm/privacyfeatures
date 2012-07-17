package com.android.privacycontrol.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.android.privacycontrol.R;
import com.android.privacycontrol.adapters.AllContactsAdapter;
import com.android.privacycontrol.conroller.ContactManager;
import com.android.privacycontrol.entities.DeviceContact;

import java.util.List;

public class AllContactsFragment extends Fragment
{
    private AllContactsAdapter allContactsAdapter;
    private ListView contactList;
    private View tabAllContacts;
    private View contactsLayoutView;
    private ContactManager contactManager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (container == null)
        {
            return null;
        }

        if (tabAllContacts != null)
        {
            return tabAllContacts;
        }

        tabAllContacts = inflater.inflate(R.layout.tab_all_contacts, container, false);

        return tabAllContacts;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void init()
    {
        contactManager = new ContactManager(this.getActivity());

        tabAllContacts = getActivity().getLayoutInflater().inflate(R.layout.tab_all_contacts, null, false);

        contactsLayoutView = tabAllContacts.findViewById(R.id.contactsLayoutView);
        contactList = (ListView) contactsLayoutView.findViewById(R.id.contactsList);

        allContactsAdapter = new AllContactsAdapter(this.getActivity());
        contactList.setAdapter(allContactsAdapter);
        refreshList();
    }


    private void refreshList()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                List<DeviceContact> deviceContacts = contactManager.getContacts();
                updateListView(deviceContacts);
            }
        }).start();
    }

    private void updateListView(final List<DeviceContact> deviceContacts)
    {
        final Runnable t = new Runnable()
        {
            public void run()
            {
                allContactsAdapter.setContacts(deviceContacts);
                allContactsAdapter.notifyDataSetChanged();
                contactList.setVisibility(View.VISIBLE);
            }
        };

        getActivity().runOnUiThread(t);

    }
}
