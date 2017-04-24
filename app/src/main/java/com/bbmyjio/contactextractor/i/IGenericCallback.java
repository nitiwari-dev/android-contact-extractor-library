package com.bbmyjio.contactextractor.i;

import com.bbmyjio.contactextractor.cquery.CList;
import com.bbmyjio.contactextractor.cquery.GenericCList;

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
