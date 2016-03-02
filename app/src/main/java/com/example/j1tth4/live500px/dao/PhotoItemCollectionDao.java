package com.example.j1tth4.live500px.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toy_o on 2/3/2559.
 */
public class PhotoItemCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("data") private List<PhotoItemDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
