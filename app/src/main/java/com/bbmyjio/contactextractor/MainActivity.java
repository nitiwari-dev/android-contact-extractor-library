package com.bbmyjio.contactextractor;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.bbmyjio.contactextractor.common.permissions.RunTimePermissionWrapper;
import com.bbmyjio.contactextractor.fragment.ContactFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Context mContext;

    String[] WALK_THROUGH = new String[]{Manifest.permission.READ_CONTACTS};

    private RelativeLayout container;


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
        container = (RelativeLayout) findViewById(R.id.main_container_relativeLayout);
    }

    private void init() {
        if (RunTimePermissionWrapper.isAllPermissionGranted(this, WALK_THROUGH)) {
            initContactFragment();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initContactFragment();
    }

    private void initContactFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container.getId(), ContactFragment.newInstance(""));
        fragmentTransaction.commit();
    }
}
