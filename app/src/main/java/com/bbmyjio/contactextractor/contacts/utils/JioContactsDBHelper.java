/*
 *   Copyright (C) 2017 Nitesh Tiwari.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.bbmyjio.contactextractor.contacts.utils;

import android.content.ContentValues;
import android.content.Context;

import com.bbmyjio.contactextractor.contacts.model.db.JioContactModel;
import com.bbmyjio.contactextractor.contacts.provider.JioContactsProvider;

/**
 * Constant Helper for insert/delete/update
 * <p>
 * Created by Nitesh on 20-01-2017.
 */

public class JioContactsDBHelper extends JioContactConstants {

    private Context mContext;

    public JioContactsDBHelper(Context context) {
        this.mContext = context;
    }


    public void insertLogHelper(JioContactModel jioContactModel) {
        ContentValues values = new ContentValues();
        values.put(PHONE.HOME, jioContactModel.home_phone);
        values.put(PHONE.MOBILE, jioContactModel.mobile_phone);
        values.put(PHONE.WORK, jioContactModel.work_phone);

        values.put(EMAIL.HOME, jioContactModel.home_email);
        values.put(EMAIL.WORK, jioContactModel.work_email);

        values.put(NAME.DISPLAY_NAME, jioContactModel.display_name);
        values.put(NAME.FAMILY_NAME, jioContactModel.family_name);
        values.put(NAME.GIVEN_NAME, jioContactModel.given_name);

        values.put(POSTAL.POSTAL_CODE, jioContactModel.postal_code);
        values.put(POSTAL.CITY, jioContactModel.city);

        values.put(ORGANISATION.COMPANY, jioContactModel.company);
        values.put(ORGANISATION.DEPARTMENT, jioContactModel.department);

        values.put(EVENTS.ANNIVESARY, jioContactModel.annv_event);
        values.put(EVENTS.BIRTH, jioContactModel.birth_event);

        values.put(ACCOUNT.ACCOUNT_INFO, jioContactModel.account_info);

        values.put(COMMON.IDENTITY, jioContactModel.identity);
        values.put(COMMON.FAVORITES, jioContactModel.favorites);
        values.put(COMMON.RELATION, jioContactModel.relation);

        mContext.getContentResolver().insert(JioContactsProvider.JioContactsColumns.CONTENT_URI
                , values);

    }
}
