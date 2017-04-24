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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * ContactList Extractor using Rx android 2.0 to fetch and send to the ObservableTransformer
 * <p>
 * Created by Nitesh on 19-04-2017.
 */

class CListExtracter extends BaseContactListEx {

    private static final String TAG = CListExtracter.class.getSimpleName();
    private Context mContext;

    public CListExtracter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    /*public ObservableTransformer<CList, CList> getCListTransformer(final List<Integer> mFilterType) {

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
    }*/


    /*private ObservableTransformer<CList, CList> getCListTransformer() {
        return getCListTransformer(null);
    }*/

    /*Single<List<CList>> getList(final List<Integer> mFilterTypeList, final String orderBy,
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
    }*/


    /*Single<List<CList>> getList(final List<Integer> mFilterTypeList, final String orderBy,
                                final String limit, final String skip) {
        List<CList> mList = new ArrayList<>();

        final Map<String, CList> cListMap = new HashMap<>();

        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                ContentResolver cr = mContext.getContentResolver();

                Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                Cursor fetchCursor = cr.query(CONTENT_URI,
                        null, null, null, null);

                if (fetchCursor != null) {

                    Log.d(TAG, "|Start|" + new Date(System.currentTimeMillis()).toString()
                            + "\n Cursor Count" + fetchCursor.getCount());
                    int count = 0;

                    if (fetchCursor.getCount() > 0) {
                        while (fetchCursor.moveToNext()) {
                            e.onNext(fetchCursor);
                        }

                    }

                    Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n No Phone " + (fetchCursor.getCount() - count));

                    fetchCursor.close();

                    mFilterTypeList.clear();
                    e.onComplete();

                }
            }
        }).flatMap(new Function<Cursor, ObservableSource<CList>>() {
            @Override
            public ObservableSource<CList> apply(Cursor fetchCursor) throws Exception {

                for (String nm :fetchCursor.getColumnNames()){
                    Log.d(TAG, "" + nm);
                }

                String _id = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));

                String contactId = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                CList cList;
                if (cListMap.containsKey(contactId))
                    cList = cListMap.get(contactId);
                else {
                    cList = new CList();
                    cList.setId(_id);
                    cList.setContactId(contactId);
                }

                IContactQuery iContactQuery = new BaseContactQuery(mContext, fetchCursor, null);

                if (mFilterTypeList == null || mFilterTypeList.size() == 0) {
                    cList = fetchAll(iContactQuery, cList);
                    cListMap.put(contactId, cList);
                    return Observable.just(cList);
                }

                cList = queryFilterType(iContactQuery, mFilterTypeList, cList);

                cListMap.put(contactId, cList);
                return Observable.just(cList);
            }
        }).collectInto(mList, new BiConsumer<List<CList>, CList>() {
            @Override
            public void accept(List<CList> cLists, CList cList) throws Exception {
                cLists.add(cList);
            }
        });
    }*/


    Single<List<GenericCList>> getList(final int mFilterType, final String orderBy,
                                       final String limit, final String skip) {
        final List<GenericCList> mList = new ArrayList<>();

        final Map<String, GenericCList> cListMap = new HashMap<>();

        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                Cursor fetchCursor = getCursorByType(mFilterType);

                if (fetchCursor != null) {

                    Log.d(TAG, "|Start|" + new Date(System.currentTimeMillis()).toString() + "\n Cursor Count" + fetchCursor.getCount());
                    int count = 0;

                    if (fetchCursor.getCount() > 0) {
                        while (fetchCursor.moveToNext()) {
                            e.onNext(fetchCursor);
                        }
                    }

                    Log.d(TAG, "|End" + new Date(System.currentTimeMillis()).toString() + "\n No Phone " + (fetchCursor.getCount() - count));

                    cListMap.clear();
                    fetchCursor.close();
                    e.onComplete();

                }
            }
        }).flatMap(new Function<Cursor, ObservableSource<Map<String, GenericCList>>>() {
            @Override
            public ObservableSource<Map<String, GenericCList>> apply(Cursor fetchCursor) throws Exception {

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
                return Observable.just(cListMap);
            }
        }).collectInto(mList, new BiConsumer<List<GenericCList>, Map<String, GenericCList>>() {
            @Override
            public void accept(List<GenericCList> genericCLists, Map<String, GenericCList> stringGenericCListMap) throws Exception {
                mList.clear();
                mList.addAll(stringGenericCListMap.values());
            }
        });
    }




   /* Single<List<CList>> getList(final String limit, final String skip) {
        return getList(null, limit, skip);
    }

    Single<List<CList>> getList(final String orderBy, final String limit, final String skip) {
        return getList(null, orderBy, limit, skip);
    }

    Single<List<CList>> getList() {
        return getList(null, null);
    }*/
}
