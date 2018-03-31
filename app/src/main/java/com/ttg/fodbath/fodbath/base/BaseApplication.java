package com.ttg.fodbath.fodbath.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ttg.fodbath.fodbath.activity.MainActivity;
import com.ttg.fodbath.fodbath.utils.PreferenceUtils;
import com.ttg.fodbath.fodbath.utils.UIUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 应用入口,提供全局资源数据传递 2017/4/27 0027.
 */

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";
    private List<Activity> mList = new LinkedList<>();//记录当前应用的Activity,用于退出整个应用
    public HashMap<String, Object> map = new HashMap<>();//全局数据传递
    public HashMap<String, Object> getMap() {
        return map;
    }
//    private BaseApplication(){} //构造私有化
    private static BaseApplication instance;//单例
    public static BaseApplication getInstance() {
//        if (instance == null) {
//            synchronized (BaseApplication.class) {
//                if (instance == null) {
//                    instance = new BaseApplication();
//                }
//            }
//        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.init(this);//初始化UI工具
        this.instance = this;
        initLanguage();//初始化语言
    }

    /** 初始化语言,获取最后一次保存的语言并且设置,确保每次打开应用都是最后一次设置的语言环境 */
    private void initLanguage() {
        String savedLanguage = PreferenceUtils.getString(getBaseContext(), "savedLanguage");
        if (savedLanguage != null) {
            if (savedLanguage.equals("en")) {
                setLanguage(Locale.ENGLISH);
            } else if (savedLanguage.equals("zh")) {
               setLanguage(Locale.TRADITIONAL_CHINESE);
            }
        }
    }

    /**
     * 设置语言
     */
    private void setLanguage(Locale language) {
        Resources resources = getBaseContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = language;
        resources.updateConfiguration(config, dm);
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
