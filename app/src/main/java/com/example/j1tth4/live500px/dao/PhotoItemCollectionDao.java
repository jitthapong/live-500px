package com.example.j1tth4.live500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toy_o on 2/3/2559.
 */
public class PhotoItemCollectionDao implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(success ? (byte) 1 : (byte) 0);
        dest.writeTypedList(data);
    }

    public PhotoItemCollectionDao() {
    }

    protected PhotoItemCollectionDao(Parcel in) {
        this.success = in.readByte() != 0;
        this.data = in.createTypedArrayList(PhotoItemDao.CREATOR);
    }

    public static final Parcelable.Creator<PhotoItemCollectionDao> CREATOR = new Parcelable.Creator<PhotoItemCollectionDao>() {
        public PhotoItemCollectionDao createFromParcel(Parcel source) {
            return new PhotoItemCollectionDao(source);
        }

        public PhotoItemCollectionDao[] newArray(int size) {
            return new PhotoItemCollectionDao[size];
        }
    };
}
