package com.ttg.fodbath.fodbath.utils;

/**
 * 防止快速点击 2017/5/12 0012.
 */

public class FastClickUtils {

    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
