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

package com.coderconsole.cextracter.cquery.base;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.coderconsole.cextracter.i.ICFilter;
import com.coderconsole.cextracter.i.IContactQuery;


/**
 * BaseContact List Extracter
 * <p>
 * Created by Nitesh on 22-04-2017.
 */

public abstract class AbstractContactListExtractor {

    private Context mContext;

    public AbstractContactListExtractor(Context mContext) {
        this.mContext = mContext;
    }

    private static final String TAG = AbstractContactListExtractor.class.getSimpleName();

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
            case ICFilter.ONLY_GROUPS:
                cList.setcGroups(icQuery.getGroups());
                break;
            default:
                break;

        }

        return cList;
    }

    private String getOrderByQuery(String orderBy, String limit, String skip, String defaultOrderBy) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(!TextUtils.isEmpty(orderBy) ? orderBy : defaultOrderBy + " ASC ");
        sBuilder.append(!TextUtils.isEmpty(limit) && TextUtils.isDigitsOnly(limit) ? " limit " + limit + " " : " ");
        sBuilder.append(!TextUtils.isEmpty(skip) && TextUtils.isDigitsOnly(skip) ? " offset " + skip + " " : " ");

        return sBuilder.toString();
    }


    protected Cursor getCursorByType(int type) {
        return getCursorByType(type, null);
    }

    protected Cursor getCursorByType(int type, String orderBy) {
        return getCursorByType(type, orderBy, null);
    }

    protected Cursor getCursorByType(int type, String orderBy, String limit) {
        return getCursorByType(type, orderBy, limit, null);
    }

    protected Cursor getCursorByType(int type, String orderBy, String limit, String skip) {

        Uri CONTENT_URI;
        String selection = null;
        String selectionArgs[] = null;
        String projections[] = null;

        switch (type) {
            case ICFilter.ONLY_EMAIL:
                CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                selection = ContactsContract.CommonDataKinds.Email.DATA + " != " + "\'\' AND " + ContactsContract.CommonDataKinds.Email.DATA + " NOT NULL";
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.Email.DATA);
                break;
            case ICFilter.ONLY_NAME:
                CONTENT_URI = ContactsContract.Data.CONTENT_URI;
                selection = ContactsContract.Data.MIMETYPE + " = ?";
                selectionArgs = new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);
                break;

            case ICFilter.ONLY_ACCOUNT:
                CONTENT_URI = ContactsContract.RawContacts.CONTENT_URI;
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.RawContacts.CONTACT_ID);
                break;

            case ICFilter.ONLY_POSTCODE:
                CONTENT_URI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID);
                break;

            case ICFilter.ONLY_ORGANISATION:
                CONTENT_URI = ContactsContract.Data.CONTENT_URI;
                selection = ContactsContract.Data.MIMETYPE + " = ?";
                selectionArgs = new String[]{
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};

                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.Organization.CONTACT_ID);

                break;

            case ICFilter.ONLY_EVENTS:
                CONTENT_URI = ContactsContract.Data.CONTENT_URI;
                selection = ContactsContract.Data.MIMETYPE + " = ?";
                selectionArgs = new String[]{
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE};

                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.Event.CONTACT_ID);

                break;

            case ICFilter.ONLY_GROUPS:
                CONTENT_URI = ContactsContract.Groups.CONTENT_URI;
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.Groups._ID);
                break;

            case ICFilter.ONLY_PHONE:
                CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                break;

            default:
                CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                orderBy = getOrderByQuery(orderBy, limit, skip, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                break;
        }


        return mContext.getContentResolver().query(CONTENT_URI,
                projections, selection, selectionArgs, orderBy);

    }
}
