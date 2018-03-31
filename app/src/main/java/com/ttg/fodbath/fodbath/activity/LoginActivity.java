package com.ttg.fodbath.fodbath.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;
import com.ttg.fodbath.fodbath.base.BaseApplication;
import com.ttg.fodbath.fodbath.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录 2017/4/27 0027.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    @Bind(R.id.tv_forgot_password)
    TextView mForgotPassword;
    @Bind(R.id.btn_login)
    Button mLogin;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_login, null);
        ButterKnife.bind(this, view);
        mBackHome.setVisibility(View.GONE);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mForgotPassword.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /** 跳转至忘记密码    */
            case R.id.tv_forgot_password:
                Intent toForgotPassword = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(toForgotPassword);
                break;

            /** 登录  */
            case R.id.btn_login:
                login();
                break;

            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.window_loading, null);
        PopupWindow mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(false);//点击空白处不做任何操作
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);//popupwindow弹出的位置
        WindowManager.LayoutParams lpProvince = getWindow().getAttributes();// 弹出window时背景颜色变暗
        lpProvince.alpha = 0.7f;
        getWindow().setAttributes(lpProvince);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(toMain);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 退出整个应用程序
     */
    @Override
    public void onBackPressed() {
        BaseApplication.getInstance().exit();
    }
}
