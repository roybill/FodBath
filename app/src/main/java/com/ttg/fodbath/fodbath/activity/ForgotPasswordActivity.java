package com.ttg.fodbath.fodbath.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 忘记密码 2017/4/27 0027.
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_forgot_password, null);
        mBackHome.setText("Login");
        ButterKnife.bind(this,view);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mBackHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /** 返回登录界面  */
            case R.id.tv_back_home:
                Intent backLogin = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(backLogin);
                finish();
                break;

            default:
                break;
        }
    }
}
