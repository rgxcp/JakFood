package com.rgxcp.jakfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ListViewHolder> implements Filterable {

    // Deklarasi variable global
    private DatabaseReference mFirebase;

    // Setup constructor
    private Context mContext;
    private ArrayList<FoodList> mArrayList;
    private List<FoodList> mList;

    // Validasi user
    private String mUsername;

    // Constructor
    FavoriteAdapter(Context mContext, ArrayList<FoodList> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        mList = new ArrayList<>(mArrayList);
    }

    // Recycler view methods
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // Menjalankan method
        getUsername();

        // Deklarasi dan assign variable lokal || Menangkap data dari Getter
        String mAlamatSingkat = mArrayList.get(position).getAlamat_singkat();
        String mNamaRestoran = mArrayList.get(position).getNama_restoran();
        String mThumbnailRestoran = mArrayList.get(position).getThumbnail_restoran();
        RequestOptions mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_placeholder);

        final String mIdRestoran = mArrayList.get(position).getId_restoran();
        final String mJenisMakanan = mArrayList.get(position).getJenis_makanan();

        // Mengisi data layout recycler view
        holder.mTextRestaurantName.setText(mNamaRestoran);
        holder.mTextShortAddress.setText(mAlamatSingkat);
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

        holder.mTextDeleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebase = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername).child(mIdRestoran);
                mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Menghapus dari favorite
                        mFirebase.getRef().child("alamat_singkat").removeValue();
                        mFirebase.getRef().child("id_restoran").removeValue();
                        mFirebase.getRef().child("jenis_makanan").removeValue();
                        mFirebase.getRef().child("nama_restoran").removeValue();
                        mFirebase.getRef().child("thumbnail_restoran").removeValue();
                        mFirebase.getRef().child("waktu_akses").removeValue();
                        Toast.makeText(mContext, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(mContext, "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
        ImageView mImageRestaurantThumbnail;
        TextView mTextRestaurantName, mTextShortAddress, mTextDeleteFavorite;

        // Constructor
        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variable global
            mImageRestaurantThumbnail = itemView.findViewById(R.id.img_rf_restaurant_thumbnail);
            mTextRestaurantName = itemView.findViewById(R.id.txt_rf_restaurant_name);
            mTextShortAddress = itemView.findViewById(R.id.txt_rf_short_address);
            mTextDeleteFavorite = itemView.findViewById(R.id.txt_rf_delete_favorite);
        }
    }

    // Method mendapatkan data user aktif
    private void getUsername() {
        String USERNAME_KEY = "username_key";
        String UsernameKeyArgs = "";
        SharedPreferences mSharedPreferences = Objects.requireNonNull(mContext.getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE));
        mUsername = mSharedPreferences.getString(UsernameKeyArgs, "");
    }
}
