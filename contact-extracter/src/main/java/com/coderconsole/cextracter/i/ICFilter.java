package com.coderconsole.cextracter.i;

/**
 * All the filters using to query different types of contacts
 * <p>
 * Created by Nitesh on 28-04-2017.
 */

public interface ICFilter {
    int ONLY_NAME = 0;
    int ONLY_EMAIL = 1;
    int ONLY_PHONE = 2;
    int ONLY_ACCOUNT = 3;
    int ONLY_POSTCODE = 4;
    int ONLY_ORGANISATION = 5;
    int ONLY_EVENTS = 7;
    int ONLY_GROUPS = 8;
    int ONLY_PHOTO_URI = 9;
}
