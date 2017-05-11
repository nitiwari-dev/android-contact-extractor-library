/*
 *   Copyright (C) 2017 Nitesh Tiwari.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.coderconsole.example.fragment;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coderconsole.example.R;
import com.coderconsole.example.adapter.ItemData;
import com.coderconsole.example.adapter.ContactAdapter;
import com.coderconsole.cextracter.cmodels.CAccount;
import com.coderconsole.cextracter.cmodels.CEmail;
import com.coderconsole.cextracter.cmodels.CEvents;
import com.coderconsole.cextracter.cmodels.CGroups;
import com.coderconsole.cextracter.cmodels.CName;
import com.coderconsole.cextracter.cmodels.COrganisation;
import com.coderconsole.cextracter.cmodels.CPhone;
import com.coderconsole.cextracter.cmodels.CPostBoxCity;
import com.coderconsole.cextracter.cmodels.ContactGenericType;
import com.coderconsole.cextracter.cquery.CQuery;
import com.coderconsole.cextracter.cquery.base.CList;
import com.coderconsole.cextracter.i.ICFilter;
import com.coderconsole.cextracter.i.IContact;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactInfoFragment extends Fragment {

    public static final String ARG_PARAM_FILTER = "arg_filter";
    private View parentView;
    private RecyclerView mRecyclerView;

    private static final String SEPERATOR = " / ";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_contact_info, container, false);
        return parentView;
    }

    public static ContactInfoFragment newInstance(int cType) {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_FILTER, cType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        Bundle bundle = getArguments();
        if (bundle == null)
            return;

        int filterType = bundle.getInt(ARG_PARAM_FILTER, -1);

        if (filterType == -1) return;

        readAndFillContacts(filterType);
    }

    private void initView() {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration();
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.contactRecyclerView);
        mRecyclerView.addItemDecoration(itemDecoration);
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


    private void readAndFillContacts(final int filterType) {
        CQuery cQuery = CQuery.getInstance(getActivity());
        cQuery.filter(filterType);
        cQuery.build(new IContact() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                List<ItemData> mListAdapter = new ArrayList<>();
                //Toast.makeText(getActivity(), " Contacts count " + mList.size(), Toast.LENGTH_SHORT).show();
                if (mList != null && !mList.isEmpty()) {
                    for (CList cList : mList) {
                        setUpContactList(filterType, cList, mListAdapter);
                    }
                }

                ContactAdapter mAdapter = new ContactAdapter(getActivity(), mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onContactError(Throwable throwable) {
                Toast.makeText(getActivity(), "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpContactList(int filter, CList cList, List<ItemData> mListAdapter) {

        switch (filter) {
            case ICFilter.ONLY_NAME:

                CName cName = cList.getcName();

                if (cName != null) {

                    StringBuilder builder = new StringBuilder();
                    builder.append("Display Name - " + cName.getDisplayName()).append(System.getProperty("line.separator"));
                    builder.append("Given Name - " + cName.getGivenName()).append(System.getProperty("line.separator"));
                    builder.append("Family Name - " + cName.getFamilyName()).append(System.getProperty("line.separator"));

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cName.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_PHONE:

                CPhone cPhone = cList.getcPhone();
                if (cPhone != null) {
                    StringBuilder builder = new StringBuilder();

                    if (cPhone.getHome().size() > 0)
                        builder.append("Home - " + TextUtils.join(SEPERATOR, cPhone.getHome())).append(System.getProperty("line.separator"));

                    if (cPhone.getWork().size() > 0)
                        builder.append("Work - " + TextUtils.join(SEPERATOR, cPhone.getWork())).append(System.getProperty("line.separator"));

                    if (cPhone.getMobile().size() > 0)
                        builder.append("Mobile - " + TextUtils.join(SEPERATOR, cPhone.getMobile())).append(System.getProperty("line.separator"));

                    if (cPhone.getOther().size() > 0)
                        builder.append("Other - " + TextUtils.join(SEPERATOR, cPhone.getOther())).append(System.getProperty("line.separator"));
                    ItemData itemData = new ItemData(cPhone.getDisplayName(), builder.toString(), uriToBitmapConverter(cPhone.getPhotoUri()));
                    mListAdapter.add(itemData);
                }


                break;

            case ICFilter.ONLY_EMAIL:

                CEmail cEmail = cList.getcEmail();

                if (cEmail != null) {

                    StringBuilder builder = new StringBuilder();

                    if (cEmail.getHome().size() > 0)
                        builder.append("Home - ").append(TextUtils.join(SEPERATOR, cEmail.getHome())).append(System.getProperty("line.separator"));

                    if (cEmail.getWork().size() > 0)
                        builder.append("Work - ").append(TextUtils.join(SEPERATOR, cEmail.getWork())).append(System.getProperty("line.separator"));

                    if (cEmail.getMobile().size() > 0)
                        builder.append("Mobile - ").append(TextUtils.join(SEPERATOR, cEmail.getMobile())).append(System.getProperty("line.separator"));

                    if (cEmail.getOther().size() > 0)
                        builder.append("Other - ").append(TextUtils.join(SEPERATOR, cEmail.getOther())).append(System.getProperty("line.separator"));

                    ItemData itemData = new ItemData(cList.getContactId(), builder.toString(), uriToBitmapConverter(cEmail.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_ACCOUNT:
                CAccount cAccount = cList.getcAccount();

                if (cAccount != null) {

                    StringBuilder builder = new StringBuilder();
                    for (ContactGenericType contactGenericType : cAccount.getmGenericType()) {
                        builder.append("Name - " + contactGenericType.name).append(System.getProperty("line.separator") + contactGenericType.type);
                    }
                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                    mListAdapter.add(itemData);

                }
                break;

            case ICFilter.ONLY_ORGANISATION:

                COrganisation org = cList.getcOrg();

                if (org != null) {

                    StringBuilder builder = new StringBuilder();
                    for (COrganisation.CompanyDepart companyDepart : org.getCompanyOrgList()) {
                        builder.append("Company - " + companyDepart.getCompany()).append(System.getProperty("line.separator")).append("Org - " + companyDepart.getOrg());
                    }

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                    mListAdapter.add(itemData);

                }
                break;

            case ICFilter.ONLY_EVENTS:
                CEvents events = cList.getcEvents();


                if (events != null) {
                    StringBuilder builder = new StringBuilder();

                    if (!TextUtils.isEmpty(events.getBirthDay()))
                        builder.append("Birthday - " + events.getBirthDay());

                    if (!TextUtils.isEmpty(events.getAnniversay()))
                        builder.append(System.getProperty("line.separator")).append("Anniversary - " + events.getAnniversay());

                    ItemData itemData = new ItemData(events.getDisplayName(), builder.toString(), uriToBitmapConverter(events.getPhotoUri()));
                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_GROUPS:
                CGroups groups = cList.getcGroups();

                if (groups != null) {

                    StringBuilder builder = new StringBuilder();

                    for (CGroups.BaseGroups baseGroups : groups.getmList()) {

                        builder.append("Id - " + baseGroups.getId());

                        if (!TextUtils.isEmpty(baseGroups.getTitle()))
                            builder.append(System.getProperty("line.separator")).append("Title - " + baseGroups.getTitle());

                    }

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_POSTCODE:
                CPostBoxCity postBoxCity = cList.getcPostCode();

                if (postBoxCity != null) {

                    StringBuilder builder = new StringBuilder();
                    for (CPostBoxCity.PostCity postCity : postBoxCity.getmPostCity()) {
                        builder.append("City-" + postCity.getCity()).append(System.getProperty("line.separator")).append("Post " + postCity.getPost());
                    }

                    ItemData itemData = new ItemData(postBoxCity.getDisplayName(), builder.toString(), uriToBitmapConverter(postBoxCity.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_PHOTO_URI:
                String photoUri = cList.getPhotoUri();

                if (photoUri != null) {

                    StringBuilder builder = new StringBuilder();

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            default:
                break;


        }
    }


    Bitmap uriToBitmapConverter(String uriString) {
        try {
            Uri uri = Uri.parse(uriString);
            return MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (FileNotFoundException fnf) {
            //fnf.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }

        return null;
    }
}
