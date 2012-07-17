package com.android.privacycontrol.entities;


public class DeviceContact
{
    private String contactId;
    private String contactName;
    private String contactNumber;
    private int statusCode;


    public DeviceContact(String contactId, String contactName, String contactNumber, int statusCode)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.statusCode = statusCode;
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

    public int getStatusCode()
    {
        return statusCode;
    }

}
