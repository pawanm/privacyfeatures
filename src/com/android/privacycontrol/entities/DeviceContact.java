package com.android.privacycontrol.entities;


public class DeviceContact
{
    private String contactId;
    private String contactName;
    private String contactNumber;
    private int contactState;


    public DeviceContact(String contactId, String contactName, String contactNumber, int statusCode)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactState = statusCode;
    }

    public String getContactId()
    {
        return contactId;
    }

    public String getContactName()
    {
        return contactName;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public int getContactState()
    {
        return contactState;
    }

    public void setContactState(int code)
    {
        contactState =code;
    }

}
