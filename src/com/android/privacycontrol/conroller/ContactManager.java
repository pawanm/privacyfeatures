package com.android.privacycontrol.conroller;

import android.content.Context;
import com.android.privacycontrol.database.DeviceContactStore;
import com.android.privacycontrol.device.DeviceManager;
import com.android.privacycontrol.entities.DeviceContact;
import com.android.privacycontrol.utils.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactManager
{
    private List<DeviceContact> contactList;
    private DeviceContactStore contactStore;
    private DeviceManager deviceManager;

    public ContactManager(Context context)
    {
        contactList = new ArrayList<DeviceContact>();
        contactStore = new DeviceContactStore(context);
        deviceManager = new DeviceManager(context);
    }

    public List<DeviceContact> getContacts()
    {
        if(contactList.size()==0)
        {
            Logging.debug("getting contacts from store");
            contactList = contactStore.getDeviceContacts();
            if(contactList.size()==0)
            {
                Logging.debug("getting contacts from device");
                contactList =  deviceManager.getDeviceContacts();
                contactStore.saveDeviceContacts(contactList);
                Logging.debug("contacts saved: " + contactList.size());
            }
        }
        Logging.debug("returning contacts: " + contactList.size());
        return getSortedList(contactList);
    }

    private List<DeviceContact> getSortedList(List<DeviceContact> contactList)
    {
        Collections.sort(contactList,comparator);
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
