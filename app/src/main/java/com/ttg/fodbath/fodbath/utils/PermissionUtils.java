package com.ttg.fodbath.fodbath.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 权限工具 2017/04/27.
 */
public class PermissionUtils {
    public static final int REQUEST_READANDWRITE = 1;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_LOCATION = 3;

    private static String[] PERMISSIONS_READANDWRITE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    private static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA};

    public static boolean verifyReadAndWritePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED
                || permission2 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_READANDWRITE,
                    REQUEST_READANDWRITE);
            return false;
        }else {
            return true;
        }
    }

    public static boolean verifyCameraPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_CAMERA,
                    REQUEST_CAMERA);
            return false;
        }else {
            return true;
        }
    }

    public static boolean verifyLocationPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);//自定义的code
            return false;
        }else {
            return true;
        }
    }

}
