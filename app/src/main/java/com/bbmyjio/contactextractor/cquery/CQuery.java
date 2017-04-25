package com.bbmyjio.contactextractor.cquery;

import android.content.Context;

import com.bbmyjio.contactextractor.common.permissions.PermissionWrapper;

import java.util.List;

import com.bbmyjio.contactextractor.i.ICCallback;
import com.bbmyjio.contactextractor.i.IGenericCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.observers.BiConsumerSingleObserver;
import io.reactivex.internal.schedulers.IoScheduler;

/**
 * CQuery to fetch the query for contact
 * <p>
 * Created by Nitesh on 03-04-2017.
 */

public class CQuery {

    private static final String TAG = CQuery.class.getSimpleName();

    private static CQuery cQuery;

    private Context mContext;

    private String limit;

    private String skip;

    private String orderBy;

    private int mListFilterType;


    private CQuery(Context context) {
        this.mContext = context;
    }

    public final static CQuery getInstance(Context context) {
        if (cQuery == null) {
            cQuery = new CQuery(context);
        }

        return cQuery;
    }

    public CQuery limit(String limit) {
        this.limit = limit;
        return cQuery;
    }

    public CQuery skip(String skip) {
        this.skip = skip;
        return cQuery;
    }

    public void build(final ICCallback iContact) {

       if (!PermissionWrapper.hasContactsPermissions(mContext)) {
            throw new SecurityException("Contact Permission Missing");
        }

        new CListExtractor(mContext).getList(mListFilterType, orderBy, limit, skip)
                .subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<CList>, Throwable>() {
                    @Override
                    public void accept(List<CList> genericCLists, Throwable throwable) throws Exception {
                        if (iContact == null)
                            return;

                        if (throwable == null)
                            iContact.onContactSuccess(genericCLists);
                    }
                });
    }

    public void build(final IGenericCallback iGenericQuery) {
        if (!PermissionWrapper.hasContactsPermissions(mContext)) {
            throw new SecurityException("Contact Permission Missing");
        }


        new CommonCListExtracter(mContext).getList(mListFilterType, orderBy, limit, skip)
                .subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumerSingleObserver<>(new BiConsumer<List<GenericCList>, Throwable>() {
                    @Override
                    public void accept(List<GenericCList> cLists, Throwable throwable) throws Exception {
                        if (iGenericQuery == null)
                            return;

                        if (throwable == null) iGenericQuery.onContactSuccess(cLists);
                        else iGenericQuery.onContactError(throwable);
                    }
                }));
    }

    public CQuery filter(int type) {
        mListFilterType = type;
        return this;
    }

   /* public CQuery filter(List<Integer> type) {
        mListFilterType.addAll(type);
        return this;
    }*/

    public CQuery orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }


}
