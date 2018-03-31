package com.ttg.fodbath.fodbath.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.ttg.fodbath.fodbath.R;

import java.util.Locale;

/**
 * Activity基类 2017/4/27 0027.
 */

public abstract class BaseActivity extends Activity {

    protected FrameLayout mContainer;
    protected TextView mBackHome;
    protected TextView mLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        BaseApplication.getInstance().addActivity(this);//记录当前应用的Activity,用于退出整个应用
        setContentView(R.layout.activity_base);
        mContainer = (FrameLayout) findViewById(R.id.content_container);
        mBackHome = (TextView) findViewById(R.id.tv_back_home);
        mLanguage = (TextView) findViewById(R.id.tv_language);
        initView();
        initData();
        initEvent();
    }

    protected abstract View initView();

    protected abstract void initData();

    protected abstract void initEvent();
}
