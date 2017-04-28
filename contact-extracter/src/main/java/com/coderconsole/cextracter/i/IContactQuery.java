package com.coderconsole.cextracter.i;

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

}
