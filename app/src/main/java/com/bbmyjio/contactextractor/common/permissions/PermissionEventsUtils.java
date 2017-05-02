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
import android.text.TextUtils;


/**
 * Created by Kanchan on 13-07-2016.
 */
public class PermissionEventsUtils {

    public static void handleContinueClicked(Context context, int requestCode, String[] permission) {
        switch (requestCode) {
            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_REGISTRATION:
                break;

            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_REGISTRATION_PROFILE:


                break;

            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_ACCOUNT_SCREENS:

                break;

            case RunTimePermissionWrapper.REQUEST_CODE.LOCATION:

                break;

        }
    }

    public static void handleNotNowClicked(int requestCode, String[] permission) {
        switch (requestCode) {

            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_ACCOUNT_SCREENS:
                break;

            case RunTimePermissionWrapper.REQUEST_CODE.LOCATION:
                break;

        }
    }

    public static void handleCancelClicked(int requestCode, String[] permission) {
        switch (requestCode) {

            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_ACCOUNT_SCREENS:
                break;

            case RunTimePermissionWrapper.REQUEST_CODE.LOCATION:
                break;

        }
    }

    public static void handleSnackBarClick(int requestCode, String[] permission) {

        String eventName = getScreenEvent(requestCode);

        if (TextUtils.isEmpty(eventName))
            return;
    }

    public static String getScreenEvent(int requestCode) {
        String screenName = getScreenName(requestCode);

        if (!TextUtils.isEmpty(screenName)) {
        }

        return null;
    }

    private static String getScreenName(int requestCode) {

        switch (requestCode) {
            case RunTimePermissionWrapper.REQUEST_CODE.LOCATION:
                return SCREEN_NAMES.LOCATION;
            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_ACCOUNT_SCREENS:
                return SCREEN_NAMES.MY_ACCOUNT;
        }

        return null;
    }

    public interface SCREEN_NAMES {
        String LOCATION = "Location", MY_ACCOUNT = "My Account";
    }
}
