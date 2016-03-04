package com.example.j1tth4.live500px.manager.http;

import com.example.j1tth4.live500px.dao.PhotoItemCollectionDao;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by toy_o on 2/3/2559.
 */
public interface ApiService {

    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList();

    @POST("list/after/{id}")
    Call<PhotoItemCollectionDao> loadPhotoListAfterId(@Path("id") int id);

    @POST("list/before/{id}")
    Call<PhotoItemCollectionDao> loadPhotoListBeforeId(@Path("id") int id);
}
