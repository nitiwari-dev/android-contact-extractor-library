package com.bbmyjio.contactextractor.cquery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.bbmyjio.contactextractor.i.IContactQuery;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Nitesh on 22-04-2017.
 */

abstract class BaseContactListEx {

    private Context mContext;

    public BaseContactListEx(Context mContext) {
        this.mContext = mContext;
    }

    private static final String TAG = BaseContactListEx.class.getSimpleName();

    /*protected CList fetchAll(IContactQuery icQuery, CList cList) {
        cList.setcAccount(icQuery.getAccount());
        cList.setcEmail(icQuery.getEmail());
        cList.setcPhone(icQuery.getPhone());
        cList.setcName(icQuery.getName());
        cList.setcEvents(icQuery.getEvents());
        cList.setcOrg(icQuery.getOrg());
        cList.setcPostCode(icQuery.getPostCode());
        cList.setPhotoUri(icQuery.getPhotoUri());
        return cList;
    }*/

    protected CList queryFilterType(IContactQuery icQuery, List<Integer> mFilterType, CList cList) {

        for (Integer integer : mFilterType) {
            switch (integer) {
                case IContactQuery.Filter.ONLY_ACCOUNT:
                    cList.setcAccount(icQuery.getAccount());
                    break;
                case IContactQuery.Filter.ONLY_EMAIL:
                    cList.setcEmail(icQuery.getEmail());
                    break;
                case IContactQuery.Filter.ONLY_PHONE:
                    cList.setcPhone(icQuery.getPhone());
                    break;
                case IContactQuery.Filter.ONLY_NAME:
                    cList.setcName(icQuery.getName());
                    break;
                case IContactQuery.Filter.ONLY_EVENTS:
                    cList.setcEvents(icQuery.getEvents());
                    break;
                case IContactQuery.Filter.ONLY_ORGANISATION:
                    cList.setcOrg(icQuery.getOrg());
                    break;
                case IContactQuery.Filter.ONLY_POSTCODE:
                    cList.setcPostCode(icQuery.getPostCode());
                    break;
                case IContactQuery.Filter.ONLY_PHOTO_URI:
                    cList.setPhotoUri(icQuery.getPhotoUri());
                    break;
                default:
                    break;
            }
        }

        return cList;
    }

    protected GenericCList queryFilterType(IGenericQuery icQuery, int mFilterType, GenericCList cList) {

        switch (mFilterType) {
            case IContactQuery.Filter.COMMON:
                cList.setcPhone(icQuery.getPhone());
                cList.setDisplayName(icQuery.getName());
                cList.setPhotoUri(icQuery.getPhotoUri());
                break;
            default:
                break;
        }

        return cList;
    }

    protected String orderBy(String orderBy, String limit, String skip) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(!TextUtils.isEmpty(orderBy) ? orderBy : ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        sBuilder.append(!TextUtils.isEmpty(limit) && TextUtils.isDigitsOnly(limit) ? " limit " + limit + " " : " ");
        sBuilder.append(!TextUtils.isEmpty(skip) && TextUtils.isDigitsOnly(skip) ? " offset " + skip + " " : " ");

        return sBuilder.toString();
    }


    protected String[] projections() {

        String[] projections = new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.HAS_PHONE_NUMBER};

        return projections;
    }


    protected Cursor getCursorByType(int type) {

        switch (type) {
            case IContactQuery.Filter.COMMON:
                Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                Cursor fetchCursor = mContext.getContentResolver().query(CONTENT_URI,
                        null, null, null, null);

                return fetchCursor;
        }

        return null;

    }
}
