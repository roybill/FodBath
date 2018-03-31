package com.ttg.fodbath.fodbath.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 产品搜索结果每条item数据bean   2017/5/3 0003.
 */

public class ProductSearchResultItemBean {

    public TextView productNumber;
    public TextView productContent;
    public TextView suggestPrice;
    public TextView stockNumber;
    public TextView reservedNumber;
    public ImageView add;
    public ImageView more;

    public ProductSearchResultItemBean() {
    }

    public ProductSearchResultItemBean(TextView productNumber, TextView productContent, TextView suggestPrice, TextView stockNumber, TextView reservedNumber, ImageView add, ImageView more, TextView productName) {
        this.productNumber = productNumber;
        this.productContent = productContent;
        this.suggestPrice = suggestPrice;
        this.stockNumber = stockNumber;
        this.reservedNumber = reservedNumber;
        this.add = add;
        this.more = more;
        this.productName = productName;
    }

    public TextView getProductName() {
        return productName;
    }

    public TextView getProductNumber() {
        return productNumber;
    }

    public TextView getProductContent() {
        return productContent;
    }

    public TextView getSuggestPrice() {
        return suggestPrice;
    }

    public TextView getStockNumber() {
        return stockNumber;
    }

    public TextView getReservedNumber() {
        return reservedNumber;
    }

    public ImageView getAdd() {
        return add;
    }

    public ImageView getMore() {
        return more;
    }

    public TextView productName;

    public void setProductNumber(TextView productNumber) {
        this.productNumber = productNumber;
    }

    public void setProductName(TextView productName) {
        this.productName = productName;
    }

    public void setProductContent(TextView productContent) {
        this.productContent = productContent;
    }

    public void setSuggestPrice(TextView suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public void setStockNumber(TextView stockNumber) {
        this.stockNumber = stockNumber;
    }

    public void setReservedNumber(TextView reservedNumber) {
        this.reservedNumber = reservedNumber;
    }

    public void setAdd(ImageView add) {
        this.add = add;
    }

    public void setMore(ImageView more) {
        this.more = more;
    }

}
