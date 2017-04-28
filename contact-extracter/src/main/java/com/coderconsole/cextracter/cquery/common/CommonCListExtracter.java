package com.coderconsole.cextracter.cquery.common;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.coderconsole.cextracter.cquery.BaseContactListEx;
import com.coderconsole.cextracter.cquery.ICommonCQuery;

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

public class CommonCListExtracter extends BaseContactListEx {

    private static final String TAG = CommonCListExtracter.class.getSimpleName();

    public CommonCListExtracter(Context mContext) {
        super(mContext);
    }


    public Single<List<CommonCList>> getList(final int mFilterType, final String orderBy,
                                      final String limit, final String skip) {

        return Single.create(new SingleOnSubscribe<List<CommonCList>>() {
            @Override
            public void subscribe(SingleEmitter<List<CommonCList>> emitter) throws Exception {
                Cursor fetchCursor = getCursorByType(mFilterType);

                if (fetchCursor == null) {
                    emitter.onError(new Exception("Cursor is null"));
                    return;
                }


                Log.d(TAG, "|Start|" + new Date(System.currentTimeMillis()).toString() + "\n Cursor Count" + fetchCursor.getCount());
                int count = 0;

                List<CommonCList> commonCLists = new ArrayList<>();

                Map<String, CommonCList> cListMap = new HashMap<>();

                while (fetchCursor.moveToNext())
                    cListMap = fillMap(fetchCursor, cListMap, mFilterType);

                commonCLists.addAll(cListMap.values());
                cListMap.clear();

                Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n No Phone " + (fetchCursor.getCount() - count));

                fetchCursor.close();
                emitter.onSuccess(commonCLists);

            }
        });
    }

    private Map<String, CommonCList> fillMap(Cursor fetchCursor, Map<String, CommonCList> cListMap, int mFilterType) {
        String _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

        CommonCList cList;
        if (cListMap.containsKey(contactId))
            cList = cListMap.get(contactId);
        else {
            cList = new CommonCList();
            cList.setId(_id);
            cList.setContactId(contactId);
        }

        ICommonCQuery iContactQuery = new BaseCommonCCQuery(fetchCursor, cList, _id, contactId);
        cList = queryFilterType(iContactQuery, mFilterType, cList);
        cListMap.put(contactId, cList);

        return cListMap;
    }
}
