package com.ttg.fodbath.fodbath.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 尺寸转换 2017/04/27 0007.
 */

public class DensityUtils {

    /** px转dp   */
    public static int px2dp(Context context,int px){
        //dp = px*160/dpi
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        return (int) (px*160f/dpi+0.5f);
    }

    /** dp转px   */
    public static int dp2px(Context context,int dp){
        //px = dp*(dpi/160)
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        return (int)(dp*(dpi/160)+0.5f);
    }

}
