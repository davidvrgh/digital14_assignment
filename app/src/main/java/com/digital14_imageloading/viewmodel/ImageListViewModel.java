package com.digital14_imageloading.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digital14_imageloading.constants.Constants;
import com.digital14_imageloading.model.ImageListResponse;
import com.digital14_imageloading.network.RetrofitService;
import com.digital14_imageloading.result.Result;
import com.digital14_imageloading.result.ResultType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListViewModel extends ViewModel {

    public final static int ID_DUMMY = -1;
    private MutableLiveData<Result> mResult;
    private int mTotalSize;
    private int mCurrentSize;
    private Map<Integer, ImageListResponse> mDataMap;
    private List<ImageListResponse.Hit> mDisplayList;
    private String mQuery;
    private boolean mIsLoading;

    public ImageListViewModel() {
        init();
    }

    public void setQuery(String query) {
        mQuery = query;
        init();
        loadData();
    }

    private void init() {
        mResult = new MutableLiveData<>();
        mDataMap = new HashMap<>();
        mDisplayList = new ArrayList<>();
        mCurrentSize = 0;
        mTotalSize = 0;
        mIsLoading = false;
    }

    public MutableLiveData<Result> getResult() {
        return mResult;
    }

    /*public List<ImageListResponse.Hit> getDisplayList() {
        return mDisplayList;
    }*/

    /*private void clearModel() {
        mDataMap.clear();
    }*/

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
        Set<Integer> keySet = mDataMap.keySet();
        for (Integer page : keySet) {
            mDisplayList.addAll(mDataMap.get(page).getHits());
        }
        if (needToLoadMore()) {
            mDisplayList.add(getDummyHit());
        }
        mCurrentSize = mDisplayList.size();
    }

    private static ImageListResponse.Hit getDummyHit() {
        return new ImageListResponse.Hit(null, null, null, ID_DUMMY, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    private boolean needToLoadMore() {
        return (mCurrentSize < mTotalSize) || mDataMap.isEmpty();
    }

    public void loadData() {
        if (needToLoadMore() && !mIsLoading) {
            mIsLoading = true;
            int page = mDataMap.size() + 1;
            mResult.setValue(new Result(mQuery, mDisplayList, page, ResultType.TYPE_PROGRESS));
            RetrofitService.getInstance().getListForQuery(Constants.API_KEY, mQuery, page).enqueue(new Callback<ImageListResponse>() {
                @Override
                public void onResponse(Call<ImageListResponse> call, Response<ImageListResponse> response) {
                    mIsLoading = false;
                    addData(page, response.body());
                    mResult.setValue(new Result(mQuery, mDisplayList, page, ResultType.TYPE_SUCCESS));
                }

                @Override
                public void onFailure(Call<ImageListResponse> call, Throwable t) {
                    mIsLoading = false;
                    mResult.setValue(new Result(mQuery, mDisplayList, page, ResultType.TYPE_FAILURE));
                }
            });
        }
    }
}
