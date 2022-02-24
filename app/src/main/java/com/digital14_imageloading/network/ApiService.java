package com.digital14_imageloading.network;

import com.digital14_imageloading.model.ImageListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/")
    Call<ImageListResponse> getListForQuery(@Query("key") String key, @Query("q") String query, @Query("page") int page);

}
