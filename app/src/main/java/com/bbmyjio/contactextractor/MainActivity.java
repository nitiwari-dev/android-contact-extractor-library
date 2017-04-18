package com.bbmyjio.contactextractor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bbmyjio.contactextractor.common.permissions.RunTimePermissionWrapper;

import java.util.List;

import contacts.model.api.CPhone;
import contacts.services.JioContactIntentService;
import cquery.CList;
import cquery.CQuery;
import i.ICCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Context mContext;

    String[] WALK_THROUGH = new String[]{Manifest.permission.READ_CONTACTS};

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = MainActivity.this;

        initView();

        init();

        RunTimePermissionWrapper.handleRunTimePermission(MainActivity.this, RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_WALKTHROUGH, WALK_THROUGH);

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.contactRecyclerView);
    }

    private void init() {
        if (RunTimePermissionWrapper.isAllPermissionGranted(this, WALK_THROUGH)) {
            readAndFillContacts();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readAndFillContacts();
    }

    private void readAndFillContacts() {
        CQuery cQuery = CQuery.getInstance(this);
        cQuery.build(new ICCallback() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                Log.d(TAG, "|onContactSuccess count|" + mList.size());

                if (mList != null) {
                    for (CList cList : mList) {
                        Log.d(TAG, "|ID|" + cList.id);

                        CPhone cPhone = cList.phone;
                        if (cPhone != null) {
                            List<String> mobile = cPhone.mobile;
                            if (mobile != null && mobile.size() > 0) {
                                for (String mo : mobile)
                                    Log.d(TAG, "|Mobile|" + mo);
                            }


                            List<String> home = cPhone.home;
                            if (home != null && home.size() > 0) {
                                for (String ho : home)
                                    Log.d(TAG, "|Home|" + ho);
                            }

                            List<String> work = cPhone.work;
                            if (work != null && work.size() > 0) {
                                for (String wo : work)
                                    Log.d(TAG, "|Work|" + wo);
                            }
                        }
                    }
                }
            }

            @Override
            public void onContactError(Throwable throwable) {
                throwable.printStackTrace();
                Log.d(TAG, "|onContactSuccess count|" + throwable.getLocalizedMessage());
            }
        });
    }
}
