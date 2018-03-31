package com.ttg.fodbath.fodbath.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 更改密码 2017/4/27 0027.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ChangePasswordActivity";

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_change_password, null);
        mBackHome.setText(R.string.main_home);
        mBackHome.setText(R.string.main_home);
        ButterKnife.bind(this, view);
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
        switch (view.getId()) {
            /** 返回主界面   */
            case R.id.tv_back_home:
                Intent backHome = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(backHome);
                finish();
                break;
            default:
                break;
        }
    }
}
