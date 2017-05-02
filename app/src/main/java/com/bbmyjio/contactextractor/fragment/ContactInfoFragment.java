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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bbmyjio.contactextractor.R;
import com.bbmyjio.contactextractor.adapter.ItemData;
import com.bbmyjio.contactextractor.adapter.MyAdapter;
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
                Toast.makeText(getActivity(), " Contacts count " + mList.size(), Toast.LENGTH_SHORT).show();
                if (mList != null && !mList.isEmpty()) {
                    for (CList cList : mList) {
                        setUpContactList(filterType, cList, mListAdapter);
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

    private void setUpContactList(int filter, CList cList, List<ItemData> mListAdapter) {

        switch (filter) {
            case ICFilter.ONLY_NAME:

                CName cName = cList.getcName();

                if (cName != null) {

                    StringBuilder builder = new StringBuilder();
                    builder.append("Display Name - " + cName.getDisplayName() + "\n");
                    builder.append("Given Name - " + cName.getGivenName() + "\n");
                    builder.append("Family Name - " + cName.getFamilyName() + "\n");

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cName.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_PHONE:

                CPhone cPhone = cList.getcPhone();
                if (cPhone != null) {
                    StringBuilder builder = new StringBuilder();

                    builder.append(TextUtils.join(",", cPhone.getHome()));
                    builder.append(TextUtils.join(",", cPhone.getWork()));
                    builder.append(TextUtils.join(",", cPhone.getMobile()));
                    builder.append(TextUtils.join(",", cPhone.getOther()));
                    ItemData itemData = new ItemData(cPhone.getDisplayName(), builder.toString(), uriToBitmapConverter(cPhone.getPhotoUri()));
                    mListAdapter.add(itemData);
                }


                break;

            case ICFilter.ONLY_EMAIL:

                CEmail cEmail = cList.getcEmail();

                if (cEmail != null) {

                    StringBuilder builder = new StringBuilder();

                    if (cEmail.getHome().size() > 0)
                        builder.append("Home-").append(TextUtils.join(",", cEmail.getHome())).append("\n");

                    if (cEmail.getWork().size() > 0)
                        builder.append("Work-").append(TextUtils.join(",", cEmail.getWork())).append("\n");

                    if (cEmail.getMobile().size() > 0)
                        builder.append("Mobile-").append(TextUtils.join(",", cEmail.getMobile())).append("\n");

                    if (cEmail.getOther().size() > 0)
                        builder.append("Other-").append(TextUtils.join(",", cEmail.getOther())).append("\n");

                    ItemData itemData = new ItemData(cList.getContactId(), builder.toString(), uriToBitmapConverter(cEmail.getPhotoUri()));

                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_ACCOUNT:
                CAccount cAccount = cList.getcAccount();

                if (cAccount != null) {

                    StringBuilder builder = new StringBuilder();
                    for (ContactGenericType contactGenericType : cAccount.getmGenericType()) {
                        builder.append("Name - " + contactGenericType.name).append("\n" + contactGenericType.type);
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
                        builder.append("Company-" + companyDepart.getCompany()).append("\nOrg-" + companyDepart.getOrg());
                    }

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

                    mListAdapter.add(itemData);

                }
                break;

            case ICFilter.ONLY_EVENTS:
                CEvents events = cList.getcEvents();


                if (events != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("Birthday-" + events.getBirthDay() + "\nAnniversary-" + events.getAnniversay());
                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));
                    mListAdapter.add(itemData);

                }

                break;

            case ICFilter.ONLY_GROUPS:
                CGroups groups = cList.getcGroups();

                if (groups != null) {

                    StringBuilder builder = new StringBuilder();

                    for (CGroups.BaseGroups baseGroups : groups.getmList()) {
                        builder.append("Title-" + baseGroups.getTitle() + "\nId" + baseGroups.getId());
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
                        builder.append("City-" + postCity.getCity()).append("\nPost " + postCity.getPost());
                    }

                    ItemData itemData = new ItemData(cList.contactId, builder.toString(), uriToBitmapConverter(cList.getPhotoUri()));

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
