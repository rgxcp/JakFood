package com.rgxcp.jakfood;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ZomatoPhotoAdapter extends RecyclerView.Adapter<ZomatoPhotoAdapter.ListViewHolder> {

    // Setup constructor
    private Context mContext;
    private List<ZomatoPhoto> mList;

    // Constructor
    ZomatoPhotoAdapter(Context mContext, List<ZomatoPhoto> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // Deklarasi dan assign variable lokal || Menangkap data dari Getter
        String mPhotoThumbnail = mList.get(position).getThumb_url();
        RequestOptions mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_placeholder);

        final String mPhoto = mList.get(position).getUrl();

        // Mengisi data layout recycler view
        Glide.with(mContext).load(mPhotoThumbnail).apply(mRequestOptions).into(holder.mImagePhoto);

        // Activities
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuFragment mPhotoFragment = new MenuFragment();
                FragmentManager mFragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                mFragmentManager.beginTransaction().replace(R.id.photo_azfd_container, mPhotoFragment).addToBackStack(null).commit();

                Bundle mBundle = new Bundle();
                mBundle.putString("MenuArgs", mPhoto);
                mPhotoFragment.setArguments(mBundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // Class
    static class ListViewHolder extends RecyclerView.ViewHolder {

        // Deklarasi variable global
        ImageView mImagePhoto;

        // Constructor
        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variable global
            mImagePhoto = itemView.findViewById(R.id.img_rp_photo);
        }
    }
}
