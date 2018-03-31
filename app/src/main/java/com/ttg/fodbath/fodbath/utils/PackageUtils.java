package com.ttg.fodbath.fodbath.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 版本号信息 2017/04/27 0015.
 */
public class PackageUtils {

    /**
     * 获取版本名称
     */
    public static String getVersionName(Context context) {
        //获取包管理器
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            //获取清单文件对象
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知版本";
        }
    }

    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        //获取包管理器
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            //获取清单文件对象
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
