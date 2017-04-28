package com.coderconsole.cextracter.common.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Permission Wrapper to check permissions is enabled or not
 *
 * Created by nitesh on 06-05-2016.
 */
public class PermissionWrapper {

    public static boolean hasPermissions(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasContactsPermissions(Context context) {
        return hasPermissions(context, Manifest.permission.READ_CONTACTS);
    }
}
