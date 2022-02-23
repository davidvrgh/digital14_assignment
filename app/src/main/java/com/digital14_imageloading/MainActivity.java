package com.digital14_imageloading;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitService.getInstance().getListForQuery(Utils.API_KEY, "car", 1).enqueue(new Callback<ImageListResponse>() {
            @Override
            public void onResponse(Call<ImageListResponse> call, Response<ImageListResponse> response) {
                Log.e(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<ImageListResponse> call, Throwable t) {
                Log.e(TAG, "onFailed");
            }
        });
    }
}
