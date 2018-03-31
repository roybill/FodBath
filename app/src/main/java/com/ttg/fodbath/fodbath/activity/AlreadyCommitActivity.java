package com.ttg.fodbath.fodbath.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ttg.fodbath.fodbath.R;
import com.ttg.fodbath.fodbath.base.BaseActivity;
import com.ttg.fodbath.fodbath.base.BaseApplication;
import com.ttg.fodbath.fodbath.bean.SearchResultBean;
import com.ttg.fodbath.fodbath.utils.CommitListUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已提交至留货清单 2017/4/28 0028.
 */

public class AlreadyCommitActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AlreadyCommitActivity";
    @Bind(R.id.already_commit_listview)
    ListView mListView;
    private List<SearchResultBean> mDatas;
    private AlreadyCommitAdapter adapter;

    @Override
    protected View initView() {
        View view = View.inflate(this, R.layout.activity_already_commit, null);
        mBackHome.setText(R.string.main_home);
        ButterKnife.bind(this, view);
        mContainer.addView(view);
        return view;
    }

    @Override
    protected void initData() {
        BaseApplication application = BaseApplication.getInstance();
        mDatas = (List<SearchResultBean>) application.getMap().get("alreadyCommitData");
        if (mDatas == null) {
            emptyData(); //数据为空
        } else {
            adapter = new AlreadyCommitAdapter();
            Collections.reverse(mDatas);//倒序展示数据,最后添加的数据展示在最前面
            mListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

//        CommitListUtils instance = CommitListUtils.getInstance();
//        if (instance != null) {
//            mDatas = instance.getList();
//            Log.d(TAG, "0943mDatasSize=" + mDatas.size());
//        }
//        adapter = new AlreadyCommitAdapter();
//        mListView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
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
                Intent backHome = new Intent(AlreadyCommitActivity.this, MainActivity.class);
                startActivity(backHome);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 已提交至留货清单listview适配器
     */
    private class AlreadyCommitAdapter extends BaseAdapter {

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(AlreadyCommitActivity.this, R.layout.already_commit_item, null);
                holder.tvProductNumber = (TextView) convertView.findViewById(R.id.tv_commit_product_number);
                holder.tvProductContent = (TextView) convertView.findViewById(R.id.tv_commit_product_content);
                holder.tvAskReservedNumber = (TextView) convertView.findViewById(R.id.tv_commit_reserved_number);
                holder.tvExpireDate = (TextView) convertView.findViewById(R.id.tv_expire_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SearchResultBean bean = mDatas.get(position);
            holder.tvProductNumber.setText(bean.productNumber + "");
            holder.tvProductContent.setText(bean.productContent + "");
            holder.tvAskReservedNumber.setText(bean.reservedNumber + "");
            holder.tvExpireDate.setText(bean.expireDate+"");
            return convertView;
        }
    }

    /**
     * 数据为空
     */
    private void emptyData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cannot_find_the_relevant_information).
                setCancelable(false).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent backMain = new Intent(AlreadyCommitActivity.this, MainActivity.class);
                        startActivity(backMain);
                        finish();
                    }
                }).show();
    }

    private class ViewHolder {
        TextView tvAskNumber;
        TextView tvExpireDate;
        TextView tvProductNumber;
        TextView tvProductContent;
        TextView tvAskReservedNumber;
    }
}
