package com.android.phoneagent.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.android.phoneagent.R;
import com.android.phoneagent.adapters.ContactsAdapter;
import com.android.phoneagent.conroller.ContactManager;
import com.android.phoneagent.entities.DeviceContact;
import com.android.phoneagent.factory.AppFactory;
import com.android.phoneagent.utils.Utils;
import compatibility.actionbar.ActionBarActivity;
import compatibility.actionbar.ActionBarHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends ActionBarActivity
{
    private ContactsAdapter contactsAdapter;
    private ListView contactList;
    private View contactsLayoutView;
    private LinearLayout tempView;
    private View emptyStateView;
    private ContactManager contactManager;
    private DeviceContact selectedContact;
    private ActionBarHelper actionBarHelper;
    private int contactsDisplayMode=0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);
        init();
        Utils.overrideFontToRobotoBold(findViewById(R.layout.activity_contacts));

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarHelper.setDisplayHomeAsUpEnabled(true);
        actionBarHelper.setDisplayShowHomeEnabled(false);

    }
    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void init()
    {
        contactManager = AppFactory.getContactManager(this);
        actionBarHelper = getActionBarHelper();
        contactsLayoutView = findViewById(R.id.contactsLayoutView);
        tempView = (LinearLayout) findViewById(R.id.progressbar_view);
        contactList = (ListView) contactsLayoutView.findViewById(R.id.contactsList);
        contactsAdapter = new ContactsAdapter(this);
        contactList.setAdapter(contactsAdapter);

        emptyStateView = findViewById(R.id.emptyContactView);
        contactList.setEmptyView(emptyStateView);

        registerForContextMenu(contactList);

        addOnClickListener();

        showProgressBar(true);

        refreshList(false);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)
        {
            refreshList(true);
        }
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contacts_context_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        DeviceContact deviceContact = contactsAdapter.getItem(info.position);
        selectedContact = deviceContact;
        menu.setHeaderTitle(deviceContact.getContactName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        int newContactState=0;
       /* if (itemId == R.id.menu_make_favourite)
        {
            newContactState=1;
        }*/
        if (itemId == R.id.menu_make_restricted)
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
        refreshList(false);
        return true;
    }

    private void refreshList(final boolean toggleFlag)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                List<DeviceContact> deviceContacts = new ArrayList<DeviceContact>();
                if(toggleFlag==false)
                {
                    contactsDisplayMode=0;
                }
                switch (contactsDisplayMode)
                {
                    case 0: deviceContacts = contactManager.getAllContacts();
                            contactsDisplayMode=-1;break;
                    case 1: deviceContacts = contactManager.getFavouriteList();
                            contactsDisplayMode=-1;break;
                    case -1: deviceContacts = contactManager.getRestrictedContactList();
                            contactsDisplayMode=0;break;
                }
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
                contactsAdapter.setContacts(deviceContacts);
                contactsAdapter.notifyDataSetChanged();
                showProgressBar(false);
                setActivityTitle();
            }
        };

        runOnUiThread(t);

    }

    private void setActivityTitle()
    {
        if(contactsDisplayMode==0)
        {
            setTitle("Restricted Contacts");
        }
        else if(contactsDisplayMode==1)
        {
            setTitle("All Contacts");
        }
        else if(contactsDisplayMode==-1)
        {
            setTitle("All Contacts");
        }
    }

    private void showProgressBar(boolean flag)
    {
        if (flag)
        {
            emptyStateView.setVisibility(View.GONE);
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

