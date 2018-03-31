package com.ttg.fodbath.fodbath.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;
import com.ttg.fodbath.fodbath.utils.FastClickUtils;
import com.ttg.fodbath.fodbath.utils.PreferenceUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面  2017/04/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    @Bind(R.id.ll_preview_list)
    LinearLayout mPreviewList;
    @Bind(R.id.ll_data_query_and_request)
    LinearLayout mDataQueryAndRequest;
    @Bind(R.id.ll_change_password)
    LinearLayout mChangePassword;
    @Bind(R.id.ll_sign_out)
    LinearLayout mSignOut;

    private String currentLanguage;

    @Override
    protected View initView() {
        currentLanguage = getLocaleLanguage();
        View view = View.inflate(this, R.layout.activity_main, null);
        ButterKnife.bind(this, view);
        mLanguage.setText(R.string.main_language);
        mLanguage.setTextColor(Color.parseColor("#C3AD58"));
        mBackHome.setText(R.string.main_home);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mPreviewList.setOnClickListener(this);
        mDataQueryAndRequest.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
        mSignOut.setOnClickListener(this);
        mLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /** 预览留货清单  */
            case R.id.ll_preview_list:
                Intent toPreviewList = new Intent(MainActivity.this, AlreadyCommitActivity.class);
                startActivity(toPreviewList);
                break;
            /** 库存查询及留货请求  */
            case R.id.ll_data_query_and_request:
                Intent toDataQueryAndRequest = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(toDataQueryAndRequest);
                break;
            /** 更改密码  */
            case R.id.ll_change_password:
                Intent toChangePassword = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(toChangePassword);
                break;
            /** 登出  */
            case R.id.ll_sign_out:
                Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(toLogin);
                finish();
                break;
            /** 语言切换   */
            case R.id.tv_language:
                switchLanguage();
                break;
            default:
                break;
        }
    }

    /**
     * 语言切换
     */
    private void switchLanguage() {
        if(FastClickUtils.isFastDoubleClick()){
            return ;//避免快速点击频繁切换语言
        }else{
            if (currentLanguage.equals("en")) {
                setLanguage(Locale.TRADITIONAL_CHINESE);  //当前为英文环境就切换为繁体中文
                PreferenceUtils.putString(MainActivity.this, "savedLanguage", getLocaleLanguage());//保存当前语言环境
            } else if (currentLanguage.equals("zh")) {
                setLanguage(Locale.ENGLISH);      //当前为繁体中文环境就切换为英文
                PreferenceUtils.putString(MainActivity.this, "savedLanguage", getLocaleLanguage());//保存当前语言环境
            }
        }
    }

    /**
     * 获取当前语言环境
     */
    private String getLocaleLanguage() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
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
        //刷新界面
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainActivity.this.startActivity(intent);
    }

    /**
     * 按返回键提示是否登出
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.confirm_sign_out).
                setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent backLogin = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(backLogin);
                        finish();
                    }
                }).setNegativeButton(R.string.cancel, null);
        dialog.show();
    }
}
