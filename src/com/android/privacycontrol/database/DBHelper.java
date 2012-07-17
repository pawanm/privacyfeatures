package com.android.privacycontrol.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private final static String mDatabaseName = "privacyfeatures.sqlite";
    private final static int mDatabaseVersion = 1;
    public final static String DEVICE_CONTACTS_TABLE = "DeviceContacts";
    private static DBHelper instance;

    public DBHelper(Context context)
    {
        super(context, mDatabaseName, null, mDatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(getPhoneBookTableQuery());
    }

    private String getPhoneBookTableQuery()
    {
        return String.format(
                "create table %s (%s text not null, %s text not null, %s text not null, %s text not null)",
                DEVICE_CONTACTS_TABLE,
                "contactid",
                "contactname",
                "contactno",
                "statuscode");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
    }

    public static DBHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DBHelper(context);
        }
        return instance;
    }
}