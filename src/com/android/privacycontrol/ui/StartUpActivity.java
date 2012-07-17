package com.android.privacycontrol.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.privacycontrol.R;

public class StartUpActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        startActivity(ContactsActivity.class);

    }

    private void startActivity(Class clz)
    {
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }
}
