package com.bbmyjio.contactextractor.cquery;

import android.content.Context;
import android.database.Cursor;
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
 * Created by Nitesh on 25-04-2017.
 */

public class CListExtractor extends BaseContactListEx {
    private final static String TAG = CListExtractor.class.getSimpleName();

    public CListExtractor(Context mContext) {
        super(mContext);
    }


    Single<List<CList>> getList(final int mFilterType, final String orderBy, final String limit, final String skip ){

        return Single.create(new SingleOnSubscribe<List<CList>>() {
            @Override
            public void subscribe(SingleEmitter<List<CList>> emitter) throws Exception {
                Cursor fetchCursor = getCursorByType(mFilterType);

                if (fetchCursor == null) {
                    emitter.onError(new Exception("Cursor is null"));
                    return;
                }


                Log.d(TAG, "|Start|" + new Date(System.currentTimeMillis()).toString() + "\n Cursor Count" + fetchCursor.getCount());
                int count = 0;

                List<CList> cLists = new ArrayList<>();

                Map<String, CList> cListMap = new HashMap<>();

                while (fetchCursor.moveToNext())
                    cListMap = fillMap(fetchCursor, cListMap, mFilterType);

                cLists.addAll(cListMap.values());
                cListMap.clear();

                Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n No Phone " + (fetchCursor.getCount() - count));

                fetchCursor.close();
                emitter.onSuccess(cLists);

            }
        });
    }

    private Map<String, CList> fillMap(Cursor fetchCursor, Map<String, CList> cListMap, int mFilterType) {
        return null;
    }

}
