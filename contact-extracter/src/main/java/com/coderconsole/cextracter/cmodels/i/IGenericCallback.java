package com.coderconsole.cextracter.cmodels.i;

import com.coderconsole.cextracter.cmodels.cquery.GenericCList;

import java.util.List;

/**
 * Interface to provide ContactQuery success/error response
 *
 * Created by Nitesh on 03-04-2017.
 */

public interface IGenericCallback {
    void onContactSuccess(List<GenericCList> mList);
    void onContactError(Throwable throwable);
}
