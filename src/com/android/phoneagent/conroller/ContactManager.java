package com.android.phoneagent.conroller;

import android.content.Context;
import com.android.phoneagent.database.DeviceContactStore;
import com.android.phoneagent.device.DeviceManager;
import com.android.phoneagent.entities.ContactState;
import com.android.phoneagent.entities.DeviceContact;
import com.android.phoneagent.listeners.ICallBack;
import com.android.phoneagent.utils.Logging;

import java.util.*;

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

    public synchronized void updateContactList(final ICallBack<String,String> callBack)
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    List<DeviceContact> deviceContacts = deviceManager.getDeviceContacts(true);
                    List<DeviceContact> storeContacts = contactStore.getDeviceContacts();
                    Logging.debug("contact store size: " + storeContacts.size());
                    Logging.debug("device contact size: " + deviceManager.getDeviceContacts(true).size());
                    for(DeviceContact deviceContact: deviceContacts)
                    {
                        if(!isContactExistsInStore(deviceContact,storeContacts))
                        {
                            contactStore.addContact(deviceContact);
                            Logging.debug("new contact added: " + deviceContact.getContactId() + "," +  deviceContact.getContactName());
                        }
                    }
                    callBack.success("done");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    callBack.failure(ex.getMessage());
                }
            }
        });
        thread.start();
    }

    private boolean isContactExistsInStore(DeviceContact deviceContact, List<DeviceContact> storeContacts)
    {
        boolean flag=false;
        for(DeviceContact storeContact: storeContacts)
        {
            if(storeContact.getContactNumber().equals(deviceContact.getContactNumber()))
            {
               flag=true;
               break;
            }
        }
        return flag;
    }

    public List<DeviceContact> getAllContacts()
    {
        allContactsList = contactStore.getDeviceContacts();

        if (allContactsList.size() == 0)
        {
            Logging.debug("getting contacts from device");
            allContactsList = deviceManager.getDeviceContacts(false);
            contactStore.saveDeviceContacts(allContactsList);
            Logging.debug("contacts saved: " + allContactsList.size());
        }

        return getSortedList(allContactsList);
    }

    public List<DeviceContact> getRestrictedContactList()
    {
        return getFilteredContacts(ContactState.RESTRICTED);
    }

    public List<DeviceContact> getFavouriteList()
    {
        return getFilteredContacts(ContactState.FAVOURITE);
    }

    private List<DeviceContact> getFilteredContacts(ContactState contactState)
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

    public synchronized boolean updateContact(DeviceContact contact, ContactState contactState)
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
