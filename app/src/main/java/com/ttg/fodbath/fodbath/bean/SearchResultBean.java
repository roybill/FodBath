package com.ttg.fodbath.fodbath.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 产品搜索结果本地数据bean,需要序列化才能够传递对象 2017/5/3 0003.
 */

public class SearchResultBean implements Parcelable {

    public String askNumber;
    public String productNumber;
    public String productContent;
    public String expireDate;
    public int stockNumber;
    public int suggestPrice;
    public int reservedNumber;
    public int successCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.askNumber);
        dest.writeString(this.productNumber);
        dest.writeString(this.productContent);
        dest.writeString(this.expireDate);
        dest.writeInt(this.stockNumber);
        dest.writeInt(this.suggestPrice);
        dest.writeInt(this.reservedNumber);
        dest.writeInt(this.successCount);
    }

    public SearchResultBean() {
    }

    protected SearchResultBean(Parcel in) {
        this.askNumber = in.readString();
        this.productNumber = in.readString();
        this.productContent = in.readString();
        this.expireDate = in.readString();
        this.stockNumber = in.readInt();
        this.suggestPrice = in.readInt();
        this.reservedNumber = in.readInt();
        this.successCount = in.readInt();
    }

    public static final Creator<SearchResultBean> CREATOR = new Creator<SearchResultBean>() {
        @Override
        public SearchResultBean createFromParcel(Parcel source) {
            return new SearchResultBean(source);
        }

        @Override
        public SearchResultBean[] newArray(int size) {
            return new SearchResultBean[size];
        }
    };

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
