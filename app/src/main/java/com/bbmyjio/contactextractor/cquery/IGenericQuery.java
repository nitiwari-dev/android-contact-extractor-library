package com.bbmyjio.contactextractor.cquery;

import com.bbmyjio.contactextractor.contacts.model.api.CPhone;

/**
 * Created by Nitesh on 24-04-2017.
 */

public interface IGenericQuery {
    CPhone getPhone();
    String getName();
    String getPhotoUri();
}
