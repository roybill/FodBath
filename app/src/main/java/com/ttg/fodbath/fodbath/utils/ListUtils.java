package com.ttg.fodbath.fodbath.utils;

import com.ttg.fodbath.fodbath.bean.SearchResultBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 预览表单数据处理工具 2017/5/9 0009.
 */

public class ListUtils {

    private static ListUtils instance;
    static ArrayList<SearchResultBean> list;
    private ListUtils(){}
    public static ListUtils getInstance() {
        if (instance == null) {
            synchronized (ListUtils.class) {
                if (instance == null) {
                    instance = new ListUtils();
                    list = new ArrayList();
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
