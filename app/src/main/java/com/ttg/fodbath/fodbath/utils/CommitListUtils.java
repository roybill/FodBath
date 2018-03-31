package com.ttg.fodbath.fodbath.utils;

import com.ttg.fodbath.fodbath.bean.SearchResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 预览留货清单提交到已提交至留货清单 2017/5/15 0015.
 */

public class CommitListUtils {

    private static CommitListUtils instance;
    static ArrayList<SearchResultBean> list;
    static SearchResultBean bean;
    private CommitListUtils(){}
    public static CommitListUtils getInstance() {
        if (instance == null) {
            synchronized (CommitListUtils.class) {
                if (instance == null) {
                    instance = new CommitListUtils();
                    list = new ArrayList<>();
                    bean = new SearchResultBean();
                }
            }
        }
        return instance;
    }

    public List<SearchResultBean> getList() {
        return list;
    }

    public SearchResultBean getBean(){return bean;}

    public void add(SearchResultBean item) {
        list.add(item);
    }

    public void remove(SearchResultBean item) {
        list.remove(item);
    }

    public void clear() {
        list.clear();
    }

}
