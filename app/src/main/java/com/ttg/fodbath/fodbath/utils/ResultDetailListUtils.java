package com.ttg.fodbath.fodbath.utils;

import com.ttg.fodbath.fodbath.bean.SearchResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品搜索结果表单工具 2017/5/11 0011.
 */

public class ResultDetailListUtils {

    private static ResultDetailListUtils instance;
    static ArrayList<SearchResultBean> list;
    private ResultDetailListUtils(){}
    public static ResultDetailListUtils getInstance(){
        if(instance == null){
            synchronized (ResultDetailListUtils.class){
                if(instance == null){
                    instance = new ResultDetailListUtils();
                    list = new ArrayList<>();
                }
            }
        }
        return instance;
    }
    public List<SearchResultBean> getList(){
        return list;
    }

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
