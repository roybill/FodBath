package com.ttg.fodbath.fodbath.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;
import com.ttg.fodbath.fodbath.base.BaseApplication;
import com.ttg.fodbath.fodbath.bean.SearchResultBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 预览留货清单详情 2017/5/9 0009.
 */

public class PreviewListDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_detail_suggest_price)
    TextView mSuggestPrice;
    @Bind(R.id.tv_detail_stock_number)
    TextView mStockNumber;
    @Bind(R.id.tv_preview_detail_reserved_number)
    TextView mReservedNumber;
    @Bind(R.id.btn_preview_detail_format)
    Button mToProductFormat;

    private SearchResultBean mDatas;
    private List<SearchResultBean> list = new ArrayList<>();

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_preview_list_detail, null);
        mBackHome.setText(R.string.main_home);
        ButterKnife.bind(this, view);
        mContainer.addView(view);
        return null;
    }

    @Override
    protected void initData() {
        BaseApplication application = BaseApplication.getInstance();
        mDatas = (SearchResultBean) application.getMap().get("previewListData");
        if (mDatas != null) {
            int suggestPrice = mDatas.suggestPrice;
            int stockNumber = mDatas.stockNumber;
            int reservedNumber = mDatas.reservedNumber;
            mSuggestPrice.setText(suggestPrice + "");
            mStockNumber.setText(stockNumber + "");
            mReservedNumber.setText(reservedNumber + "");
        }
    }

    @Override
    protected void initEvent() {
        mBackHome.setOnClickListener(this);
        mReservedNumber.setOnClickListener(this);
        mToProductFormat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /** 返回主页    */
            case R.id.tv_back_home:
                Intent toMain = new Intent(PreviewListDetailActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
                break;
            /** 要求预留数量  */
            case R.id.tv_preview_detail_reserved_number:
                showSelectWindow();
                break;
            /** 到产品规格界面 */
            case R.id.btn_preview_detail_format:
                Intent toProductFormat = new Intent(PreviewListDetailActivity.this, ProductFormatActivity.class);
                startActivity(toProductFormat);
                break;
            default:
                break;
        }
    }

    /**
     * 要求预留数量
     */
    private void showSelectWindow() {
        View reservedNumberWindow = LayoutInflater.from(PreviewListDetailActivity.this).inflate(R.layout.window_reserved_number, null);
        final PopupWindow mPopupWindow = new PopupWindow(reservedNumberWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);//点击空白处popupwindow消失
        mPopupWindow.showAtLocation(reservedNumberWindow, Gravity.CENTER, 0, 0);//popupwindow弹出的位置
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
        TextView tvZero = (TextView) reservedNumberWindow.findViewById(R.id.tv_reserved_zero);
        TextView tvOne = (TextView) reservedNumberWindow.findViewById(R.id.tv_reserved_one);
        TextView tvTwo = (TextView) reservedNumberWindow.findViewById(R.id.tv_reserved_two);
        final String zero = tvZero.getText().toString();
        final String one = tvOne.getText().toString();
        final String two = tvTwo.getText().toString();
        tvZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReservedNumber.setText(zero);
                //和预览留货清单的值对应,当"要求预留数量"发生改变后,该集合内的数据相应改变
                mDatas.reservedNumber = Integer.parseInt(mReservedNumber.getText().toString());
                list.add(mDatas);
                mPopupWindow.dismiss();
            }
        });
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReservedNumber.setText(one);
                mDatas.reservedNumber = Integer.parseInt(mReservedNumber.getText().toString());
                list.add(mDatas);
                mPopupWindow.dismiss();
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReservedNumber.setText(two);
                mDatas.reservedNumber = Integer.parseInt(mReservedNumber.getText().toString());
                list.add(mDatas);
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent backPreviewList = new Intent(PreviewListDetailActivity.this, PreviewListActivity.class);
        startActivity(backPreviewList);
        finish();
    }
}
