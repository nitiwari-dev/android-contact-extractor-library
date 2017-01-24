package com.bbmyjio.contactextractor;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import common.permissions.RunTimePermissionWrapper;
import contacts.query.JioContactQuery;
import contacts.services.JioContactIntentService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Context mContext;

    String[] WALK_THROUGH = new String[]{Manifest.permission.READ_CONTACTS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = MainActivity.this;

        init();

        RunTimePermissionWrapper.handleRunTimePermission(MainActivity.this, RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_WALKTHROUGH, WALK_THROUGH);

    }

    private void init() {
        if (RunTimePermissionWrapper.isAllPermissionGranted(this, WALK_THROUGH)) {
            rawContact();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //queryFirstNameLastName();
        rawContact();
    }

    private void queryFirstNameLastName() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String whereName = ContactsContract.Data.MIMETYPE + " = ?";
                String[] whereNameParams = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE};
                Cursor nameCur = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams, ContactsContract.CommonDataKinds.Email.ADDRESS);
                while (nameCur.moveToNext()) {
                    String given = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                    String family = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                    String display = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));

                    Log.d(TAG, "|givenName|" + given + "|family|" + family + "|display|" + display);
                }
                nameCur.close();
            }
        }).start();
    }


    //new String[] { "com.whatsapp" }

    private void rawContact() {
        Intent intent = new Intent(mContext, JioContactIntentService.class);
        mContext.startService(intent);

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                new JioContactQuery(mContext).insertContactList();
            *//*  Cursor c = getContentResolver().query(
                        ContactsContract.RawContacts.CONTENT_URI,
                        new String[]{ContactsContract.RawContacts.CONTACT_ID, ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, ContactsContract.RawContacts.ACCOUNT_INFO, ContactsContract.RawContacts.ACCOUNT_TYPE},
                        null,
                        null,
                        null);

                //ArrayList<String> myWhatsappContacts = new ArrayList<String>();


                while (c.moveToNext()) {
                    int contactNameColumn = c.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY);
                    int accName = c.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_INFO);
                    int accType = c.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID);

                    // You can also read RawContacts.CONTACT_ID to read the
                    // ContactsContract.Contacts table or any of the other related ones.

                    Log.d(TAG, "|DISPLAY NAME|" + c.getString(contactNameColumn) + "\n"
                            + "|ACCOUNT_INFO|" + c.getString(accName) + "|CONTACT_ID|" + c.getString(accType));

                    //myWhatsappContacts.add(c.getString(contactNameColumn));
                }
*//*

            }
        }).start();*/
    }
}
