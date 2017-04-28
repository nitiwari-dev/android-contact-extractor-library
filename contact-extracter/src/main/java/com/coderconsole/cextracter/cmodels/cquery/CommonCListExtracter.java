package com.coderconsole.cextracter.cmodels.cquery;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * ContactList Extractor using Rx android 2.0 to fetch and send to the ObservableTransformer
 * <p>
 * Created by Nitesh on 19-04-2017.
 */

class CommonCListExtracter extends BaseContactListEx {

    private static final String TAG = CommonCListExtracter.class.getSimpleName();

    public CommonCListExtracter(Context mContext) {
        super(mContext);
    }


    Single<List<GenericCList>> getList(final int mFilterType, final String orderBy,
                                       final String limit, final String skip) {

        return Single.create(new SingleOnSubscribe<List<GenericCList>>() {
            @Override
            public void subscribe(SingleEmitter<List<GenericCList>> emitter) throws Exception {
                Cursor fetchCursor = getCursorByType(mFilterType);

                if (fetchCursor == null) {
                    emitter.onError(new Exception("Cursor is null"));
                    return;
                }


                Log.d(TAG, "|Start|" + new Date(System.currentTimeMillis()).toString() + "\n Cursor Count" + fetchCursor.getCount());
                int count = 0;

                List<GenericCList> genericCLists = new ArrayList<>();

                Map<String, GenericCList> cListMap = new HashMap<>();

                while (fetchCursor.moveToNext())
                    cListMap = fillMap(fetchCursor, cListMap, mFilterType);

                genericCLists.addAll(cListMap.values());
                cListMap.clear();

                Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n No Phone " + (fetchCursor.getCount() - count));

                fetchCursor.close();
                emitter.onSuccess(genericCLists);

            }
        });
    }

    private Map<String, GenericCList> fillMap(Cursor fetchCursor, Map<String, GenericCList> cListMap, int mFilterType) {
        String _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

        GenericCList cList;
        if (cListMap.containsKey(contactId))
            cList = cListMap.get(contactId);
        else {
            cList = new GenericCList();
            cList.setId(_id);
            cList.setContactId(contactId);
        }

        IGenericQuery iContactQuery = new BaseGenericCQuery(fetchCursor, cList, _id, contactId);
        cList = queryFilterType(iContactQuery, mFilterType, cList);
        cListMap.put(contactId, cList);

        return cListMap;
    }
}
