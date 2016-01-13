package com.silva.benjamin.ninetyninecoins.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class StarredItem extends RealmObject{
    @PrimaryKey
    private String _id;

    public StarredItem() {
    }

    public StarredItem(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
