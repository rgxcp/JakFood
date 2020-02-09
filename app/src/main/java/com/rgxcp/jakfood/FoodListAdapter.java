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

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ListViewHolder> implements Filterable {

    // Setup constructor
    private Context mContext;
    private ArrayList<FoodList> mArrayList;
    private List<FoodList> mList;

    // Constructor
    FoodListAdapter(Context context, ArrayList<FoodList> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mList = new ArrayList<>(mArrayList);
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
        Double mBintangDouble = mArrayList.get(position).getBintang();
        String mAlamatSingkat = mArrayList.get(position).getAlamat_singkat();
        String mNamaRestoran = mArrayList.get(position).getNama_restoran();
        String mThumbnailRestoran = mArrayList.get(position).getThumbnail_restoran();
        String mBintangString = mBintangDouble.toString();
        RequestOptions mRequestOptions = new RequestOptions().centerCrop().placeholder(R.color.graySecondary).error(R.color.graySecondary);

        final String mIdRestoran = mArrayList.get(position).getId_restoran();
        final String mJenisMakanan = mArrayList.get(position).getJenis_makanan();

        // Validasi bintang
        if (mBintangDouble >= 0 && mBintangDouble <= 1.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_1);
        } else if (mBintangDouble >= 2 && mBintangDouble <= 2.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_2);
        } else if (mBintangDouble >= 3 && mBintangDouble <= 3.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_3);
        } else if (mBintangDouble >= 4 && mBintangDouble <= 4.9) {
            holder.mImageStar.setImageResource(R.drawable.ic_star_4);
        } else {
            holder.mImageStar.setImageResource(R.drawable.ic_star_5);
        }

        // Mengisi data layout recycler view
        holder.mTextRestaurantName.setText(mNamaRestoran);
        holder.mTextShortAddress.setText(mAlamatSingkat);
        holder.mTextStar.setText(mBintangString);
        Glide.with(mContext).load(mThumbnailRestoran).apply(mRequestOptions).into(holder.mImageRestaurantThumbnail);

        // Activities
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodDetail = new Intent(mContext, FoodDetailActivity.class);
                mGotoFoodDetail.putExtra("IdRestoranArgs", mIdRestoran);
                mGotoFoodDetail.putExtra("JenisMakananArgs", mJenisMakanan);
                mContext.startActivity(mGotoFoodDetail);
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
        return mArrayList.size();
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
            List<FoodList> mListFiltered = new ArrayList<>();

            // Cari list
            if (charSequence == null || charSequence.length() == 0) {
                mListFiltered.addAll(mList);
            } else {
                String mQuery = charSequence.toString().toLowerCase().trim();
                for (FoodList mFoodList : mList) {
                    // Berdasarkan nama restoran
                    if (mFoodList.getNama_restoran().toLowerCase().contains(mQuery)) {
                        mListFiltered.add(mFoodList);
                    }

                    // Berdasarkan alamat restoran
                    if (mFoodList.getAlamat_singkat().toLowerCase().contains(mQuery)) {
                        mListFiltered.add(mFoodList);
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
            mArrayList.clear();
            mArrayList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Class
    class ListViewHolder extends RecyclerView.ViewHolder {

        // Deklarasi variable global
        ImageView mImageRestaurantThumbnail, mImageStar;
        TextView mTextRestaurantName, mTextShortAddress, mTextStar;

        // Constructor
        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variable global
            mImageRestaurantThumbnail = itemView.findViewById(R.id.img_rfl_restaurant_thumbnail);
            mImageStar = itemView.findViewById(R.id.img_rfl_star);
            mTextRestaurantName = itemView.findViewById(R.id.txt_rfl_restaurant_name);
            mTextShortAddress = itemView.findViewById(R.id.txt_rfl_short_address);
            mTextStar = itemView.findViewById(R.id.txt_rfl_star);
        }
    }
}
