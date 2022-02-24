package com.digital14_imageloading.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.digital14_imageloading.R;
import com.digital14_imageloading.fragment.ImageDetailFragment;
import com.digital14_imageloading.fragment.ImageListFragment;

public class MainActivity extends AppCompatActivity implements ImageListFragment.Listener {


    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ImageListFragment.getInstance(), ImageListFragment.TAG).commit();
    }


    @Override
    public void onItemClick(String url) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ImageDetailFragment.getInstance(url), ImageDetailFragment.TAG).addToBackStack(ImageDetailFragment.TAG).commit();
    }
}
