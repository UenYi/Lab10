package com.uyh.lab10;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private ListView lv;
    private List<String> names = new ArrayList<>();
    private List<String> phones = new ArrayList<>();
    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.phoneInput);
        lv = (ListView) findViewById(R.id.phoneList);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Cursor cursor = getContacts();

                getMatchingContacts(cursor, input.getText().toString(), names, phones);

                lv.setAdapter(new ContactAdapter(MainActivity.this, names, phones, input));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getMatchingContacts(Cursor cursor, String text, List<String> names, List<String> phones) {
        Log.d(TAG, "The text to match is: " + text);

        while (cursor.moveToNext()) {
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String phoneNum = getPhoneNum(contactId);

            if(!phoneNum.isEmpty()){
                if(phoneNum.equalsIgnoreCase(text)){
                    names.add(displayName);
                    phones.add(phoneNum);
                }
            }
        }
    }

    private String getPhoneNum(String id) {
        String number = "";
        Uri uri = Phone.CONTENT_URI;
        String selection = Phone.CONTACT_ID + "='" + id + "'";

        Cursor phoneCursor = getContentResolver().query(uri,null,selection,null,null);

        if(phoneCursor.moveToNext()){
            number = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));
        }

        phoneCursor.close();

        return number;
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
