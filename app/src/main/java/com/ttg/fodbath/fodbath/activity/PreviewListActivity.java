package com.ttg.fodbath.fodbath.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.ttg.fodbath.fodbath.utils.CommitListUtils;
import com.ttg.fodbath.fodbath.utils.ListUtils;
import com.ttg.fodbath.fodbath.utils.ProductNumberUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 预览留货清单 2017/4/27 0027.
 */

public class PreviewListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PreviewListActivity";
    @Bind(R.id.preview_listview)
    ListView mListView;
    @Bind(R.id.btn_product)
    Button mBtnProduct;
    @Bind(R.id.btn_commit)
    Button mBtnCommit;
    @Bind(R.id.tv_data_empty)
    TextView mDataEmpty;

    private PopupWindow mPopupWindow;
    private List<SearchResultBean> mDatas;
    private PreviewListAdapter adapter;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_preview_list, null);
        mBackHome.setText(R.string.main_home);
        ButterKnife.bind(this, view);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {
        ListUtils instance = ListUtils.getInstance();
        if (instance != null) {
            mDatas = instance.getList();
        }
        if (mDatas.isEmpty()) {
            mListView.setVisibility(View.GONE);//集合数据为空时,隐藏listview
            mDataEmpty.setVisibility(View.VISIBLE);//集合数据为空时,显示提示内容
            mBtnCommit.setVisibility(View.GONE);//集合数据为空时,隐藏提交按钮
        } else {
            adapter = new PreviewListAdapter();
            mListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        //返回产品编号到产品搜索结果
        BaseApplication application = BaseApplication.getInstance();
        application.getMap().put("previewListDatas",mDatas);
    }

    @Override
    protected void initEvent() {
        mBackHome.setOnClickListener(this);
        mBtnProduct.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /** 返回主界面   */
            case R.id.tv_back_home:
                Intent backHome = new Intent(PreviewListActivity.this, MainActivity.class);
                startActivity(backHome);
                finish();
                break;
            /** 跳转到产品搜索界面   */
            case R.id.btn_product:
                Intent toSearch = new Intent(PreviewListActivity.this, SearchActivity.class);
                startActivity(toSearch);
                finish();
                break;
            /** 提交到已提交至留货清单界面  */
            case R.id.btn_commit:
                commitToList();
                break;
            default:
                break;
        }
    }

    /**
     * 预览清单listview适配器
     */
    private class PreviewListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDatas != null) {
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mDatas != null) {
                return mDatas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final SearchResultBean data = mDatas.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(PreviewListActivity.this, R.layout.preview_list_item, null);
                holder.tvProductNumber = (TextView) convertView.findViewById(R.id.tv_preview_product_name);
                holder.tvProductContent = (TextView) convertView.findViewById(R.id.tv_preview_product_content);
                holder.tvPreviewSuggestPrice = (TextView) convertView.findViewById(R.id.tv_preview_suggest_price);
                holder.tvPreviewStockNumber = (TextView) convertView.findViewById(R.id.tv_preview_stock_number);
                holder.tvPreviewReservedNumber = (TextView) convertView.findViewById(R.id.tv_preview_reserved_number);
                holder.ivMore = (ImageView) convertView.findViewById(R.id.iv_preview_more);
                holder.ivRemove = (ImageView) convertView.findViewById(R.id.iv_remove);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SearchResultBean bean = mDatas.get(position);
            holder.tvProductNumber.setText(bean.productNumber + "");
            holder.tvProductContent.setText(bean.productContent + "");
            holder.tvPreviewSuggestPrice.setText(bean.suggestPrice + "");
            holder.tvPreviewStockNumber.setText(bean.stockNumber + "");
            holder.tvPreviewReservedNumber.setText(bean.reservedNumber + "");
            holder.ivRemove.setTag(R.id.iv_remove, position);
            final ViewHolder finalHolder = holder;
            holder.tvPreviewReservedNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View reservedNumberWindow = LayoutInflater.from(PreviewListActivity.this).inflate(R.layout.window_reserved_number, null);
                    mPopupWindow = new PopupWindow(reservedNumberWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                            finalHolder.tvPreviewReservedNumber.setText(zero);
                            changeData(data, position, Integer.parseInt(zero));
                            mPopupWindow.dismiss();
                        }
                    });
                    tvOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /** 选择为1的"要求预留数量"    */
                            finalHolder.tvPreviewReservedNumber.setText(one);
                            changeData(data, position, Integer.parseInt(one));
                            mPopupWindow.dismiss();
                        }
                    });
                    tvTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /** 选择为2的"要求预留数量"    */
                            finalHolder.tvPreviewReservedNumber.setText(two);
                            changeData(data, position, Integer.parseInt(two));
                            mPopupWindow.dismiss();
                        }
                    });
                }
            });
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** 到预览留货清单详情界面 */
                    toPreviewListDetail(position);
                }
            });
            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreviewListActivity.this);
                    builder.setTitle(R.string.confirm_delete).
                            setCancelable(false).
                            setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDatas.remove(position);
                                    //同时移除单例保存的产品编号
                                    ProductNumberUtils instance = ProductNumberUtils.getInstance();
                                    List<String> list = instance.getList();
                                    list.remove(position);
                                    if (mDatas.isEmpty()) {
                                        mListView.setVisibility(View.GONE);
                                        mDataEmpty.setVisibility(View.VISIBLE);
                                        mBtnCommit.setVisibility(View.GONE);
                                    }
                                    notifyDataSetChanged();
                                    Toast.makeText(PreviewListActivity.this, R.string.successfully_deleted, Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton(R.string.cancel, null).show();
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
    }

    /**
     * 到预览留货清单详情界面
     */
    private void toPreviewListDetail(int position) {
        SearchResultBean previewListData = mDatas.get(position);
        BaseApplication application = BaseApplication.getInstance();
        application.getMap().put("previewListData", previewListData);
        Intent toProductDetail = new Intent(PreviewListActivity.this, PreviewListDetailActivity.class);
        startActivity(toProductDetail);
    }

    /**
     * 提交到已提交至留货清单界面
     */
    private void commitToList() {
        for (int i = 0; i < mDatas.size(); i++) {
            SearchResultBean bean = mDatas.get(i);
            int stockNumber = bean.stockNumber;
            if (stockNumber == 0) {
                commitFailed();//当列表数据中有库存数量为0的条目,存盘失败
                return;
            }
        }
        commitSuccess();//存盘成功
    }

    /**
     * 存盘失败
     */
    private void commitFailed() {
        String productNumber = null;
        for (int i = 0; i < mDatas.size(); i++) {
            SearchResultBean bean = mDatas.get(i);
            if (bean.stockNumber == 0) {
                productNumber = bean.productNumber;
            }
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("存盤失敗").
                setCancelable(false).
                setMessage("預留提交失敗!產品'" + productNumber + "' 之預留數量超過最大數量").
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 存盘成功
     */
    private void commitSuccess() {
        String expireDate = getExpireDate();//获取四天后的到期日
        Log.d(TAG,"1526expireDate="+expireDate);
        //用单例模式添加当前item对象到集合
        BaseApplication application = BaseApplication.getInstance();
        for (int i = 0; i < mDatas.size(); i++) {
            SearchResultBean bean = mDatas.get(i);
            bean.setExpireDate(expireDate);
        }
        application.getMap().put("alreadyCommitData", mDatas);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("儲存成功").setCancelable(true).
                setMessage("您的预留清单已经成功地提交.编号" + "E000048-01-" + ",您预留的产品将会于" + expireDate + "到期").
                setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent toMain = new Intent(PreviewListActivity.this, MainActivity.class);
                        startActivity(toMain);
                        finish();
                    }
                }).show();
    }

    /**
     * 获取四天后的日期
     */
    private String getExpireDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        calendar.add(Calendar.DATE, 4);
        String expireDate = sdf.format(calendar.getTime());
        return expireDate;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PreviewListActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.exit_the_request_list).
                setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent backSearchResult = new Intent(PreviewListActivity.this, ProductSearchResultActivity.class);
                        startActivity(backSearchResult);
                        finish();
                    }
                }).setNegativeButton(R.string.cancel, null);
        dialog.show();
    }

    private class ViewHolder {
        TextView tvProductNumber;
        TextView tvProductContent;
        ImageView ivRemove;
        ImageView ivMore;
        TextView tvPreviewSuggestPrice;
        TextView tvPreviewStockNumber;
        TextView tvPreviewReservedNumber;
    }
}
