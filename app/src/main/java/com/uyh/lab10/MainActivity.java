package com.uyh.lab10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private ListView lv;
    private List<String> names = new ArrayList<>();
    private List<String> phones = new ArrayList<>();
    private final String TAG = getClass().getName();

    //private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.phoneInput);
        lv = (ListView) findViewById(R.id.phoneList);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged called.");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.d(TAG, "onTextChanged called.");
                Log.d(TAG, "onTextChanged charSequence: " + charSequence);
                names = new ArrayList<String>();
                phones = new ArrayList<String>();
                Cursor cursor = getContacts();

                getMatchingContacts(cursor, input.getText().toString(), names, phones);

                lv.setAdapter(new ContactAdapter(MainActivity.this, names, phones, input));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged called.");
            }
        });
    }

    private void getMatchingContacts(Cursor cursor, String text, List<String> names, List<String> phones) {
        Log.d(TAG, "The text to match is: " + text);

        while (cursor.moveToNext()) {
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String phoneNum = getPhoneNum(contactId);
            Log.d(TAG, "getMatchingContacts returned from getting phone.");

            if (!phoneNum.isEmpty()) {
                Log.d(TAG, "getMatchingContacts number: " + phoneNum);
                if (phoneNum.contains(text)) {
                    Log.d(TAG, "getMatchingContacts contains: " + phoneNum.contains(text));
                    names.add(displayName);
                    phones.add(phoneNum);

                    Log.d(TAG, "getMatchingContacts names size: " + names.size());
                    Log.d(TAG, "getMatchingContacts phones size: " + phones.size());
                }
            }
        }
    }

    private String getPhoneNum(String id) {
        Log.d(TAG, "getPhoneNum called.");
        String number = "";
        Uri uri = Phone.CONTENT_URI;
        String selection = Phone.CONTACT_ID + "='" + id + "'";

        Cursor phoneCursor = getContentResolver().query(uri, null, selection, null, null);

        if (phoneCursor.moveToNext()) {
            Log.d(TAG, "getPhoneNum number - have result");
            number = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));
            Log.d(TAG, "getPhoneNum number: " + number);
        }

        phoneCursor.close();

        return number;
    }

    private Cursor getContacts() {
        Log.d(TAG, "getContacts called.");
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + "='" + ("1") + "'";

        String sortOder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return getContentResolver().query(uri, projection, selection, null, sortOder);
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult called.");
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(this, "nope", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showContacts() {
        Log.d(TAG, "showContacts called.");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        } else {

        }
    }*/
}
