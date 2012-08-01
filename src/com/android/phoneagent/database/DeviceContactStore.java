package com.android.phoneagent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.phoneagent.entities.ContactState;
import com.android.phoneagent.entities.DeviceContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DeviceContactStore
{
    private final SQLiteDatabase mDatabase;
    public final static String DEVICE_CONTACTS_TABLE = "DeviceContacts";

    public enum fields
    {
        ContactId, ContactName, ContactNo, ContactState
    }

    public DeviceContactStore(Context context)
    {
        this.mDatabase = DBHelper.getInstance(context).getWritableDatabase();
    }

    public synchronized void saveDeviceContacts(List<DeviceContact> deviceContacts)
    {
        Map<String, String> values = new HashMap<String, String>();
        for (DeviceContact deviceContact : deviceContacts)
        {
            values.put(fields.ContactId.toString(), deviceContact.getContactId());
            values.put(fields.ContactName.toString(), deviceContact.getContactName());
            values.put(fields.ContactNo.toString(), deviceContact.getContactNumber());
            values.put(fields.ContactState.toString(), getIntForEnum(deviceContact.getContactState())+"");

            mDatabase.insert(DEVICE_CONTACTS_TABLE, null ,convertToContentValues(values));
        }
    }

    private ContentValues convertToContentValues(DeviceContact contact)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(fields.ContactId.toString(),contact.getContactId());
        contentValues.put(fields.ContactName.toString(),contact.getContactName());
        contentValues.put(fields.ContactNo.toString(),contact.getContactNumber());
        contentValues.put(fields.ContactState.toString(),getIntForEnum(contact.getContactState()));

        return contentValues;
    }


    private int getIntForEnum(ContactState contactState)
    {
        switch (contactState)
        {
            case FAVOURITE:return 1;
            case RESTRICTED:return -1;
        }
        return 0;
    }

    private ContactState getEnumForInt(int code)
    {
        switch (code)
        {
            case 1:return ContactState.FAVOURITE;
            case -1:return ContactState.RESTRICTED;
        }
        return ContactState.NORMAL;
    }

    private ContentValues convertToContentValues(Map<String, String> values)
    {
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> entry : values.entrySet())
        {
            contentValues.put(entry.getKey(), entry.getValue());
        }
        return contentValues;
    }


    public synchronized List<DeviceContact> getDeviceContacts()
    {
        String[] columns =
                { fields.ContactId.toString(), fields.ContactName.toString(), fields.ContactNo.toString(),
                        fields.ContactState.toString()};

        Cursor resultCursor = mDatabase.query(DEVICE_CONTACTS_TABLE, columns, null, null, null, null, fields.ContactId.toString()
                + " ASC");
        ArrayList<DeviceContact> entries = new ArrayList<DeviceContact>();

        while (resultCursor.moveToNext())
        {
            int code = Integer.parseInt(resultCursor.getString(3));
            DeviceContact entry = new DeviceContact(resultCursor.getString(0), resultCursor.getString(1), resultCursor.getString(2), getEnumForInt(code));
            entries.add(entry);
        }

        resultCursor.close();
        return entries;
    }

    public synchronized void updateContact(DeviceContact contact)
    {
        String where = fields.ContactId + "=?";
        String whereArgs[] =
                { contact.getContactId() };
        mDatabase.update(DEVICE_CONTACTS_TABLE, convertToContentValues(contact), where, whereArgs);

    }

    public synchronized void addContact(DeviceContact deviceContact)
    {
        Map<String, String> values = new HashMap<String, String>();
        values.put(fields.ContactId.toString(), deviceContact.getContactId());
        values.put(fields.ContactName.toString(), deviceContact.getContactName());
        values.put(fields.ContactNo.toString(), deviceContact.getContactNumber());
        values.put(fields.ContactState.toString(), getIntForEnum(deviceContact.getContactState())+"");

        mDatabase.insert(DEVICE_CONTACTS_TABLE, null ,convertToContentValues(values));

    }

}
