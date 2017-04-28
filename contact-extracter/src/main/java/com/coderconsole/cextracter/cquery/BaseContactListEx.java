package com.coderconsole.cextracter.cquery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.coderconsole.cextracter.cquery.base.CList;
import com.coderconsole.cextracter.cquery.common.CommonCList;
import com.coderconsole.cextracter.i.ICFilter;
import com.coderconsole.cextracter.i.IContactQuery;


/**
 * BaseContact List Extracter
 *
 * Created by Nitesh on 22-04-2017.
 */

public abstract class BaseContactListEx {

    private Context mContext;

    public BaseContactListEx(Context mContext) {
        this.mContext = mContext;
    }

    private static final String TAG = BaseContactListEx.class.getSimpleName();

    protected CList queryFilterType(IContactQuery icQuery, int mFilterType, CList cList) {

        switch (mFilterType) {
            case ICFilter.ONLY_ACCOUNT:
                cList.setcAccount(icQuery.getAccount());
                break;
            case ICFilter.ONLY_EMAIL:
                cList.setcEmail(icQuery.getEmail());
                break;
            case ICFilter.ONLY_PHONE:
                cList.setcPhone(icQuery.getPhone());
                break;
            case ICFilter.ONLY_NAME:
                cList.setcName(icQuery.getName());
                break;
            case ICFilter.ONLY_EVENTS:
                cList.setcEvents(icQuery.getEvents());
                break;
            case ICFilter.ONLY_ORGANISATION:
                cList.setcOrg(icQuery.getOrg());
                break;
            case ICFilter.ONLY_POSTCODE:
                cList.setcPostCode(icQuery.getPostCode());
                break;
            case ICFilter.ONLY_PHOTO_URI:
                cList.setPhotoUri(icQuery.getPhotoUri());
                break;
            case ICFilter.ONLY_GROUPS:
                cList.setcGroups(icQuery.getGroups());
                break;
            default:
                break;

        }

        return cList;
    }

    protected CommonCList queryFilterType(ICommonCQuery icQuery, int mFilterType, CommonCList cList) {

        switch (mFilterType) {
            case ICFilter.COMMON:
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


    protected Cursor getCursorByType(int type) {

        Uri CONTENT_URI = null;
        String selection = null;
        String selectionArgs[] = null;
        String projections[] = null;
        String orderBy = null;

        switch (type) {
            case ICFilter.COMMON:
                CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                break;
            case ICFilter.ONLY_EMAIL:
                CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                orderBy = ContactsContract.CommonDataKinds.Email.DATA;
                selection = ContactsContract.CommonDataKinds.Email.DATA + " != " + "\'\' AND " + ContactsContract.CommonDataKinds.Email.DATA + " NOT NULL";
                break;
            case ICFilter.ONLY_NAME:
                CONTENT_URI = ContactsContract.Data.CONTENT_URI;
                selection = ContactsContract.Data.MIMETYPE + " = ?";
                selectionArgs = new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                orderBy = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME;
                break;

            case ICFilter.ONLY_ACCOUNT:
                CONTENT_URI = ContactsContract.RawContacts.CONTENT_URI;
                orderBy = ContactsContract.RawContacts.CONTACT_ID;
                break;

            case ICFilter.ONLY_POSTCODE:
                CONTENT_URI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;

                break;

            case ICFilter.ONLY_ORGANISATION:
                CONTENT_URI = ContactsContract.Data.CONTENT_URI;
                selection = ContactsContract.Data.MIMETYPE + " = ?";
                selectionArgs = new String[]{
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                break;

            case ICFilter.ONLY_EVENTS:
                CONTENT_URI = ContactsContract.Data.CONTENT_URI;
                selection = ContactsContract.Data.MIMETYPE + " = ?";
                selectionArgs = new String[]{
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE};
                break;

            case ICFilter.ONLY_GROUPS:
                CONTENT_URI = ContactsContract.Groups.CONTENT_URI;
                break;
        }

        if (CONTENT_URI == null)
            return null;


        Cursor fetchCursor = mContext.getContentResolver().query(CONTENT_URI,
                projections, selection, selectionArgs, orderBy);

        return fetchCursor;

    }
}
