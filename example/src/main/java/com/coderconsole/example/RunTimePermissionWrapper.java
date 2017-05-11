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

package com.coderconsole.example;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.coderconsole.cextracter.common.permissions.PermissionWrapper;

import java.util.ArrayList;
import java.util.List;

public class RunTimePermissionWrapper extends PermissionWrapper {


    /**
     * Handle the Runtime Permission
     *
     * @param activity
     * @param multiplePermissionRequestCode Request Code for Permission
     * @param multiplePermissions           varArgs of multiple Permissions
     */
    public static void handleRunTimePermission(Activity activity, int multiplePermissionRequestCode, String... multiplePermissions) {

        List<String> neededPermissionList = getDeniedPermissionList(activity, multiplePermissions);

        if (neededPermissionList == null) {
            return;
        }

        int permissionSize = neededPermissionList.size();
        if (permissionSize > 0) {
            ActivityCompat.requestPermissions(activity, neededPermissionList.toArray(new String[permissionSize]), multiplePermissionRequestCode);
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

    public static boolean isAllPermissionGranted(Activity activity, String[] multiplePermissions) {
        List<String> list = getDeniedPermissionList(activity, multiplePermissions);
        return list != null && list.size() == 0;
    }


    public interface REQUEST_CODE {
        int MULTIPLE_WALKTHROUGH = 200;
    }
}
