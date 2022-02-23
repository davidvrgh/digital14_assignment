package com.digital14_imageloading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.digital14_imageloading.databinding.ImgDetailBinding;

public class ImageDetailFragment extends Fragment {

    public static final String PARAM_URL = "url";
    public static final String TAG = ImageDetailFragment.class.getSimpleName();

    public static ImageDetailFragment getInstance(String url) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ImgDetailBinding mBinding;
    private String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(PARAM_URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = ImgDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getActivity()).load(mUrl).into(mBinding.fullImg);
    }
}
