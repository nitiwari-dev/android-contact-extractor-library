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

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Runtime Permission Wrapper to handle the runtime permissions.
 * <p/>
 * Created by nitesh on 13-06-2016.
 */
public class RunTimePermissionWrapper extends PermissionWrapper {


    public static void handleRunTimePermission(Activity context, int multiplePermissionRequestCode, String... multiplePermissions) {
        handleRunTimePermission(context, multiplePermissionRequestCode, false, multiplePermissions);
    }


    /**
     * Handle the Runtime Permission
     *
     * @param activity
     * @param multiplePermissionRequestCode Request Code for Permission
     * @param isShowBBDialogForPermission   flag to determine to show CustomBBDialog for more information
     * @param multiplePermissions           varArgs of multiple Permissions
     */
    public static void handleRunTimePermission(Activity activity, int multiplePermissionRequestCode, boolean isShowBBDialogForPermission, String... multiplePermissions) {

        List<String> neededPermissionList = getDeniedPermissionList(activity, multiplePermissions);

        if (neededPermissionList == null) {
            return;
        }

        int permissionSize = neededPermissionList.size();
        if (permissionSize > 0) {

            if (isShowBBDialogForPermission) {
                //For only one permission ask for notification again
                if (permissionSize == 1) {

                    if (!PermissionPrefs.getFirstDenyPermission(activity)) {
                        ActivityCompat.requestPermissions(activity, neededPermissionList.toArray(new String[permissionSize]), multiplePermissionRequestCode);
                        PermissionPrefs.setFirstDenyPermission(activity, true);
                        return;
                    }
                }

                //DONOT show NOT NOW Button for multiple permissions on walkthrough.
//                showDialogForPermission(activity, multiplePermissionRequestCode, (multiplePermissionRequestCode != REQUEST_CODE.MULTIPLE_WALKTHROUGH), multiplePermissions);
            } else {
                ActivityCompat.requestPermissions(activity, neededPermissionList.toArray(new String[permissionSize]), multiplePermissionRequestCode);
            }
        }

    }


    public static List<String> getDeniedPermissionList(Activity context, String[] multiplePermissions) {

        if (context == null || multiplePermissions == null)
            return null;

        List<String> neededPermissionList = new ArrayList<>();

        for (String permission : multiplePermissions) {
            if (!hasPermissions(context, permission)) {
                neededPermissionList.add(permission);
            }
        }
        return neededPermissionList;
    }

    public static String[] getDeniedPermissionArray(Activity context, String[] multiplePermissions) {
        List<String> list = getDeniedPermissionList(context, multiplePermissions);
        if (list != null && list.size() > 0) {
            return list.toArray(new String[list.size()]);
        }
        return null;
    }

    public static boolean isAllPermissionGranted(Activity activity, String[] multiplePermissions) {
        List<String> list = getDeniedPermissionList(activity, multiplePermissions);
        return list != null && list.size() == 0;
    }


    public interface REQUEST_CODE {
        int SMS_DEALS_VERIFY_OTP = 99, SMS_DEALS_SEND_OTP = 100, CONTACTS = 101, READ_CALL_LOGS = 102, LOCATION = 103, STORAGE = 104, CAMERA = 105, LOCATION_MAP = 106,
                MULTIPLE_WALKTHROUGH = 200, MULTIPLE_REGISTRATION = 201, MULTIPLE_ACCOUNT_SCREENS = 202,
                LAUNCH_APP_INFO_SCREEN = 300, MULTIPLE_REGISTRATION_PROFILE = 203;
    }


    public interface PERMISSION_LIST {
        String WALK_THROUGH[] = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        String REGISTRATION[] = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS};

        String MY_ACCOUNT[] = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS};

        String LOCATION[] = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};

        String DEALS_PERMISSION_SMS[] = new String[]{Manifest.permission.READ_SMS};
    }

    public interface PERMISSION_TYPE {
        String SMS = "SMS", STORAGE = "Storage", TELEPHONE = "Telephone", CAMERA = "Camera", CONTACTS = "Contacts", LOCATION = "Your location";
    }
}
