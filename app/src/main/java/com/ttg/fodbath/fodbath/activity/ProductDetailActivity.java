package com.ttg.fodbath.fodbath.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
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
import com.ttg.fodbath.fodbath.bean.SearchResultBean;
import com.ttg.fodbath.fodbath.utils.ListUtils;
import com.ttg.fodbath.fodbath.utils.ProductNumberUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 产品详情 2017/4/28 0028.
 */

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ProductDetailActivity";
    @Bind(R.id.btn_detail_view)
    Button mBtnDetailView;
    @Bind(R.id.btn_detail_format)
    Button mBtnDetailFormat;
    @Bind(R.id.btn_detail_add)
    Button mBtnAdd;
    @Bind(R.id.tv_detail_suggest_price)
    TextView mSuggestPrice;
    @Bind(R.id.tv_detail_stock_number)
    TextView mStockNumber;
    @Bind(R.id.tv_detail_reserved_number)
    TextView mReservedNumber;

    private SearchResultBean mDatas;
    private List<SearchResultBean> list;
    private List<String> mAddedNumber;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_product_detail, null);
        mBackHome.setText(R.string.main_home);
        ButterKnife.bind(this, view);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        BaseApplication application = BaseApplication.getInstance();
        mDatas = (SearchResultBean) application.getMap().get("currentDetailData");
        if (mDatas != null) {
            int suggestPrice = mDatas.suggestPrice;
            int stockNumber = mDatas.stockNumber;
            int reservedNumber = mDatas.reservedNumber;
            mSuggestPrice.setText(suggestPrice + "");
            mStockNumber.setText(stockNumber + "");
            mReservedNumber.setText(reservedNumber + "");
        }
        //获取已经添加到"预览留货清单"的产品编号
        ProductNumberUtils instance = ProductNumberUtils.getInstance();
        mAddedNumber = instance.getList();
    }

    @Override
    protected void initEvent() {
        mBackHome.setOnClickListener(this);
        mBtnDetailView.setOnClickListener(this);
        mBtnDetailFormat.setOnClickListener(this);
        mReservedNumber.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**  到主页面   */
            case R.id.tv_back_home:
                Intent backHome = new Intent(ProductDetailActivity.this, MainActivity.class);
                startActivity(backHome);
                finish();
                break;
            /** 要求预留数量  */
            case R.id.tv_detail_reserved_number:
                showSelectWindow();
                break;
            /** 到预览留货清单 */
            case R.id.btn_detail_view:
                Intent toDetailView = new Intent(ProductDetailActivity.this, PreviewListActivity.class);
                startActivity(toDetailView);
                break;
            /** 产品规格    */
            case R.id.btn_detail_format:
                Intent toProductFormat = new Intent(ProductDetailActivity.this, ProductFormatActivity.class);
                startActivity(toProductFormat);
                break;
            /** 新增到预览留货清单  */
            case R.id.btn_detail_add:
                addToPreviewList();
            default:
                break;
        }
    }

    /**
     * 要求预留数量
     */
    private void showSelectWindow() {
        View reservedNumberWindow = LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.window_reserved_number, null);
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
                //和产品搜索结果的值对应,当"要求预留数量"发生改变后,该集合内的数据相应改变
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

    /**
     * 新增到预览留货清单
     */
    private void addToPreviewList() {
        String productNumber = mDatas.productNumber;
        //对比产品搜索结果的产品编号和已经保存的产品编号
        if (mAddedNumber != null) {
            Iterator<String> iterator = mAddedNumber.iterator();
            while (iterator.hasNext()) {
                String addedNumber = iterator.next();
                if (productNumber == addedNumber) {
                    Toast.makeText(ProductDetailActivity.this, R.string.has_been_added_to_reservation_request_list, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        if (mDatas.reservedNumber == 0) {
            Toast.makeText(ProductDetailActivity.this, R.string.please_choose_request_qty, Toast.LENGTH_SHORT).show();
            return;
        } else {
            ListUtils instance = ListUtils.getInstance();
            instance.add(mDatas);
            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
            builder.setTitle(R.string.success_add_to_goods_list).
                    setCancelable(false).
                    setPositiveButton(R.string.view_list, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toPreviewList = new Intent(ProductDetailActivity.this, PreviewListActivity.class);
                            startActivity(toPreviewList);
                        }
                    }).
                    setNegativeButton(R.string.keep_on, null).show();
        }
        mAddedNumber.add(productNumber);
    }

    @Override
    public void onBackPressed() {
        Intent backSearchResult = new Intent(ProductDetailActivity.this, ProductSearchResultActivity.class);
        startActivity(backSearchResult);
        finish();
    }
}
