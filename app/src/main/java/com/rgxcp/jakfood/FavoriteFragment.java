package com.rgxcp.jakfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteFragment extends Fragment {

    // Deklarasi variable global
    private ProgressBar mProgressBar;

    // Setup recycler view
    private ArrayList<FoodList> mArrayList;
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
        mProgressBar = mView.findViewById(R.id.pbr_ff_loading);

        // Deklarasi dan assign variable lokal
        SearchView mSearchView = mView.findViewById(R.id.srv_ff_favorite);
        DatabaseReference mDatabaseReference;

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
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername);
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Looping
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        FoodList mFoodList = dataSnapshot1.getValue(FoodList.class);
                        mArrayList.add(mFoodList);
                    }

                    // Setup adapter
                    mFavoriteAdapter = new FavoriteAdapter(getActivity(), mArrayList);
                    mRecyclerView.setAdapter(mFavoriteAdapter);

                    // Progress Bar
                    mProgressBar.setVisibility(View.INVISIBLE);
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
