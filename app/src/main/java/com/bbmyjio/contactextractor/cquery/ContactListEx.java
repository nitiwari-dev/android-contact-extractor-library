package com.bbmyjio.contactextractor.cquery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.bbmyjio.contactextractor.i.IContactQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;

/**
 * ContactList Extractor using Rx android 2.0 to fetch and send to the ObservableTransformer
 * <p>
 * Created by Nitesh on 19-04-2017.
 */

class ContactListEx extends BaseContactListEx {

    private static final String TAG = ContactListEx.class.getSimpleName();
    private Context mContext;

    public ContactListEx(Context mContext) {
        this.mContext = mContext;
    }

    private ObservableTransformer<CList, CList> getCListTransformer(final List<Integer> mFilterType) {

        return new ObservableTransformer<CList, CList>() {
            @Override
            public ObservableSource<CList> apply(Observable<CList> upstream) {
                return upstream.flatMap(new Function<CList, ObservableSource<CList>>() {
                    @Override
                    public ObservableSource<CList> apply(CList cList) throws Exception {


                        IContactQuery iContactQuery = new BaseContactQuery(mContext, cList.getId());

                        if (mFilterType == null || mFilterType.size() == 0)
                            return Observable.just(fetchAll(iContactQuery, cList));

                        return Observable.just(queryFilterType(iContactQuery, mFilterType, cList));

                    }
                });
            }

        };
    }


    private ObservableTransformer<CList, CList> getCListTransformer() {
        return getCListTransformer(null);
    }

    Single<List<CList>> getList(final List<Integer> mFilterTypeList, final String orderBy,
                                final String limit, final String skip) {
        List<CList> mList = new ArrayList<>();

        return Observable.create(new ObservableOnSubscribe<CList>() {
            @Override
            public void subscribe(ObservableEmitter<CList> e) throws Exception {
                ContentResolver cr = mContext.getContentResolver();

                Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

                Cursor fetchCursor = cr.query(CONTENT_URI,
                        projections(), null, null, orderBy(orderBy, limit, skip));

                if (fetchCursor != null) {

                    Log.d(TAG, "|Start|" + new Date(System.currentTimeMillis()).toString()
                            + "\n Cursor Count" + fetchCursor.getCount());
                    int count = 0;
                    while (fetchCursor.moveToNext()) {
                        int identity = fetchCursor.getInt(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));

                        ++count;
                        CList cList = new CList();
                        cList.setId(String.valueOf(identity));
                        e.onNext(cList);
                    }


                    Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n No Phone " + (fetchCursor.getCount() - count));

                    fetchCursor.close();

                    mFilterTypeList.clear();
                    e.onComplete();

                }
            }
        }).compose(new ContactListEx(mContext).getCListTransformer(mFilterTypeList)).collectInto(mList, new BiConsumer<List<CList>, CList>() {
            @Override
            public void accept(List<CList> cLists, CList cList) throws Exception {
                Log.d(TAG, "clist size" + cLists.size());
                cLists.add(cList);
            }
        });
    }



    Single<List<CList>> getList(final String limit, final String skip) {
        return getList(null, limit, skip);
    }

    Single<List<CList>> getList(final String orderBy, final String limit, final String skip) {
        return getList(null, orderBy, limit, skip);
    }

    Single<List<CList>> getList() {
        return getList(null, null);
    }
}
