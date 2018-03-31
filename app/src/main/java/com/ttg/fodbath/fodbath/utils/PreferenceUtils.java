package com.ttg.fodbath.fodbath.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences存储 2017/04/27
 */

public class PreferenceUtils {

    private static SharedPreferences mSp;

    private static SharedPreferences getSp(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences("settingInfo", Context.MODE_PRIVATE);
        }
        return mSp;
    }

    /**
     * 获得boolean数据,没有时为false,方法重载
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * boolean数值,方法重载
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 设置boolean值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获得String的数据,没有时为null
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * 获得String的数据,方法重载
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getString(key, defValue);
    }

    /**
     * 设置String值
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取int数据,重载
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, 1);
    }

    /**
     * 获取int数据,重载
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 设置int数据,重载
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
