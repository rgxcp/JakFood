package com.rgxcp.jakfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FoodListActivity extends AppCompatActivity {

    // Deklarasi variable global
    private ShimmerFrameLayout mShimmer;

    // Setup recycler view
    private ArrayList<FoodList> mArrayList;
    private FoodListAdapter mFoodListAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // Assign variable global
        mShimmer = findViewById(R.id.sfl_afl_loading);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = findViewById(R.id.btn_afl_back);
        SearchView mSearchView = findViewById(R.id.srv_afl_food);
        TextView mTextFoodName = findViewById(R.id.txt_afl_food_name);
        DatabaseReference mFirebase;

        // Shimmer
        mShimmer.startShimmer();

        // Recycler view
        mArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rcv_afl_food_list);

        // Setup layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Menangkap data dari Intent
        Bundle mBundle = getIntent().getExtras();
        String mJenisMakanan = Objects.requireNonNull(mBundle).getString("JenisMakananArgs");
        String mNamaMakanan = Objects.requireNonNull(mBundle).getString("NamaMakananArgs");

        // Setup hint search view
        mTextFoodName.setText(mNamaMakanan);
        mSearchView.setQueryHint("Cari " + Objects.requireNonNull(mNamaMakanan).toLowerCase() + " apa?");

        // Isi food list
        if (mJenisMakanan != null) {
            mFirebase = FirebaseDatabase.getInstance().getReference().child("semua_makanan").child(mJenisMakanan);
            mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Looping
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        // FoodList mFoodList = dataSnapshot1.getValue(FoodList.class);

                        // Mengirim data ke Setter
                        FoodList mFoodList = new FoodList();
                        mFoodList.setBintang(Double.valueOf(Objects.requireNonNull(dataSnapshot1.child("bintang").getValue()).toString()));
                        mFoodList.setAlamat_singkat(Objects.requireNonNull(dataSnapshot1.child("alamat_singkat").getValue()).toString());
                        mFoodList.setId_restoran(Objects.requireNonNull(dataSnapshot1.child("id_restoran").getValue()).toString());
                        mFoodList.setJenis_makanan(Objects.requireNonNull(dataSnapshot1.child("jenis_makanan").getValue()).toString());
                        mFoodList.setNama_restoran(Objects.requireNonNull(dataSnapshot1.child("nama_restoran").getValue()).toString());
                        mFoodList.setThumbnail_restoran(Objects.requireNonNull(dataSnapshot1.child("thumbnail_restoran").getValue()).toString());

                        // Menambahkan semua data ke model
                        mArrayList.add(mFoodList);
                    }

                    // Setup adapter
                    mFoodListAdapter = new FoodListAdapter(FoodListActivity.this, mArrayList);
                    mRecyclerView.setAdapter(mFoodListAdapter);

                    // Shimmer
                    mShimmer.stopShimmer();
                    mShimmer.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Activities
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mFoodListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
}
