package com.digital14_imageloading.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digital14_imageloading.viewmodel.ImageListViewModel;
import com.digital14_imageloading.adapter.ListAdapter;
import com.digital14_imageloading.utils.ListItemClickListener;
import com.digital14_imageloading.databinding.ImgListFragmentBinding;
import com.digital14_imageloading.result.Result;

public class ImageListFragment extends Fragment implements ListItemClickListener, Observer<Result>, View.OnClickListener {

    public static final String TAG = ImageListFragment.class.getSimpleName();
    private ImgListFragmentBinding mBinding;
    private ListAdapter mAdapter;
    private ImageListViewModel mViewModel;
    private Listener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

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
        mViewModel = new ViewModelProvider(requireActivity()).get(ImageListViewModel.class);
        mBinding.btnSubmit.setOnClickListener(this);
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
                    mViewModel.loadData();
                }
            }
        });
        mViewModel.getResult().observe(getViewLifecycleOwner(), this);
    }

    @Override
    public void onItemClick(String url) {
        mListener.onItemClick(url);
    }

    @Override
    public void onChanged(Result result) {
        switch (result.getResultType()) {
            case TYPE_FAILURE:
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show(); //Need more descriptive error
                mAdapter.setListItems(result.getList());
                break;
            case TYPE_SUCCESS:
                mAdapter.setListItems(result.getList());
                break;
            case TYPE_PROGRESS:
                mAdapter.setListItems(result.getList());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String query = mBinding.edtSearch.getText().toString();
        mViewModel.setQuery(query);
    }

    public interface Listener extends ListItemClickListener {

    }

}
