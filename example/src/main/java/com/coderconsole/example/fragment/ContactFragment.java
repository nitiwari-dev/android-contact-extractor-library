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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coderconsole.example.R;
import com.coderconsole.cextracter.i.ICFilter;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private static final String ARG_CONTACT_TYPE = "contactTypeParam";

    private String mContactType;


    private View mParentView;
    private ViewPager mcViewPager;
    private TabLayout mTabLayout;

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance(String cType) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTACT_TYPE, cType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContactType = getArguments().getString(ARG_CONTACT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_contact, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTabLayout = (TabLayout) mParentView.findViewById(R.id.cTabsLayout);
        mcViewPager = (ViewPager) mParentView.findViewById(R.id.cViewPager);

        fillAdapter();

    }

    private void fillAdapter() {

        ContactViewPagerAdapter adapter = new ContactViewPagerAdapter(getFragmentManager());
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_PHONE), "Phone");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_EMAIL), "Email");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_NAME), "Name");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_ACCOUNT), "Account");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_EVENTS), "Events");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_ORGANISATION), "Org");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_POSTCODE), "PostCode");
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.ONLY_GROUPS), "Groups");

        mcViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mcViewPager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    class ContactViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ContactViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
