package com.bbmyjio.contactextractor.fragment;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bbmyjio.contactextractor.R;
import com.bbmyjio.contactextractor.adapter.ItemData;
import com.bbmyjio.contactextractor.adapter.MyAdapter;
import com.coderconsole.cextracter.cmodels.CGroups;
import com.coderconsole.cextracter.cquery.CQuery;
import com.coderconsole.cextracter.cquery.base.CList;
import com.coderconsole.cextracter.i.ICFilter;
import com.coderconsole.cextracter.i.IContact;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 28-04-2017.
 */

public class ContactInfoFragment extends Fragment {

    public static final String ARG_PARAM_FILTER = "arg_filter";
    private View parentView;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_contact_info, container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
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


    private void readAndFillContacts() {

        CQuery cQuery = CQuery.getInstance(getActivity());
        cQuery.filter(ICFilter.ONLY_GROUPS);
        cQuery.build(new IContact() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                List<ItemData> mListAdapter = new ArrayList<>();
                Toast.makeText(getActivity(), " Contacts count " + mList.size(), Toast.LENGTH_SHORT).show();
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
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });

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
