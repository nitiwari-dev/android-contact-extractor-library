package com.coderconsole.cextracter.i;

import com.coderconsole.cextracter.cquery.common.CommonCList;

import java.util.List;

/**
 * Interface to provide ContactQuery success/error response
 *
 * Created by Nitesh on 03-04-2017.
 */

public interface ICommonContact {
    void onContactSuccess(List<CommonCList> mList);
    void onContactError(Throwable throwable);
}
