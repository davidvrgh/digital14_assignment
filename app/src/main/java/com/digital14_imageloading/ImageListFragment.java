package com.digital14_imageloading;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital14_imageloading.databinding.ImgListFragmentBinding;

public class ImageListFragment extends Fragment implements ImageListModel.Listener, ListItemClickListener {

    private ImgListFragmentBinding mBinding;
    private ImageListModel mImageListModel;
    private ListAdapter mAdapter;

    public static ImageListFragment getInstance() {
        return new ImageListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ListAdapter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = ImgListFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mBinding.edtSearch.getText().toString();
                mImageListModel = new ImageListModel(query, ImageListFragment.this);
                mImageListModel.loadData();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.list.setLayoutManager(linearLayoutManager);
        mBinding.list.setAdapter(mAdapter);
        mBinding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastViewPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                if (lastViewPosition >= mAdapter.getItemCount() - 1) {
                    mImageListModel.loadData();
                }
            }
        });
    }

    @Override
    public void doRefresh() {
        mAdapter.setListItems(mImageListModel.getDisplayList());
    }

    @Override
    public void onItemClick(String url) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ImageDetailFragment.getInstance(url), ImageDetailFragment.TAG).addToBackStack(ImageDetailFragment.TAG).commit();
    }
}
