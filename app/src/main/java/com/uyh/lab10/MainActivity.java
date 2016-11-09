package com.uyh.lab10;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private ListView lv;
    String[] name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.phoneInput);
        lv = (ListView) findViewById(R.id.phoneList);
        Cursor cursor = getContacts();

        int size = cursor.getCount();
        name = new String[size];
        phone = new String[size];

        lv.setAdapter(new ContactAdapter(this, name, phone));

    }

    private Cursor getContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + "='" + ("1") + "'";

        String sortOder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return getContentResolver().query(uri, projection, selection, null, sortOder);
    }
}
