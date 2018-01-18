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
import android.provider.ContactsContract;
import android.util.Log;

import com.coderconsole.cextracter.i.ICFilter;
import com.coderconsole.cextracter.i.IContactQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Contact List Extractor
 * <p>
 * Created by Nitesh on 25-04-2017.
 */

public class CListExtractorAbstract extends AbstractContactListExtractor {
    private final static String TAG = CListExtractorAbstract.class.getSimpleName();

    private final Context mContext;

    public CListExtractorAbstract(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


    public Single<List<CList>> getList(final int mFilterType, final String orderBy, final String limit, final String skip) {

        return Single.create(new SingleOnSubscribe<List<CList>>() {
            @Override
            public void subscribe(SingleEmitter<List<CList>> emitter) throws Exception {
                Cursor fetchCursor = getCursorByType(mFilterType, orderBy);

                if (fetchCursor == null) {
                    emitter.onError(new Exception("Cursor is null"));
                    return;
                }


                Log.d(TAG, "|Start Filter Type  = " + mFilterType + " " + new Date(System.currentTimeMillis()).toString() + "\n Cursor Count" + fetchCursor.getCount());
                emitter.onSuccess(new ArrayList<>(fillMap(fetchCursor, mFilterType).values()));

            }
        });
    }

    private Map<String, CList> fillMap(Cursor fetchCursor, int mFilterType) {

        Map<String, CList>  cListMap = new HashMap<>();
        while (fetchCursor.moveToNext()){
            String _id = null;
            String contactId = null;

            switch (mFilterType) {
                case ICFilter.ONLY_NAME:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID));
                    break;

                case ICFilter.ONLY_EMAIL:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
                    break;

                case ICFilter.ONLY_PHONE:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    break;

                case ICFilter.ONLY_ACCOUNT:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.RawContacts._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
                    break;

                case ICFilter.ONLY_POSTCODE:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID));
                    break;

                case ICFilter.ONLY_ORGANISATION:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.CONTACT_ID));
                    break;

                case ICFilter.ONLY_EVENTS:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event._ID));
                    contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.CONTACT_ID));
                    break;
                case ICFilter.ONLY_GROUPS:
                    _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Groups._ID));
                    contactId = _id;
                    break;

                default:
                    break;
            }

            CList cList;
            if (cListMap.containsKey(contactId))
                cList = cListMap.get(contactId);
            else {
                cList = new CList();
                cList.setId(_id);
                cList.setContactId(contactId);
            }

            IContactQuery iContactQuery = new BaseContactQueryCB(mContext, fetchCursor, cList, null);
            cList = queryFilterType(iContactQuery, mFilterType, cList);

            cListMap.put(contactId, cList);
        }


        Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n Count" + (cListMap.values().size()));


        if (!fetchCursor.isClosed())
            fetchCursor.close();


        return cListMap;
    }

}
