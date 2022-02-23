package com.digital14_imageloading;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListModel extends ViewModel {

    private int mTotalSize;
    private int mCurrentSize;
    private Map<Integer, ImageListResponse> mDataMap;
    private List<ImageListResponse.Hit> mDisplayList;
    private String mQuery;
    private boolean mIsLoading;
    private Listener mListener;


    public ImageListModel(String query, Listener listener) {
        mDataMap = new HashMap<>();
        mDisplayList = new ArrayList<>();
        mCurrentSize = 0;
        mTotalSize = 0;
        mQuery = query;
        mIsLoading = false;
        mListener = listener;
    }

    public List<ImageListResponse.Hit> getDisplayList() {
        return mDisplayList;
    }

    private void clearModel() {
        mDataMap.clear();
    }

    private void addData(int page, ImageListResponse response) {
        mDataMap.put(page, response);
        computeDisplayList();
        int latestTotal = response.getTotalHits();
        if (latestTotal != mTotalSize) {
            mTotalSize = latestTotal;
        }
    }

    private void computeDisplayList() {
        mDisplayList.clear();
        Set<Map.Entry<Integer, ImageListResponse>> entrySet = mDataMap.entrySet();
        for (Map.Entry entry : entrySet) {
            mDisplayList.addAll(((ImageListResponse) entry.getValue()).getHits());
        }
        mCurrentSize = mDisplayList.size();
    }

    private boolean needToLoadMore() {
        return (mCurrentSize < mTotalSize) || mDataMap.isEmpty();
    }

    public void loadData() {
        if (needToLoadMore() && !mIsLoading) {
            mIsLoading = true;
            int page = mDataMap.size() + 1;
            RetrofitService.getInstance().getListForQuery(Utils.API_KEY, mQuery, page).enqueue(new Callback<ImageListResponse>() {
                @Override
                public void onResponse(Call<ImageListResponse> call, Response<ImageListResponse> response) {
                    mIsLoading = false;
                    addData(page, response.body());
                    mListener.doRefresh();
                }

                @Override
                public void onFailure(Call<ImageListResponse> call, Throwable t) {
                    mIsLoading = false;
                }
            });
        }
    }

    interface Listener {

        public void doRefresh();

    }

}
