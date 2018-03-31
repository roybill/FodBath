package com.ttg.fodbath.fodbath.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 库存查询及留货请求 2017/5/11 0011.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_search)
    Button mSearch;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_data_query_and_request, null);
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
        mSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /** 返回主界面    */
            case R.id.tv_back_home:
                Intent backHome = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(backHome);
                finish();
                break;
            /** 产品搜索按钮  */
            case R.id.btn_search:
                Intent toResult = new Intent(SearchActivity.this, ProductSearchResultActivity.class);
                startActivity(toResult);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent toMain = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(toMain);
        finish();
    }
}
