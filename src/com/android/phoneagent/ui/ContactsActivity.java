package com.android.phoneagent.ui;

import android.content.Intent;
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
import com.android.phoneagent.entities.ContactState;
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
    private CurrentDisplayMode currentDisplayMode = CurrentDisplayMode.ALLCONTACTS;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);
        init();
        Utils.overrideFontToRobotoBold(findViewById(R.layout.activity_contacts));
        currentDisplayMode = CurrentDisplayMode.ALLCONTACTS;
    }

    private enum CurrentDisplayMode
    {
        ALLCONTACTS, FAVOURITELIST, RESTRICTEDLIST
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)
        {
            setCurrentDisplayMode();
            refreshList();
        }
        else if(itemId ==  R.id.contacts_menu_preferences)
        {
            startActivity(new Intent(this,SettingsActivity.class));
        }
        return true;
    }

    private void setCurrentDisplayMode()
    {
        if (currentDisplayMode == CurrentDisplayMode.ALLCONTACTS)
        {
            currentDisplayMode = CurrentDisplayMode.RESTRICTEDLIST;
        }
        else if (currentDisplayMode == CurrentDisplayMode.RESTRICTEDLIST)
        {
            currentDisplayMode = CurrentDisplayMode.FAVOURITELIST;
        }
        else
        {
            currentDisplayMode = CurrentDisplayMode.ALLCONTACTS;
        }
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
        ContactState newContactState=null;
        if (itemId == R.id.menu_make_favourite)
        {
            newContactState = ContactState.FAVOURITE;
        }
        if (itemId == R.id.menu_make_restricted)
        {
            newContactState = ContactState.RESTRICTED;
        }
        else if (itemId == R.id.menu_reset)
        {
            newContactState = ContactState.NORMAL;
        }

        if(newContactState==null)
        {
            return false;
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
                List<DeviceContact> deviceContacts = new ArrayList<DeviceContact>();
                switch (currentDisplayMode)
                {
                    case ALLCONTACTS:
                        deviceContacts = contactManager.getAllContacts();
                        break;
                    case RESTRICTEDLIST:
                        deviceContacts = contactManager.getRestrictedContactList();
                        break;
                    case FAVOURITELIST:
                        deviceContacts = contactManager.getFavouriteList();
                        break;
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
                setActivityTitle(currentDisplayMode);
            }
        };

        runOnUiThread(t);

    }

    private void setActivityTitle(CurrentDisplayMode state)
    {
        switch (state)
        {
            case FAVOURITELIST:
                setTitle("Favourite");
                break;
            case RESTRICTEDLIST:
                setTitle("Restricted");
                break;
            case ALLCONTACTS:
                setTitle("AllContacts");
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

