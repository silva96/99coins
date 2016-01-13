package com.silva.benjamin.ninetyninecoins.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject{
    @PrimaryKey
    private String _id;
    private String title;
    private String category;
    private String cover;
    private String higherPrice;
    private String lowerPrice;
    private String averagePrice;
    private String priceCount;

    public Item() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHigherPrice() {
        return higherPrice;
    }

    public void setHigherPrice(String higherPrice) {
        this.higherPrice = higherPrice;
    }

    public String getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(String lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(String priceCount) {
        this.priceCount = priceCount;
    }
}
