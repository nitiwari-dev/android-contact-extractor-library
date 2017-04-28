package com.coderconsole.cextracter.cmodels.i;


import com.coderconsole.cextracter.cmodels.cquery.CList;

import java.util.List;

/**
 * Interface to provide ContactQuery success/error response
 *
 * Created by Nitesh on 03-04-2017.
 */

public interface ICCallback {
    void onContactSuccess(List<CList> mList);
    void onContactError(Throwable throwable);
}
