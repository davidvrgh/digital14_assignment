package com.digital14_imageloading;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {


    private static String DOMAIN = "https://pixabay.com";
    private static ApiService SERVICE;

    public static ApiService getInstance() {
        if (SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SERVICE = retrofit.create(ApiService.class);
        }
        return SERVICE;
    }


}
