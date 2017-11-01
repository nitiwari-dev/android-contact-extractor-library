
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

package com.coderconsole.cextracter.cquery;

import android.content.Context;

import com.coderconsole.cextracter.common.permissions.PermissionWrapper;
import com.coderconsole.cextracter.cquery.base.CList;
import com.coderconsole.cextracter.cquery.base.CListExtractorAbstract;
import com.coderconsole.cextracter.i.IContact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

public class CQuery {

    private static final String TAG = CQuery.class.getSimpleName();

    private static CQuery cQuery;

    private final Context mContext;

    private String limit;

    private String skip;

    private String orderBy;

    private int mListFilterType;

    private List<Disposable> mDisposable = new ArrayList<>();

    private CQuery(Context context) {
        this.mContext = context;
    }

    public static CQuery getInstance(Context context) {
        if (cQuery == null) {
            cQuery = new CQuery(context);
        }

        return cQuery;
    }

    public CQuery limit(String limit) {
        this.limit = limit;
        return this;
    }

    public CQuery skip(String skip) {
        this.skip = skip;
        return this;
    }

    public CQuery orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Disposable build(final IContact iContact) {

        if (!PermissionWrapper.hasContactsPermissions(mContext)) {
            throw new SecurityException("Contact Permission Missing");
        }

        return new CListExtractorAbstract(mContext).getList(mListFilterType, orderBy, limit, skip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<CList>, Throwable>() {
                    @Override
                    public void accept(List<CList> genericCLists, Throwable throwable) throws Exception {
                        if (iContact == null)
                            return;

                        if (throwable == null)
                            iContact.onContactSuccess(genericCLists);
                        else
                            iContact.onContactError(throwable);


                    }
                });
    }

    public CQuery filter(int type) {
        mListFilterType = type;
        return this;
    }

   /* public CQuery filter(List<Integer> type) {
        mListFilterType.addAll(type);
        return this;
    }*/


}
