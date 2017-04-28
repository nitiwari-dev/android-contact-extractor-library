package com.coderconsole.cextracter.cmodels.i;

import com.coderconsole.cextracter.cmodels.CAccount;
import com.coderconsole.cextracter.cmodels.CEmail;
import com.coderconsole.cextracter.cmodels.CEvents;
import com.coderconsole.cextracter.cmodels.CGroups;
import com.coderconsole.cextracter.cmodels.CName;
import com.coderconsole.cextracter.cmodels.COrganisation;
import com.coderconsole.cextracter.cmodels.CPhone;
import com.coderconsole.cextracter.cmodels.CPostBoxCity;

/**
 * Created by Nitesh on 19-04-2017.
 */

public interface IContactQuery {
    CName getName();

    CEmail getEmail();

    CPhone getPhone();

    CAccount getAccount();

    CPostBoxCity getPostCode();

    COrganisation getOrg();

    CEvents getEvents();

    CGroups getGroups();

    String getPhotoUri();

    interface Filter {
        int ONLY_NAME = 0;
        int ONLY_EMAIL = 1;
        int ONLY_PHONE = 2;
        int ONLY_ACCOUNT = 3;
        int ONLY_POSTCODE = 4;
        int ONLY_ORGANISATION = 5;
        int ONLY_EVENTS = 7;
        int ONLY_GROUPS = 8;
        int ONLY_PHOTO_URI = 9;
        int COMMON = 9;

    }
}
