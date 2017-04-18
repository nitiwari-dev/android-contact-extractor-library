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
