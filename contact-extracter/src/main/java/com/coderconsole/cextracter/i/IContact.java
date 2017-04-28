package com.coderconsole.cextracter.i;


import com.coderconsole.cextracter.cquery.base.CList;

import java.util.List;

/**
 * Interface to provide ContactQuery success/error response
 *
 * Created by Nitesh on 03-04-2017.
 */

public interface IContact {
    void onContactSuccess(List<CList> mList);
    void onContactError(Throwable throwable);
}
