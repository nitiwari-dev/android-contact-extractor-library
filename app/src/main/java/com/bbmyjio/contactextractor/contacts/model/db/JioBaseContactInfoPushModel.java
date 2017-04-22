package com.bbmyjio.contactextractor.contacts.model.db;

import com.google.myjson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 21-01-2017.
 */

public class JioBaseContactInfoPushModel {

    @SerializedName("contactsInfo")
    List<ContactInfoModel> infoModels = new ArrayList<>();

    static class ContactInfoModel {




    }
}
