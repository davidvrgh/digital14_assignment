package com.digital14_imageloading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digital14_imageloading.R;
import com.digital14_imageloading.model.ImageListResponse;
import com.digital14_imageloading.utils.ListItemClickListener;
import com.digital14_imageloading.viewmodel.ImageListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.BaseHolder> {

    private List<ImageListResponse.Hit> mHits;
    private Context mContext;
    private ListItemClickListener mListener;

    private static final int TYPE_LOADING = 1;
    private static final int TYPE_ITEM = 2;

    public ListAdapter(Context context, ListItemClickListener listener) {
        mHits = new ArrayList<>();
        mContext = context;
        mListener = listener;
    }

    public void setListItems(List<ImageListResponse.Hit> listItems) {
        mHits.clear();
        mHits.addAll(listItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOADING:
                return new ProgressViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_view_loader, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false), mListener);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bind(mHits.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (mHits.get(position).getId() == ImageListViewModel.ID_DUMMY) {
            return TYPE_LOADING;
        } else {
            return TYPE_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return mHits.size();
    }

    class ViewHolder extends BaseHolder implements View.OnClickListener {
        private TextView mTXt;
        private ImageView mImg;
        private ListItemClickListener mListener;
        private ImageListResponse.Hit mLatestModel;

        public ViewHolder(@NonNull View itemView, ListItemClickListener listener) {
            super(itemView);
            mTXt = itemView.findViewById(R.id.header);
            mImg = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
            mListener = listener;
        }

        public void bind(ImageListResponse.Hit hit) {
            mLatestModel = hit;
            mTXt.setText(mLatestModel.getUser());
            Glide.with(mImg.getContext()).load(mLatestModel.getPreviewURL()).into(mImg);
        }


        @Override
        public void onClick(View v) {
            mListener.onItemClick(mLatestModel.getLargeImageURL());
        }
    }

    class ProgressViewHolder extends BaseHolder {

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(ImageListResponse.Hit hit) {

        }
    }

    abstract class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(ImageListResponse.Hit hit);
    }

}
