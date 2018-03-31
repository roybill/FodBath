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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;
import com.ttg.fodbath.fodbath.base.BaseApplication;
import com.ttg.fodbath.fodbath.bean.SearchResultBean;
import com.ttg.fodbath.fodbath.utils.ListUtils;
import com.ttg.fodbath.fodbath.utils.ProductNumberUtils;
import com.ttg.fodbath.fodbath.utils.ResultDetailListUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 产品搜索结果 2017/4/28 0028.
 */

public class ProductSearchResultActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ProductSearchResultActivity";
    @Bind(R.id.lv_search_result)
    ListView mLvSearchResult;
    @Bind(R.id.btn_view)
    Button mBtnView;

    private List<SearchResultBean> mDatas;
    private SearchResultAdapter searchResultAdapter;

    //已经添加到"预览留货清单"的产品编号
    private List<SearchResultBean> previewListDatas;
    private ProductNumberUtils productNumberUtils;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_product_search_result, null);
        mBackHome.setText(R.string.main_home);
        ButterKnife.bind(this, view);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {
        ResultDetailListUtils instance = ResultDetailListUtils.getInstance();
        mDatas = instance.getList();
        if (mDatas.isEmpty()) {
            for (int i = 1; i <= 3; i++) {
                SearchResultBean bean = new SearchResultBean();
//            data.reservedNumber=;
                bean.productNumber = "3GI-20061299----BK--" + (i * 2);
                bean.productContent = "Rettangolo 20061.299 external parts for 20049 in black" + (i * 3);
                bean.stockNumber = i + -1;
                bean.suggestPrice = i * 5;
                mDatas.add(bean);
            }
        }
        searchResultAdapter = new SearchResultAdapter();
        mLvSearchResult.setAdapter(searchResultAdapter);
        searchResultAdapter.notifyDataSetChanged();
        productNumberUtils = ProductNumberUtils.getInstance();

        //获取已经添加到"预览留货清单"的产品编号
        BaseApplication application = BaseApplication.getInstance();
        previewListDatas = (List<SearchResultBean>) application.getMap().get("previewListDatas");
    }

    @Override
    protected void initEvent() {
        mBackHome.setOnClickListener(this);
        mBtnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /** 返回主界面   */
            case R.id.tv_back_home:
                Intent backHome = new Intent(ProductSearchResultActivity.this, MainActivity.class);
                startActivity(backHome);
                if (previewListDatas != null) {
                    previewListDatas.clear();
                }
                finish();
                break;
            /** 检视按钮  */
            case R.id.btn_view:
                Intent toView = new Intent(ProductSearchResultActivity.this, PreviewListActivity.class);
                startActivity(toView);
                break;
            default:
                break;
        }
    }

    /**
     * 搜索结果listview适配器
     */
    private class SearchResultAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final SearchResultBean data = mDatas.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ProductSearchResultActivity.this, R.layout.activity_search_result_item, null);
                holder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
                holder.ivMore = (ImageView) convertView.findViewById(R.id.iv_more);
                holder.tvProductNumber = (TextView) convertView.findViewById(R.id.tv_product_number);
                holder.tvProductContent = (TextView) convertView.findViewById(R.id.tv_product_content);
                holder.tvSuggestPrice = (TextView) convertView.findViewById(R.id.tv_suggest_price);
                holder.tvStockNumber = (TextView) convertView.findViewById(R.id.tv_stock_number);
                holder.tvReservedNumber = (TextView) convertView.findViewById(R.id.tv_reserved_number);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ivAdd.setTag(R.id.iv_add, position);
            holder.tvProductNumber.setText(data.productNumber + "");
            holder.tvProductContent.setText(data.productContent + "");
            holder.tvReservedNumber.setText(data.reservedNumber + "");
            holder.tvStockNumber.setText(data.stockNumber + "");
            holder.tvSuggestPrice.setText(data.suggestPrice + "");
            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data == null || data.reservedNumber == 0) {
                        Toast.makeText(ProductSearchResultActivity.this, R.string.please_choose_request_qty, Toast.LENGTH_SHORT).show();
                    } else {
                        //弹出添加成功dialog
                        showSuccessDialog(position);
                    }
                }
            });
            final ViewHolder finalHolder = holder;
            holder.tvReservedNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View reservedNumberWindow = LayoutInflater.from(ProductSearchResultActivity.this).inflate(R.layout.window_reserved_number, null);
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
                            /** 选择为0的"要求预留数量"    */
                            finalHolder.tvReservedNumber.setText(zero);
                            changeData(data, position, Integer.parseInt(zero));
                            mPopupWindow.dismiss();
                        }
                    });
                    tvOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /** 选择为1的"要求预留数量"    */
                            finalHolder.tvReservedNumber.setText(one);
                            changeData(data, position, Integer.parseInt(one));
                            mPopupWindow.dismiss();
                        }
                    });
                    tvTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /** 选择为2的"要求预留数量"    */
                            finalHolder.tvReservedNumber.setText(two);
                            changeData(data, position, Integer.parseInt(two));
                            mPopupWindow.dismiss();
                        }
                    });
                }
            });
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** 把当前item的数据传递到产品详情界面 */
                    toProductDetail(position);
                }
            });
            return convertView;
        }

        private void changeData(SearchResultBean data, int position, int change_count) {
            SearchResultBean new_data = new SearchResultBean();
            new_data.reservedNumber = change_count;
            new_data.stockNumber = data.stockNumber;
            new_data.suggestPrice = data.suggestPrice;
            new_data.productNumber = data.productNumber;
            new_data.productContent = data.productContent;
            mDatas.set(position, new_data);
            notifyDataSetChanged();
        }

        /**
         * 产品编号相同不添加,否则弹出添加成功dialog
         */
        private void showSuccessDialog(int position) {
            SearchResultBean currentData = mDatas.get(position);
            String productNumber = currentData.productNumber;
            if (previewListDatas != null) {     //获取已经添加到"预览留货清单"的产品编号和当前点击的产品编号对比
                Iterator<SearchResultBean> iterator = previewListDatas.iterator();
                while (iterator.hasNext()) {
                    SearchResultBean bean = iterator.next();
                    String previewListProductNumber = bean.productNumber;
                    if (productNumber == previewListProductNumber) {
                        Toast.makeText(ProductSearchResultActivity.this, R.string.has_been_added_to_reservation_request_list, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            // 对比当前点击的产品编号和已经保存的产品编号
            List<String> list = productNumberUtils.getList();
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String addedNumber = iterator.next();
                if (productNumber == addedNumber) {
                    Toast.makeText(ProductSearchResultActivity.this, R.string.has_been_added_to_reservation_request_list, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            list.add(productNumber); // 保存已经添加到"预览留货清单"的产品编号
            //用单例模式添加数据对象
            ListUtils instance = ListUtils.getInstance();
            instance.add(currentData);
            AlertDialog.Builder builder = new AlertDialog.Builder(ProductSearchResultActivity.this);
            builder.setTitle(R.string.success_add_to_goods_list).
                    setCancelable(false).
                    setPositiveButton(R.string.view_list, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toPreviewList = new Intent(ProductSearchResultActivity.this, PreviewListActivity.class);
                            startActivity(toPreviewList);
                        }
                    }).
                    setNegativeButton(R.string.keep_on, null).show();
        }
    }

    /**
     * 把当前item的数据传递到产品详情界面
     */
    private void toProductDetail(int position) {
        SearchResultBean currentDetailData = mDatas.get(position);
        BaseApplication application = BaseApplication.getInstance();
        application.getMap().put("currentDetailData", currentDetailData);
        Intent toProductDetail = new Intent(ProductSearchResultActivity.this, ProductDetailActivity.class);
        startActivity(toProductDetail);
    }

    public class ViewHolder {
        TextView tvProductName;
        TextView tvProductNumber;
        TextView tvProductContent;
        ImageView ivAdd;
        ImageView ivMore;
        TextView tvSuggestPrice;
        TextView tvStockNumber;
        TextView tvReservedNumber;
    }

    @Override
    public void onBackPressed() {
        Intent backSearch = new Intent(ProductSearchResultActivity.this, SearchActivity.class);
        startActivity(backSearch);
        finish();
    }
}
