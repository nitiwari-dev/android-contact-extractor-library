package com.bbmyjio.contactextractor;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bbmyjio.contactextractor.adapter.MyAdapter;
import com.bbmyjio.contactextractor.common.permissions.RunTimePermissionWrapper;
import com.coderconsole.cextracter.cmodels.CGroups;
import com.bbmyjio.contactextractor.adapter.ItemData;
import com.coderconsole.cextracter.cquery.base.CList;
import com.coderconsole.cextracter.cquery.CQuery;
import com.coderconsole.cextracter.cquery.common.CommonCList;
import com.coderconsole.cextracter.i.IContact;
import com.coderconsole.cextracter.i.IContactQuery;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //gettingPhoneContacts();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readAndFillContacts();
        //gettingPhoneContacts();
    }

    private void readAndFillContacts() {

        CQuery cQuery = CQuery.getInstance(this);
        cQuery.filter(IContactQuery.Filter.ONLY_GROUPS);
        cQuery.build(new IContact() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                List<ItemData> mListAdapter = new ArrayList<>();
                Toast.makeText(MainActivity.this, " Contacts count " + mList.size(), Toast.LENGTH_SHORT).show();
                if (mList != null && !mList.isEmpty()) {
                    for (CList cList : mList) {

                       /* CAccount cAccount = cList.getcAccount();

                        if (cAccount != null) {

                            StringBuilder builder = new StringBuilder();
                            for (ContactGenericType contactGenericType : cAccount.getmGenericType()) {
                                builder.append("Name - " + contactGenericType.name).append("\n" + contactGenericType.type);
                            }
                            ItemData itemData = new ItemData("ContactId - " + cList.contactId + "\n" + builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);

                        }*/

                        /*CPostBoxCity postBoxCity = cList.getcPostCode();

                        if (postBoxCity != null) {

                            StringBuilder builder = new StringBuilder();
                            for (CPostBoxCity.PostCity postCity:postBoxCity.getmPostCity()){
                                builder.append("City " + postCity.city).append(" Post "+postCity.post);
                            }

                            ItemData itemData = new ItemData("ContactId - " + cList.contactId + "\n" + builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);

                        }*/

                        /*COrganisation org = cList.getcOrg();

                        if (org != null) {

                            StringBuilder builder = new StringBuilder();
                            for (COrganisation.CompanyDepart companyDepart:org.getCompanyOrgList()){
                                builder.append("Company " + companyDepart.company).append(" Org "+companyDepart.org);
                            }

                            ItemData itemData = new ItemData("ContactId - " + cList.contactId + "\n" + builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);

                        }*/


                       /* CEvents events = cList.getcEvents();

                        if (events != null) {

                            StringBuilder builder = new StringBuilder();
                            builder.append(events.getAnniversay() + "| Birthday |" + events.getBirthDay());
                            ItemData itemData = new ItemData("ContactId - " + cList.contactId + "\n" + builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);

                        }*/


                        CGroups groups = cList.getcGroups();

                        if (groups != null) {

                            StringBuilder builder = new StringBuilder();

                            for (CGroups.BaseGroups baseGroups : groups.getmList()) {
                                builder.append("| BaseG |" + baseGroups.getTitle() + "||" + baseGroups.getId());
                            }

                            ItemData itemData = new ItemData("ContactId - " + cList.contactId + "\n" + builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);

                        }


                    }
                }

                MyAdapter mAdapter = new MyAdapter(mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });



        /*CQuery cQuery = CQuery.getInstance(this);
        cQuery.filter(IContactQuery.Filter.ONLY_ACCOUNT);
        cQuery.build(new ICCallback() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                List<ItemData> mListAdapter = new ArrayList<>();
                Toast.makeText(MainActivity.this, " Contacts count " + mList.size(), Toast.LENGTH_SHORT).show();
                if (mList != null && !mList.isEmpty()) {
                    for (CList cList : mList) {

                        CEmail cE = cList.getcEmail();

                        if (cE != null){
                            ItemData itemData = new ItemData(cList.contactId
                                    + "" + "\n Home - " + TextUtils.join(",", cList.getcEmail().getHome())
                                    + "\n Mobile " +
                                    TextUtils.join(",", cList.getcEmail().getMobile())
                                    + "\n Work - " + TextUtils.join(",", cList.getcEmail().getWork()) + "\n", uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);
                        }

                    }
                }

                MyAdapter mAdapter = new MyAdapter(mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });*/

       /* cQuery.build(new ICCallback() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                List<ItemData> mListAdapter = new ArrayList<>();
                if (mList != null && !mList.isEmpty()) {
                    for (CList cList : mList) {

                        CName cName = cList.getcName();

                        if (cName != null){
                            Log.d(TAG, "|Name|" + cList.getcName());

                            ItemData itemData = new ItemData("Id:" +cList.getId() + "\nCId:" + cList.getContactId() +"Display Name: "+cName.getDisplayName() + "" + "\n Family Name - " + cName.getFamilyName() + "\nGiven Name " + cName.getGivenName(), uriToBitmapConverter(cList.getPhotoUri()));

                            mListAdapter.add(itemData);
                        }

                    }
                }

                MyAdapter mAdapter = new MyAdapter(mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });*/
/*
        cQuery.build(new IGenericCallback() {
            @Override
            public void onContactSuccess(List<GenericCList> mList) {
                List<ItemData> mListAdapter = new ArrayList<>();
                if (mList != null && !mList.isEmpty()) {
                    for (GenericCList cList : mList) {

                        Log.d(TAG, "|Name|" + cList.getDisplayName());

                        ItemData itemData = new ItemData(cList.getDisplayName() + "" + "\n Home - " + TextUtils.join(",", cList.getcPhone().getHome()) + "\n Mobile " +
                                TextUtils.join(",", cList.getcPhone().getMobile()) + "\n Work - " + TextUtils.join(",", cList.getcPhone().getWork()) + "\n", uriToBitmapConverter(cList.getPhotoUri()));

                        mListAdapter.add(itemData);
                    }
                }

                MyAdapter mAdapter = new MyAdapter(mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onContactError(Throwable throwable) {


            }
        });
*/
    }


    Bitmap uriToBitmapConverter(String uriString) {
        try {
            Uri uri = Uri.parse(uriString);
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (FileNotFoundException fnf) {
            //fnf.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (NullPointerException e) {
            //e.printStackTrace();
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

    private void gettingPhoneContacts() {

        List<ItemData> mListAdapter = new ArrayList<>();

        ContentResolver cr = this.getContentResolver();

        // Read Contacts
        Cursor fetchCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        if (fetchCursor == null || fetchCursor.getCount() == 0)
            return;

        Map<String, CommonCList> cListMap = new HashMap<>();

        while (fetchCursor.moveToNext()) {

            String id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));
            String contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

            CommonCList commonCList = new CommonCList();

           /* IGenericQuery iGenericQuery = new BaseGenericCQuery(fetchCursor, cListMap, id, contactId);

            genericCList.setcPhone(iGenericQuery.getPhone());
            genericCList.setDisplayName(iGenericQuery.getName());
            genericCList.setId(id);
            genericCList.setContactId(contactId);
            genericCList.setPhotoUri(iGenericQuery.getPhotoUri());

            cListMap.put(contactId, genericCList);*/
        }

        ArrayList<CommonCList> commonCLists = new ArrayList<>(cListMap.values());

        for (CommonCList commonCList : commonCLists) {
            ItemData itemData = new ItemData(commonCList.getDisplayName() + "" + "\n Home - " + TextUtils.join(",", commonCList.getcPhone().getHome()) + "\n Mobile " +
                    TextUtils.join(",", commonCList.getcPhone().getMobile()) + "\n Work - " + TextUtils.join(",", commonCList.getcPhone().getWork()) + "\n", null);

            mListAdapter.add(itemData);

        }

        Toast.makeText(this, " total contact count " + commonCLists.size(), Toast.LENGTH_SHORT).show();


        MyAdapter mAdapter = new MyAdapter(mListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
