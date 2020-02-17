package com.rgxcp.jakfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ZomatoFoodListAdapter extends RecyclerView.Adapter<ZomatoFoodListAdapter.ListViewHolder> implements Filterable {

    // Setup constructor
    private Context mContext;
    private List<ZomatoFoodList> mList;
    private List<ZomatoFoodList> mListFilter;

    // Constructor
    ZomatoFoodListAdapter(Context mContext, List<ZomatoFoodList> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mListFilter = new ArrayList<>(mList);
    }

    // Recycler view methods
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_food_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // Deklarasi dan assign variable lokal || Menangkap data dari Getter
        Double mStarDouble = Double.valueOf(mList.get(position).getAggregate_rating());
        String mName = mList.get(position).getName();
        String mShortAddress = mList.get(position).getLocality_verbose();
        String mStarString = mList.get(position).getAggregate_rating();
        String mThumbnail = mList.get(position).getThumb();
        RequestOptions mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_placeholder);

        final String mRestaurantId = mList.get(position).getId();

        // Validasi bintang
        if (mStarDouble >= 0 && mStarDouble <= 1.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_1);
        } else if (mStarDouble >= 2 && mStarDouble <= 2.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_2);
        } else if (mStarDouble >= 3 && mStarDouble <= 3.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_3);
        } else if (mStarDouble >= 4 && mStarDouble <= 4.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_4);
        } else {
            holder.mImageStar.setImageResource(R.drawable.ic_star_5);
        }

        // Mengisi data layout recycler view
        holder.mTextName.setText(mName);
        holder.mTextShortAddress.setText(mShortAddress);
        holder.mTextStar.setText(mStarString);
        Glide.with(mContext).load(mThumbnail).apply(mRequestOptions).into(holder.mImageThumbnail);

        // Activities
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoZomatoFoodDetail = new Intent(mContext, ZomatoFoodDetailActivity.class);
                mGotoZomatoFoodDetail.putExtra("RestaurantIdArgs", mRestaurantId);
                mContext.startActivity(mGotoZomatoFoodDetail);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // Search view methods
    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            // Setup list
            List<ZomatoFoodList> mListFiltered = new ArrayList<>();

            // Cari list
            if (charSequence == null || charSequence.length() == 0) {
                mListFiltered.addAll(mListFilter);
            } else {
                String mQuery = charSequence.toString().toLowerCase().trim();
                for (ZomatoFoodList mZomatoFoodList : mListFilter) {
                    // Berdasarkan nama restoran
                    if (mZomatoFoodList.getName().toLowerCase().contains(mQuery)) {
                        mListFiltered.add(mZomatoFoodList);
                    }

                    // Berdasarkan alamat restoran
                    if (mZomatoFoodList.getLocality_verbose().toLowerCase().contains(mQuery)) {
                        mListFiltered.add(mZomatoFoodList);
                    }
                }
            }

            // Setup hasil
            FilterResults mFilterResults = new FilterResults();
            mFilterResults.values = mListFiltered;

            // Menampilkan hasil
            return mFilterResults;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Class
    class ListViewHolder extends RecyclerView.ViewHolder {

        // Deklarasi variable global
        ImageView mImageThumbnail, mImageStar;
        TextView mTextName, mTextShortAddress, mTextStar;

        // Constructor
        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variable global
            mImageThumbnail = itemView.findViewById(R.id.img_rfl_restaurant_thumbnail);
            mImageStar = itemView.findViewById(R.id.img_rfl_star);
            mTextName = itemView.findViewById(R.id.txt_rfl_restaurant_name);
            mTextShortAddress = itemView.findViewById(R.id.txt_rfl_short_address);
            mTextStar = itemView.findViewById(R.id.txt_rfl_star);
        }
    }
}
