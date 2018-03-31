package com.ttg.fodbath.fodbath.activity;

import android.content.Intent;
import android.view.View;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;
import butterknife.ButterKnife;

/**
 * 产品规格 2017/4/28 0028.
 */

public class ProductFormatActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_product_format, null);
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
                Intent backHome = new Intent(ProductFormatActivity.this, MainActivity.class);
                startActivity(backHome);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        Intent backPreviewListDetail = new Intent(ProductFormatActivity.this,PreviewListDetailActivity.class);
//        startActivity(backPreviewListDetail);
        finish();
    }
}
