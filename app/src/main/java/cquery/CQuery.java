package cquery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;

import com.bbmyjio.contactextractor.common.permissions.PermissionWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import i.ICCallback;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.BiConsumerSingleObserver;
import io.reactivex.internal.schedulers.IoScheduler;

/**
 * CQuery to fetch the query for contact
 * <p>
 * Created by Nitesh on 03-04-2017.
 */

public class CQuery extends BaseCQuery {

    private static final String TAG = CQuery.class.getSimpleName();
    private static CQuery cQuery;

    private Context mContext;

    private String limits;

    private String skip;

    private ICCallback icQuery;

    private CQuery(Context context) {
        super(context, null);
        this.mContext = context;
    }

    public final static CQuery getInstance(Context context) {
        if (cQuery == null) {
            cQuery = new CQuery(context);
        }

        return cQuery;
    }


    public void build(final ICCallback iContact) {

        if (!PermissionWrapper.hasContactsPermissions(mContext)) {
            throw new SecurityException("Contact Permission Missing");
        }

        getList().subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new BiConsumerSingleObserver<>(new BiConsumer<List<CList>, Throwable>() {
            @Override
            public void accept(List<CList> cLists, Throwable throwable) throws Exception {
                if (iContact == null)
                    return;

                if (throwable == null) iContact.onContactSuccess(cLists);
                else iContact.onContactError(throwable);
            }
        }));
    }

    private Single<List<CList>> getList() {
        List<CList> mList = new ArrayList<>();

        return Observable.create(new ObservableOnSubscribe<CList>() {
            @Override
            public void subscribe(ObservableEmitter<CList> e) throws Exception {
                ContentResolver cr = getCR();
                Cursor fetchCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if (fetchCursor != null) {

                    Log.d(TAG, "|Starting time to fetch" + new Date(System.currentTimeMillis()).toString());
                    while (fetchCursor.moveToNext()) {
                        int identity = fetchCursor.getInt(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));

                        if (Integer.parseInt(fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            CList c = new CList();
                            c.id = String.valueOf(identity);
                            e.onNext(c);
                        }
                    }

                    Log.d(TAG, "|End time to fetch" + new Date(System.currentTimeMillis()).toString());


                    fetchCursor.close();

                }
            }
        }).compose(getCListTransformer()).collectInto(mList, new BiConsumer<List<CList>, CList>() {
            @Override
            public void accept(List<CList> cLists, CList cList) throws Exception {
                Log.d(TAG, "clist size" + cLists.size());
                cLists.add(cList);
            }
        });
    }


    private ObservableTransformer<CList, CList> getCListTransformer() {
        return new ObservableTransformer<CList, CList>() {
            @Override
            public ObservableSource<CList> apply(Observable<CList> upstream) {
                return upstream.flatMap(new Function<CList, ObservableSource<CList>>() {
                    @Override
                    public ObservableSource<CList> apply(CList cList) throws Exception {
                        Log.d(TAG, "|within transformer|" + new Date(System.currentTimeMillis()).toString());
                        CEmailQuery emailQuery = new CEmailQuery(mContext, cList.getId());
                        cList.setEmail(emailQuery.getEmail());

                        CPhoneQuery cPhoneQuery = new CPhoneQuery(mContext, cList.getId());
                        cList.setPhone(cPhoneQuery.getPhone());
                        return Observable.just(cList);

                    }
                });
            }
        };
    }
}
