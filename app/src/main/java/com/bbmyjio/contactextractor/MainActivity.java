package com.bbmyjio.contactextractor;

import android.Manifest;
import android.content.Context;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.bbmyjio.contactextractor.adapter.MyAdapter;
import com.bbmyjio.contactextractor.cmodels.CEmail;
import com.bbmyjio.contactextractor.cmodels.CName;
import com.bbmyjio.contactextractor.cmodels.ItemData;
import com.bbmyjio.contactextractor.common.permissions.RunTimePermissionWrapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bbmyjio.contactextractor.cquery.CList;
import com.bbmyjio.contactextractor.cquery.CQuery;
import com.bbmyjio.contactextractor.i.ICCallback;
import com.bbmyjio.contactextractor.i.IContactQuery;

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

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration();
        mRecyclerView = (RecyclerView) findViewById(R.id.contactRecyclerView);
        mRecyclerView.addItemDecoration(itemDecoration);
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
        cQuery.limit("20");


        List<Integer> mList = new ArrayList<>();
        mList.add(IContactQuery.Filter.ONLY_NAME);
        mList.add(IContactQuery.Filter.ONLY_PHOTO_URI);

        cQuery.filter(mList);
        cQuery.build(new ICCallback() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                Log.d(TAG, "|onContactSuccess count|" + mList.size());

                List<ItemData> mListAdapter = new ArrayList<>();
                if (mList != null && !mList.isEmpty()) {
                    for (CList cList : mList) {

                        Log.d(TAG, "|ID|" + cList.id);

                        if (cList.getcName() != null) {
                            CName cName = cList.getcName();
                            Log.d(TAG, "|Display Name|" + cName.getDisplayName() + "|Given name|" + cName.getGivenName()
                                    + "|Family name|" + cName.getFamilyName());

                            ItemData itemData = new ItemData(cName.getDisplayName(), uriToBitmapConverter(cList.photoUri));

                            mListAdapter.add(itemData);


                        }

                        if (cList.getcEmail() != null){
                            CEmail cEmail = cList.getcEmail();

                            for (String s : cEmail.mobile){
                                Log.d(TAG, "| Mobile Email |" + s);
                            }

                            for (String s : cEmail.work){
                                Log.d(TAG, "| Mobile Work |" + s);
                            }

                            for (String s : cEmail.home){
                                Log.d(TAG, "| Mobile home |" + s);
                            }
                        }

                        //ItemData itemData = new ItemData(cName.getDisplayName(), uriToBitmapConverter(cList.photoUri));

                        //mListAdapter.add(itemData);




                    }
                }

                MyAdapter mAdapter = new MyAdapter(mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(mAdapter);

            }


            @Override
            public void onContactError(Throwable throwable) {
                throwable.printStackTrace();
                Log.d(TAG, "|onContactSuccess count|" + throwable.getLocalizedMessage());
            }
        });
    }


    Bitmap uriToBitmapConverter(String uriString) {
        try {
            Uri uri = Uri.parse(uriString);
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            if (parent.getChildAdapterPosition(view) == 0) {
                return;
            }

            outRect.top = 10;
        }

    }

}
