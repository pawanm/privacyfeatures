package com.android.privacycontrol.entities;


public class DeviceContact
{
    private String contactId;
    private String contactName;
    private String contactNumber;
    private String contactLastDigitsFromNumber;
    private int contactState;


    public DeviceContact(String contactId, String contactName, String contactNumber, int statusCode)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactState = statusCode;
        this.contactLastDigitsFromNumber = getLastDigitsFromContactNumber(contactNumber);
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

    public String getContactLastDigitsFromNumber()
    {
        return contactLastDigitsFromNumber;
    }

    public void setContactState(int code)
    {
        contactState =code;
    }


    private String getLastDigitsFromContactNumber(String contactNo)
    {
        try
        {
            int length  = contactNo.length();
            if(length<6)
            {
                return contactNo;
            }

            return contactNo.substring(4);
        }
        catch (Exception ex)
        {
            return contactNo;
        }
    }
}
