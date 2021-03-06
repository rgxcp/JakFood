package com.rgxcp.jakfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class FavoriteFragment extends Fragment {

    // Deklarasi variable global
    private ShimmerFrameLayout mShimmer;

    // Setup recycler view
    private ArrayList<FavoriteList> mArrayList;
    private FavoriteAdapter mFavoriteAdapter;
    private RecyclerView mRecyclerView;

    // Validasi user
    private String mUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Menjalankan method
        getUsername();

        // Assign variable global
        mShimmer = mView.findViewById(R.id.sfl_ff_loading);

        // Deklarasi dan assign variable lokal
        SearchView mSearchView = mView.findViewById(R.id.srv_ff_favorite);
        DatabaseReference mFirebase;

        // Shimmer
        mShimmer.startShimmer();

        // Recycler view
        mArrayList = new ArrayList<>();
        mRecyclerView = mView.findViewById(R.id.rcv_ff_favorite);

        // Setup layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Setup hint search view
        mSearchView.setQueryHint("Cari makanan favorit");

        // Mengecek apakah ada user aktif
        if (mUsername.isEmpty()) {
            FragmentManager mFragmentManager = getFragmentManager();
            if (mFragmentManager != null) {
                mFragmentManager.beginTransaction().replace(R.id.fragment_container, new EmptyUserFragment()).commit();
            }
        } else {
            // Isi favorite food
            mFirebase = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername);
            mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Looping
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        // FoodList mFavoriteList = dataSnapshot1.getValue(FoodList.class);

                        // Mengirim data ke Setter
                        FavoriteList mFavoriteList = new FavoriteList();
                        mFavoriteList.setAlamat_singkat(Objects.requireNonNull(dataSnapshot1.child("alamat_singkat").getValue()).toString());
                        mFavoriteList.setId_restoran(Objects.requireNonNull(dataSnapshot1.child("id_restoran").getValue()).toString());
                        mFavoriteList.setJenis_makanan(Objects.requireNonNull(dataSnapshot1.child("jenis_makanan").getValue()).toString());
                        mFavoriteList.setNama_restoran(Objects.requireNonNull(dataSnapshot1.child("nama_restoran").getValue()).toString());
                        mFavoriteList.setThumbnail_restoran(Objects.requireNonNull(dataSnapshot1.child("thumbnail_restoran").getValue()).toString());
                        mFavoriteList.setWaktu_akses(Objects.requireNonNull(dataSnapshot1.child("waktu_akses").getValue()).toString());

                        // Menambahkan semua data ke model
                        mArrayList.add(mFavoriteList);
                    }

                    // Sort item
                    Collections.sort(mArrayList, new Comparator<FavoriteList>() {
                        @Override
                        public int compare(FavoriteList favoriteList, FavoriteList t1) {
                            return t1.getWaktu_akses().compareTo(favoriteList.getWaktu_akses());
                        }
                    });

                    // Setup adapter
                    mFavoriteAdapter = new FavoriteAdapter(getActivity(), mArrayList);
                    mRecyclerView.setAdapter(mFavoriteAdapter);

                    // Shimmer
                    mShimmer.stopShimmer();
                    mShimmer.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Activities
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mFavoriteAdapter.getFilter().filter(s);
                return false;
            }
        });

        // Menampilkan layout
        return mView;
    }

    // Method mendapatkan data user aktif
    private void getUsername() {
        String USERNAME_KEY = "username_key";
        String UsernameKeyArgs = "";
        SharedPreferences mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString(UsernameKeyArgs, "");
    }
}
