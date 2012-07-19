package com.android.phoneagent.device;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import com.android.phoneagent.database.DBHelper;
import com.android.phoneagent.entities.DeviceContact;

import java.util.ArrayList;
import java.util.List;


public class DeviceManager
{
    private final Context mContext;
    private String mDeviceId;
    private DBHelper mDatabaseHelper;
    private List<DeviceContact> deviceContactsList;

    public DeviceManager(Context context)
    {
        mContext = context;
        init();
    }

    private void init()
    {
        final TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mDeviceId = telephonyManager.getDeviceId();
        mDatabaseHelper = new DBHelper(mContext);
        deviceContactsList = new ArrayList<DeviceContact>();
    }

    public String getDeviceId()
    {
        return mDeviceId;
    }

    public List<DeviceContact> getDeviceContacts()
    {
        if(deviceContactsList.size()>0)
        {
            return deviceContactsList;
        }

        final Cursor cursor = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        final int contactIdColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        final int contactNameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int hasPhoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

        while (cursor.moveToNext())
        {
            final String contactId = cursor.getString(contactIdColumnIndex);
            final String contactName = cursor.getString(contactNameColumnIndex);

            if (hasAtleastOnePhoneNumber(cursor, hasPhoneNumberColumnIndex))
            {
                final DeviceContact contact = getDeviceContact(contactId, contactName);
                if(contact!=null)
                {
                    deviceContactsList.add(contact);
                }
            }
        }
        cursor.close();
        return deviceContactsList;
    }

    private boolean hasAtleastOnePhoneNumber(final Cursor cursor, final int hasPhoneNumberColumnIndex)
    {
        return cursor.getString(hasPhoneNumberColumnIndex).equals("1");
    }

    private List<String> getPhoneNumbersForContact(final String contactId)
    {
        List<String> contactNumbers = new ArrayList<String>();
        Cursor phoneNumberCursor = getPhoneNumberCursor(contactId);

        int phoneNumberColumnIndex = phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        while (phoneNumberCursor.moveToNext())
        {
            contactNumbers.add(phoneNumberCursor.getString(phoneNumberColumnIndex));
        }
        phoneNumberCursor.close();
        return contactNumbers;
    }

    private Cursor getPhoneNumberCursor(final String contactId)
    {
        return mContext.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId}, null);
    }

    private DeviceContact getDeviceContact(String contactId, final String contactName)
    {
        final List<String> contactNumbers = getPhoneNumbersForContact(contactId);
        contactId = mDeviceId + "#" + contactId;
        if(contactNumbers.size()<=0)
        {
            return null;
        }
        DeviceContact deviceContact = new DeviceContact(contactId, contactName, contactNumbers.get(0), 0);
        return deviceContact;
    }

    public SQLiteDatabase getDatabase()
    {
        return mDatabaseHelper.getWritableDatabase();
    }
}
