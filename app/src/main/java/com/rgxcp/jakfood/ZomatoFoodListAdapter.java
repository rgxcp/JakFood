package com.rgxcp.jakfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ZomatoFoodListAdapter extends RecyclerView.Adapter<ZomatoFoodListAdapter.ZomatoFoodListViewHolder> {

    private Context mContext;
    private List<ZomatoFoodList> mZomatoFoodList;

    ZomatoFoodListAdapter(Context mContext, List<ZomatoFoodList> mZomatoFoodList) {
        this.mContext = mContext;
        this.mZomatoFoodList = mZomatoFoodList;
    }

    @NonNull
    @Override
    public ZomatoFoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ZomatoFoodListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_food_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ZomatoFoodListViewHolder holder, int position) {
        final String mRestaurantID = String.valueOf(mZomatoFoodList.get(position).getId());

        holder.mName.setText(mZomatoFoodList.get(position).getName());
        holder.mShortAddress.setText(mZomatoFoodList.get(position).getLocality_verbose());
        holder.mStar.setText(mZomatoFoodList.get(position).getAggregate_rating());
        Glide.with(mContext).load(mZomatoFoodList.get(position).getThumb()).into(holder.mThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoZomatoFoodDetail = new Intent(mContext, ZomatoFoodDetailActivity.class);
                mGotoZomatoFoodDetail.putExtra("IDArgs", mRestaurantID);
                mContext.startActivity(mGotoZomatoFoodDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mZomatoFoodList.size();
    }

    class ZomatoFoodListViewHolder extends RecyclerView.ViewHolder {
        ImageView mThumbnail;
        TextView mName, mShortAddress, mStar;

        ZomatoFoodListViewHolder(@NonNull View itemView) {
            super(itemView);

            mThumbnail = itemView.findViewById(R.id.img_rfl_restaurant_thumbnail);
            mName = itemView.findViewById(R.id.txt_rfl_restaurant_name);
            mShortAddress = itemView.findViewById(R.id.txt_rfl_short_address);
            mStar = itemView.findViewById(R.id.txt_rfl_star);
        }
    }
}
