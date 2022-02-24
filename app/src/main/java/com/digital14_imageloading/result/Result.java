package com.digital14_imageloading.result;

import com.digital14_imageloading.model.ImageListResponse;

import java.util.List;

public class Result {

    private String mQuery;
    private List<ImageListResponse.Hit> mList;
    private ResultType mResultType;
    private Integer mPage;

    public Result(String query, List<ImageListResponse.Hit> list, Integer page, ResultType resultType) {
        mQuery = query;
        mList = list;
        mPage = page;
        mResultType = resultType;
    }

    public String getQuery() {
        return mQuery;
    }

    public List<ImageListResponse.Hit> getList() {
        return mList;
    }

    public ResultType getResultType() {
        return mResultType;
    }

    public Integer getPage() {
        return mPage;
    }
}
