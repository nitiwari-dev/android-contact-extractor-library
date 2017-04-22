package com.bbmyjio.contactextractor.contacts.utils;

/**
 * JioContactConstants
 *
 * Created by Nitesh on 20-01-2017.
 */

public abstract class JioContactConstants {

    protected interface COMMON {
        String IDENTITY = "identity", FAVORITES = "favorites", RELATION = "relation", ID = "id";
    }

    protected interface PHONE {
        String HOME = "home_phone", WORK = "work_phone", MOBILE = "mobile_phone";
        String OTHER = "other_phone";
    }

    protected interface NAME {
        String DISPLAY_NAME = "display_name", FAMILY_NAME = "family_name", GIVEN_NAME = "given_name";
    }

    protected interface EMAIL {
        String WORK = "work_email", HOME = "home_email";
        String OTHER = "other_email";
    }

    protected interface POSTAL {
        String POSTAL_CODE = "postal_code", CITY = "city";
    }

    protected interface ORGANISATION {
        String COMPANY = "company", DEPARTMENT = "department";
    }

    protected interface EVENTS {
        String BIRTH = "birth_event", ANNIVESARY = "annv_event";
    }

    protected interface ACCOUNT {
        String ACCOUNT_ID = "acc_id", ACCOUNT_TYPE = "acc_type", ACCOUNT_INFO = "acc_info";
    }
}
