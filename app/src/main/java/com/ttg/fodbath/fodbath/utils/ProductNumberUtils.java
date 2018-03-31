package com.ttg.fodbath.fodbath.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理产品编号 2017/5/23 0023.
 */

public class ProductNumberUtils {

    private static ProductNumberUtils instance;
    static ArrayList<String> list;

    public static ProductNumberUtils getInstance() {
        if (instance == null) {
            synchronized (ProductNumberUtils.class) {
                instance = new ProductNumberUtils();
                list = new ArrayList<>();
            }
        }
        return instance;
    }

    public List<String> getList() {
        return list;
    }

    public void add(String productNumber) {
        list.add(productNumber);
    }
    public void remove(String productNumber) {
        list.remove(productNumber);
    }
}
