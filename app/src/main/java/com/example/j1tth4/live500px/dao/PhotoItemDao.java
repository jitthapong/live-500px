package com.example.j1tth4.live500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by toy_o on 2/3/2559.
 */
public class PhotoItemDao implements Parcelable {

    @SerializedName("id")  private Integer id;
    @SerializedName("link") private String link;
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("caption") private String caption;
    @SerializedName("user_id") private Integer userId;
    @SerializedName("username") private String username;
    @SerializedName("profile_picture") private String profilePicture;
    @SerializedName("tags") private List<String> tags = new ArrayList<>();
    @SerializedName("created_time") private Date createdTime;
    @SerializedName("camera") private String camera;
    @SerializedName("lens") private String lens;
    @SerializedName("focal_length") private String focalLength;
    @SerializedName("iso") private String iso;
    @SerializedName("shutter_speed") private String shutterSpeed;
    @SerializedName("aperture") private String aperture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.link);
        dest.writeString(this.imageUrl);
        dest.writeString(this.caption);
        dest.writeValue(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.profilePicture);
        dest.writeStringList(this.tags);
        dest.writeLong(createdTime != null ? createdTime.getTime() : -1);
        dest.writeString(this.camera);
        dest.writeString(this.lens);
        dest.writeString(this.focalLength);
        dest.writeString(this.iso);
        dest.writeString(this.shutterSpeed);
        dest.writeString(this.aperture);
    }

    public PhotoItemDao() {
    }

    protected PhotoItemDao(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.link = in.readString();
        this.imageUrl = in.readString();
        this.caption = in.readString();
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.username = in.readString();
        this.profilePicture = in.readString();
        this.tags = in.createStringArrayList();
        long tmpCreatedTime = in.readLong();
        this.createdTime = tmpCreatedTime == -1 ? null : new Date(tmpCreatedTime);
        this.camera = in.readString();
        this.lens = in.readString();
        this.focalLength = in.readString();
        this.iso = in.readString();
        this.shutterSpeed = in.readString();
        this.aperture = in.readString();
    }

    public static final Parcelable.Creator<PhotoItemDao> CREATOR = new Parcelable.Creator<PhotoItemDao>() {
        public PhotoItemDao createFromParcel(Parcel source) {
            return new PhotoItemDao(source);
        }

        public PhotoItemDao[] newArray(int size) {
            return new PhotoItemDao[size];
        }
    };
}
