package com.rgxcp.jakfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ZomatoReviewAdapter extends RecyclerView.Adapter<ZomatoReviewAdapter.ListViewHolder>{

    // Setup constructor
    private Context mContext;
    private List<ZomatoReview> mList;

    // Constructor
    ZomatoReviewAdapter(Context mContext, List<ZomatoReview> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // Deklarasi dan assign variable lokal || Menangkap data dari Getter
        String mName = mList.get(position).getName();
        String mRating = String.valueOf(mList.get(position).getRating());
        String mReviewTime = mList.get(position).getReview_time_friendly();
        String mReview = mList.get(position).getReview_text();
        String mProfile = mList.get(position).getProfile_image();
        RequestOptions mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_placeholder);

        // Mengisi data layout recycler view
        holder.mTextName.setText(mName);
        holder.mTextRating.setText(mRating);
        holder.mTextReviewTime.setText(mReviewTime);
        holder.mTextReview.setText(mReview);
        Glide.with(mContext).load(mProfile).apply(mRequestOptions).into(holder.mImageProfile);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // Class
    class ListViewHolder extends RecyclerView.ViewHolder {

        // Deklarasi variable global
        ImageView mImageProfile;
        TextView mTextName, mTextRating, mTextReviewTime, mTextReview;

        // Constructor
        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variable global
            mImageProfile = itemView.findViewById(R.id.img_rr_profile);
            mTextName = itemView.findViewById(R.id.txt_rr_name);
            mTextRating = itemView.findViewById(R.id.txt_rr_rating);
            mTextReviewTime = itemView.findViewById(R.id.txt_rr_review_time);
            mTextReview = itemView.findViewById(R.id.txt_rr_review);
        }
    }
}
