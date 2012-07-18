package com.android.privacycontrol.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.android.privacycontrol.R;
import com.android.privacycontrol.adapters.AllContactsAdapter;
import com.android.privacycontrol.conroller.ContactManager;
import com.android.privacycontrol.entities.DeviceContact;
import com.android.privacycontrol.factory.AppFactory;
import compatibility.actionbar.ActionBarActivity;

import java.util.List;

public class ContactsActivity extends ActionBarActivity
{
    private AllContactsAdapter allContactsAdapter;
    private ListView contactList;
    private View contactsLayoutView;
    private LinearLayout tempView;
    private ContactManager contactManager;
    private DeviceContact selectedContact;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);
        init();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void init()
    {
        contactManager = AppFactory.getContactManager(this);

        contactsLayoutView = findViewById(R.id.contactsLayoutView);
        tempView = (LinearLayout) findViewById(R.id.progressbar_view);
        contactList = (ListView) contactsLayoutView.findViewById(R.id.contactsList);
        allContactsAdapter = new AllContactsAdapter(this);
        contactList.setAdapter(allContactsAdapter);

        registerForContextMenu(contactList);

        addOnClickListener();

        showProgressBar(true);

        refreshList();
    }

    private void addOnClickListener()
    {
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                view.showContextMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.contacts_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contacts_context_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        DeviceContact deviceContact = allContactsAdapter.getItem(info.position);
        selectedContact = deviceContact;
        menu.setHeaderTitle(deviceContact.getContactName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        int newContactState=0;
        if (itemId == R.id.menu_make_favourite)
        {
            newContactState=1;
        }
        else if (itemId == R.id.menu_make_restricted)
        {
            newContactState=-1;
        }
        else if (itemId == R.id.menu_reset)
        {
            newContactState=0;
        }
        else
        {
            return super.onContextItemSelected(item);
        }
        contactManager.updateContact(selectedContact, newContactState);
        refreshList();
        return true;
    }
    private void refreshList()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                List<DeviceContact> deviceContacts = contactManager.getAllContacts();
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
                showProgressBar(false);

            }
        };

        runOnUiThread(t);

    }

    private void showProgressBar(boolean flag)
    {
        if (flag)
        {
            contactsLayoutView.setVisibility(View.GONE);
            tempView.setVisibility(View.VISIBLE);
            tempView.addView(getLayoutInflater().inflate(R.layout.progress_bar, tempView, false));
        } else
        {
            contactsLayoutView.setVisibility(View.VISIBLE);
            tempView.setVisibility(View.GONE);

        }
    }


}

