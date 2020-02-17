package com.rgxcp.jakfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    // Deklarasi variable global
    private ProgressBar mProgressBar;
    private TextView mTextFullName, mTextEmail, mTextTotalFavorite;
    private DatabaseReference mFirebase;

    // Validasi user
    private String USERNAME_KEY = "username_key";
    private String UsernameKeyArgs = "";
    private String mUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Menjalankan method
        getUsername();

        // Assign variable global
        mProgressBar = mView.findViewById(R.id.pbr_fp_loading);
        mTextFullName = mView.findViewById(R.id.txt_fp_full_name);
        mTextEmail = mView.findViewById(R.id.txt_fp_email);
        mTextTotalFavorite = mView.findViewById(R.id.txt_fp_total_favorite);

        // Deklarasi dan assign variable lokal
        ImageView mImageProfile = mView.findViewById(R.id.img_fp_profile);
        TextView mTextEditProfile = mView.findViewById(R.id.txt_fp_edit_profile);
        TextView mTextHelp = mView.findViewById(R.id.txt_fp_help);
        TextView mTextAbout = mView.findViewById(R.id.txt_fp_about);
        TextView mButtonSignOut = mView.findViewById(R.id.txt_fp_sign_out);

        // Mengecek apakah ada user aktif
        if (mUsername.isEmpty()) {
            EmptyUserFragment mEmptyUserFragment = new EmptyUserFragment();
            FragmentManager mFragmentManager = getFragmentManager();
            if (mFragmentManager != null) {
                mFragmentManager.beginTransaction().replace(R.id.fragment_container, mEmptyUserFragment).commit();
            }
        } else {
            mFirebase = FirebaseDatabase.getInstance().getReference().child("user").child(mUsername);
            mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mTextFullName.setText(Objects.requireNonNull(dataSnapshot.child("full_name").getValue()).toString());
                    mTextEmail.setText(Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString());
                    mProgressBar.setVisibility(View.INVISIBLE);

                    mFirebase = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername);
                    mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String mTotalFavorite = dataSnapshot.getChildrenCount() + " Makanan Favorit";
                            mTextTotalFavorite.setText(mTotalFavorite);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Activities
        mImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Tunggu update selanjutnya ya!", Toast.LENGTH_SHORT).show();
            }
        });

        mTextEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileFragment mEditProfileFragment = new EditProfileFragment();
                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager.beginTransaction().replace(R.id.fragment_container, mEditProfileFragment).addToBackStack(null).commit();
                }
            }
        });

        mTextHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoGetStarted = new Intent(getActivity(), GetStartedActivity.class);
                startActivity(mGotoGetStarted);
            }
        });

        mTextAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutFragment mAboutFragment = new AboutFragment();
                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager.beginTransaction().replace(R.id.fragment_container, mAboutFragment).addToBackStack(null).commit();
                }
            }
        });

        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menghapus username dari storage lokal
                SharedPreferences mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.putString(UsernameKeyArgs, null);
                mEditor.apply();

                Intent mGotoSplash = new Intent(getActivity(), SplashActivity.class);
                startActivity(mGotoSplash);
                getActivity().finish();
            }
        });

        // Menampilkan layout
        return mView;
    }

    // Method mendapatkan data user aktif
    private void getUsername() {
        SharedPreferences mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString(UsernameKeyArgs, "");
    }
}
