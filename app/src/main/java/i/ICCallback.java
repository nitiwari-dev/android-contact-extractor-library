package i;

import java.util.List;

import cquery.CList;

/**
 * Interface to provide ContactQuery success/error response
 *
 * Created by Nitesh on 03-04-2017.
 */

public interface ICCallback {
    void onContactSuccess(List<CList> mList);
    void onContactError(Throwable throwable);
}
