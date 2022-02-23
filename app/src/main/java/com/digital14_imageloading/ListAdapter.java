package com.digital14_imageloading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ImageListResponse.Hit> mHits;
    private Context mContext;
    private ListItemClickListener mListener;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mHits.get(position));
    }

    @Override
    public int getItemCount() {
        return mHits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

}
