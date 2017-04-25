package com.bbmyjio.contactextractor.i;

import android.net.Uri;

import com.bbmyjio.contactextractor.cmodels.CAccount;
import com.bbmyjio.contactextractor.cmodels.CEmail;
import com.bbmyjio.contactextractor.cmodels.CEvents;
import com.bbmyjio.contactextractor.cmodels.CGroups;
import com.bbmyjio.contactextractor.cmodels.CName;
import com.bbmyjio.contactextractor.cmodels.COrganisation;
import com.bbmyjio.contactextractor.cmodels.CPostBoxCity;
import com.bbmyjio.contactextractor.contacts.model.api.CPhone;

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
