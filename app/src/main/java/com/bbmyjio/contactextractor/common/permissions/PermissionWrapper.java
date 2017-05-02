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
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by nitesh on 06-05-2016.
 */
public class PermissionWrapper {

    public static boolean hasPermissions(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasTelephonyPermissions(Context context) {
        return hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
    }

    public static boolean hasLocationPermissions(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return hasPermissions(context, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean hasSmsReadPermissions(Context context) {
        return hasPermissions(context, Manifest.permission.READ_SMS);
    }

    public static boolean hasSmsSendPermissions(Context context) {
        return hasPermissions(context, Manifest.permission.SEND_SMS);
    }

    public static boolean hasCallLogsPermissions(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN || hasPermissions(context, Manifest.permission.READ_CALL_LOG);
    }

    public static boolean hasContactsPermissions(Context context) {
        return hasPermissions(context, Manifest.permission.READ_CONTACTS);
    }

    public static boolean hasStorageReadPermission(Context context) {
        return hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static boolean hasGetAccountPermission(Context context) {
        return hasPermissions(context, Manifest.permission.GET_ACCOUNTS);
    }

    public static boolean hasCameraPermission(Context context) {
        return hasPermissions(context, Manifest.permission.CAMERA);
    }

    public static boolean hasReadPhoneStatePermissions(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
    }
}
