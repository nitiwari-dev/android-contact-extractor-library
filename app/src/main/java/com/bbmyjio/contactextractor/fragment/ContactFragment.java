package com.bbmyjio.contactextractor.fragment;

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

import com.bbmyjio.contactextractor.R;
import com.coderconsole.cextracter.i.ICFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {
    private static final String ARG_CONTACT_TYPE = "contactTypeParam";

    private String mContactType;


    private View mParentView;
    private ViewPager mcViewPager;
    private TabLayout mTabLayout;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cType Parameter 1.
     * @return A new instance of fragment ContactFragment.
     */
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
        adapter.addFrag(ContactInfoFragment.newInstance(ICFilter.COMMON), "Common");

        mcViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mcViewPager);
    }

    private Bundle addParam(int common) {
        Bundle bundle = new Bundle();
        bundle.putInt(ContactInfoFragment.ARG_PARAM_FILTER, common);
        return bundle;
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
