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

package com.bbmyjio.contactextractor.common.permissions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * `
 * Permission Preference storage
 * <p/>
 * Created by nitesh on 13-07-2016.
 */
public class PermissionPrefs {

    private static final String IS_FIRST_DENY = "is_first_deny";
    private static final String ACCOUNT_OPENED_COUNT = "account_opened_count";
    private static final int DEFAULT_COUNT_DIALOG = 4;

    public static void setIsNeverAskAgain(Context context, String key, boolean isNeverAskAgain) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, isNeverAskAgain).apply();
    }

    public static boolean isNeverAskAgain(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static boolean getFirstDenyPermission(Context mContext) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sp.getBoolean(IS_FIRST_DENY, false);
    }

    public static void setFirstDenyPermission(Context mContext, boolean isDeny) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_FIRST_DENY, isDeny);
        editor.apply();
    }


    public static boolean isShowDialog(Context mContext) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        int count = sp.getInt(ACCOUNT_OPENED_COUNT, DEFAULT_COUNT_DIALOG);

        count = (count == 1) ? DEFAULT_COUNT_DIALOG : count;

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(ACCOUNT_OPENED_COUNT, --count);
        editor.apply();

        return count >= 3;
    }
}
