package com.android.phoneagent.conroller;

import android.content.Context;
import com.android.phoneagent.database.DeviceContactStore;
import com.android.phoneagent.device.DeviceManager;
import com.android.phoneagent.entities.ContactState;
import com.android.phoneagent.entities.DeviceContact;
import com.android.phoneagent.utils.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactManager
{
    private List<DeviceContact> allContactsList;
    private DeviceContactStore contactStore;
    private DeviceManager deviceManager;
    private static ContactManager instance;

    private ContactManager(Context context)
    {
        allContactsList = new ArrayList<DeviceContact>();

        contactStore = new DeviceContactStore(context);
        deviceManager = new DeviceManager(context);
    }

    public static synchronized ContactManager getInstance(Context context)
    {
       if(instance==null)
       {
           instance = new ContactManager(context);
       }
       return instance;
    }

    public List<DeviceContact> getAllContacts()
    {
        allContactsList = contactStore.getDeviceContacts();

        if (allContactsList.size() == 0)
        {
            Logging.debug("getting contacts from device");
            allContactsList = deviceManager.getDeviceContacts();
            contactStore.saveDeviceContacts(allContactsList);
            Logging.debug("contacts saved: " + allContactsList.size());
        }

        return getSortedList(allContactsList);
    }

    public List<DeviceContact> getRestrictedContactList()
    {
        return getFilteredContacts(-1);
    }

    public List<DeviceContact> getFavouriteList()
    {
        return getFilteredContacts(1);
    }

    private List<DeviceContact> getFilteredContacts(int contactState)
    {
        List<DeviceContact> filteredContacts = new ArrayList<DeviceContact>();
        allContactsList = getAllContacts();
        for (DeviceContact contact : allContactsList)
        {
            if (contact.getContactState().equals(contactState))
            {
                filteredContacts.add(contact);
            }
        }
        return getSortedList(filteredContacts);
    }

    public boolean updateContact(DeviceContact contact, ContactState contactState)
    {
        DeviceContact newContact = contact;
        newContact.setContactState(contactState);

        contactStore.updateContact(newContact);
        allContactsList.remove(contact);
        allContactsList.add(newContact);
        return true;
    }

    private List<DeviceContact> getSortedList(List<DeviceContact> contactList)
    {
        Collections.sort(contactList, comparator);
        return contactList;
    }

    private Comparator<DeviceContact> comparator = new Comparator<DeviceContact>()
    {
        public int compare(DeviceContact entry1, DeviceContact entry2)
        {
            String name1 = entry1.getContactName().toLowerCase();
            String name2 = entry2.getContactName().toLowerCase();

            return name1.compareTo(name2);
        }
    };

}
