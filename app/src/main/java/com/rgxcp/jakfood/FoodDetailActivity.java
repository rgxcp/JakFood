package com.rgxcp.jakfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FoodDetailActivity extends AppCompatActivity {

    // Deklarasi variable global
    private Button mButtonMaps;
    private ConstraintLayout mConstraintLayout;
    private Double mLat, mLng;
    private ImageView mImageRestaurant, mImageFavorite, mImageMenu;
    private ProgressBar mProgressBar;
    private RequestOptions mRequestOptionsMenu, mRequestOptionsRestaurant;
    private String mIdRestoranPopuler, mIdRestoranSemuaMakanan, mJenisMakananPopuler, mJenisMakananSemuaMakanan, mMenu, mRestaurantImage, mRestaurantName, mRestaurantThumbnail, mShortAddress;
    private TextView mTextRestaurantName, mTextFullAddress, mTextOpenDay, mTextOpenHour, mTextStarReview, mTextApproxPrice;
    private DatabaseReference mFirebaseAllFood, mFirebasePopuler;

    // Validasi user
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Menjalankan method
        getUsername();

        // Assign variable global
        mButtonMaps = findViewById(R.id.btn_afd_maps);
        mConstraintLayout = findViewById(R.id.cnt_afd_layout);
        mImageRestaurant = findViewById(R.id.img_afd_restaurant_image);
        mImageFavorite = findViewById(R.id.img_afd_favorite);
        mImageMenu = findViewById(R.id.img_afd_menu);
        mProgressBar = findViewById(R.id.pbr_afd_loading);
        mTextRestaurantName = findViewById(R.id.txt_afd_restaurant_name);
        mTextFullAddress = findViewById(R.id.txt_afd_full_address);
        mTextOpenDay = findViewById(R.id.txt_afd_open_day);
        mTextOpenHour = findViewById(R.id.txt_afd_open_hour);
        mTextStarReview = findViewById(R.id.txt_afd_star_review);
        mTextApproxPrice = findViewById(R.id.txt_afd_approx_price);
        mRequestOptionsMenu = new RequestOptions().centerCrop().placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_placeholder);
        mRequestOptionsRestaurant = new RequestOptions().centerCrop().placeholder(R.color.graySecondary).error(R.color.graySecondary);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = findViewById(R.id.btn_afd_back);
        DatabaseReference mFirebaseFavorite;

        // Menangkap data dari Intent
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String mIdPopuler = mBundle.getString("IdPopulerArgs");
            String mIdRestoran = mBundle.getString("IdRestoranArgs");
            String mJenisMakanan = mBundle.getString("JenisMakananArgs");

            // Handler favorite
            if (mUsername.isEmpty()) {
                mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
            } else {
                if (mIdPopuler != null) {
                    mFirebaseFavorite = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername).child(mIdPopuler);
                    mFirebaseFavorite.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                mImageFavorite.setImageResource(R.drawable.ic_favorite_red);
                            } else {
                                mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (mIdRestoran != null && mJenisMakanan != null) {
                    mFirebaseFavorite = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername).child(mIdRestoran);
                    mFirebaseFavorite.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                mImageFavorite.setImageResource(R.drawable.ic_favorite_red);
                            } else {
                                mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            // Menu populer
            if (mIdPopuler != null) {
                mFirebasePopuler = FirebaseDatabase.getInstance().getReference().child("populer").child(mIdPopuler);
                mFirebasePopuler.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mIdRestoranPopuler = Objects.requireNonNull(dataSnapshot.child("id_restoran").getValue()).toString();
                        mJenisMakananPopuler = Objects.requireNonNull(dataSnapshot.child("jenis_makanan").getValue()).toString();

                        mFirebasePopuler = FirebaseDatabase.getInstance().getReference().child("semua_makanan").child(mJenisMakananPopuler).child(mIdRestoranPopuler);
                        mFirebasePopuler.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mLat = Double.valueOf(Objects.requireNonNull(dataSnapshot.child("lokasi_lat").getValue()).toString());
                                mLng = Double.valueOf(Objects.requireNonNull(dataSnapshot.child("lokasi_lng").getValue()).toString());
                                mMenu = Objects.requireNonNull(dataSnapshot.child("menu").getValue()).toString();
                                mRestaurantImage = Objects.requireNonNull(dataSnapshot.child("gambar_restoran").getValue()).toString();
                                mRestaurantName = Objects.requireNonNull(dataSnapshot.child("nama_restoran").getValue()).toString();
                                mRestaurantThumbnail = Objects.requireNonNull(dataSnapshot.child("thumbnail_restoran").getValue()).toString();
                                mShortAddress = Objects.requireNonNull(dataSnapshot.child("alamat_singkat").getValue()).toString();

                                String mApproxPrice = Objects.requireNonNull(dataSnapshot.child("estimasi_harga").getValue()).toString() + " per orang";
                                String mFullAddress = Objects.requireNonNull(dataSnapshot.child("alamat_lengkap").getValue()).toString();
                                String mOpenDay = "Buka " + Objects.requireNonNull(dataSnapshot.child("hari_buka").getValue()).toString().toLowerCase();
                                String mOpenHour = "Mulai jam " + Objects.requireNonNull(dataSnapshot.child("jam_buka").getValue()).toString();
                                String mReview = Objects.requireNonNull(dataSnapshot.child("ulasan").getValue()).toString();
                                String mStar = Objects.requireNonNull(dataSnapshot.child("bintang").getValue()).toString();
                                String mStarReview = "Bintang " + mStar + " dari " + mReview + " ulasan";

                                mTextRestaurantName.setText(mRestaurantName);
                                mTextFullAddress.setText(mFullAddress);
                                mTextOpenDay.setText(mOpenDay);
                                mTextOpenHour.setText(mOpenHour);
                                mTextStarReview.setText(mStarReview);
                                mTextApproxPrice.setText(mApproxPrice);
                                Glide.with(FoodDetailActivity.this).load(mRestaurantImage).apply(mRequestOptionsRestaurant).into(mImageRestaurant);
                                Glide.with(FoodDetailActivity.this).load(mMenu).apply(mRequestOptionsMenu).into(mImageMenu);

                                mProgressBar.setVisibility(View.INVISIBLE);

                                // Activities
                                mImageFavorite.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (mUsername.isEmpty()) {
                                            Intent mGotoEmptyUser = new Intent(FoodDetailActivity.this, EmptyUserActivity.class);
                                            startActivity(mGotoEmptyUser);
                                        } else {
                                            mFirebasePopuler = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername).child(mIdRestoranPopuler);
                                            mFirebasePopuler.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        mFirebasePopuler.getRef().child("alamat_singkat").removeValue();
                                                        mFirebasePopuler.getRef().child("id_restoran").removeValue();
                                                        mFirebasePopuler.getRef().child("jenis_makanan").removeValue();
                                                        mFirebasePopuler.getRef().child("nama_restoran").removeValue();
                                                        mFirebasePopuler.getRef().child("thumbnail_restoran").removeValue();
                                                        mFirebasePopuler.getRef().child("waktu_akses").removeValue();

                                                        mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
                                                        Snackbar.make(mConstraintLayout, "Dihapus dari favorit", Snackbar.LENGTH_SHORT).show();
                                                    } else {
                                                        SimpleDateFormat mSDF = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                                                        String mDateTime = mSDF.format(new Date());

                                                        mFirebasePopuler.getRef().child("alamat_singkat").setValue(mShortAddress);
                                                        mFirebasePopuler.getRef().child("id_restoran").setValue(mIdRestoranPopuler);
                                                        mFirebasePopuler.getRef().child("jenis_makanan").setValue(mJenisMakananPopuler);
                                                        mFirebasePopuler.getRef().child("nama_restoran").setValue(mRestaurantName);
                                                        mFirebasePopuler.getRef().child("thumbnail_restoran").setValue(mRestaurantThumbnail);
                                                        mFirebasePopuler.getRef().child("waktu_akses").setValue(mDateTime);

                                                        mImageFavorite.setImageResource(R.drawable.ic_favorite_red);
                                                        Snackbar.make(mConstraintLayout, "Ditambah ke favorit", Snackbar.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });

                                mImageMenu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        MenuFragment mMenuFragment = new MenuFragment();
                                        FragmentManager mFragmentManager = getSupportFragmentManager();
                                        mFragmentManager.beginTransaction().replace(R.id.menu_container, mMenuFragment).addToBackStack(null).commit();

                                        Bundle mBundle = new Bundle();
                                        mBundle.putString("MenuArgs", mMenu);
                                        mMenuFragment.setArguments(mBundle);
                                    }
                                });

                                mButtonMaps.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent mGotoMaps = new Intent(FoodDetailActivity.this, MapsActivity.class);
                                        mGotoMaps.putExtra("LatArgs", mLat);
                                        mGotoMaps.putExtra("LngArgs", mLng);
                                        mGotoMaps.putExtra("NamaRestoranArgs", mRestaurantName);
                                        startActivity(mGotoMaps);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Menu semua makanan
            if (mIdRestoran != null && mJenisMakanan != null) {
                mFirebaseAllFood = FirebaseDatabase.getInstance().getReference().child("semua_makanan").child(mJenisMakanan).child(mIdRestoran);
                mFirebaseAllFood.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mLat = Double.valueOf(Objects.requireNonNull(dataSnapshot.child("lokasi_lat").getValue()).toString());
                        mLng = Double.valueOf(Objects.requireNonNull(dataSnapshot.child("lokasi_lng").getValue()).toString());
                        mIdRestoranSemuaMakanan = Objects.requireNonNull(dataSnapshot.child("id_restoran").getValue()).toString();
                        mJenisMakananSemuaMakanan = Objects.requireNonNull(dataSnapshot.child("jenis_makanan").getValue()).toString();
                        mMenu = Objects.requireNonNull(dataSnapshot.child("menu").getValue()).toString();
                        mRestaurantImage = Objects.requireNonNull(dataSnapshot.child("gambar_restoran").getValue()).toString();
                        mRestaurantName = Objects.requireNonNull(dataSnapshot.child("nama_restoran").getValue()).toString();
                        mRestaurantThumbnail = Objects.requireNonNull(dataSnapshot.child("thumbnail_restoran").getValue()).toString();
                        mShortAddress = Objects.requireNonNull(dataSnapshot.child("alamat_singkat").getValue()).toString();

                        String mApproxPrice = Objects.requireNonNull(dataSnapshot.child("estimasi_harga").getValue()).toString() + " per orang";
                        String mFullAddress = Objects.requireNonNull(dataSnapshot.child("alamat_lengkap").getValue()).toString();
                        String mOpenDay = "Buka " + Objects.requireNonNull(dataSnapshot.child("hari_buka").getValue()).toString().toLowerCase();
                        String mOpenHour = "Mulai jam " + Objects.requireNonNull(dataSnapshot.child("jam_buka").getValue()).toString();
                        String mReview = Objects.requireNonNull(dataSnapshot.child("ulasan").getValue()).toString();
                        String mStar = Objects.requireNonNull(dataSnapshot.child("bintang").getValue()).toString();
                        String mStarReview = "Bintang " + mStar + " dari " + mReview + " ulasan";

                        mTextRestaurantName.setText(mRestaurantName);
                        mTextFullAddress.setText(mFullAddress);
                        mTextOpenDay.setText(mOpenDay);
                        mTextOpenHour.setText(mOpenHour);
                        mTextStarReview.setText(mStarReview);
                        mTextApproxPrice.setText(mApproxPrice);
                        Glide.with(FoodDetailActivity.this).load(mRestaurantImage).apply(mRequestOptionsRestaurant).into(mImageRestaurant);
                        Glide.with(FoodDetailActivity.this).load(mMenu).apply(mRequestOptionsMenu).into(mImageMenu);

                        mProgressBar.setVisibility(View.INVISIBLE);

                        // Activities
                        mImageFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mUsername.isEmpty()) {
                                    Intent mGotoEmptyUser = new Intent(FoodDetailActivity.this, EmptyUserActivity.class);
                                    startActivity(mGotoEmptyUser);
                                } else {
                                    mFirebaseAllFood = FirebaseDatabase.getInstance().getReference().child("user_favorite").child(mUsername).child(mIdRestoranSemuaMakanan);
                                    mFirebaseAllFood.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                mFirebaseAllFood.getRef().child("alamat_singkat").removeValue();
                                                mFirebaseAllFood.getRef().child("id_restoran").removeValue();
                                                mFirebaseAllFood.getRef().child("jenis_makanan").removeValue();
                                                mFirebaseAllFood.getRef().child("nama_restoran").removeValue();
                                                mFirebaseAllFood.getRef().child("thumbnail_restoran").removeValue();
                                                mFirebaseAllFood.getRef().child("waktu_akses").removeValue();

                                                mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
                                                Snackbar.make(mConstraintLayout, "Dihapus dari favorit", Snackbar.LENGTH_SHORT).show();
                                            } else {
                                                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                                                String mDateTime = mSimpleDateFormat.format(new Date());

                                                mFirebaseAllFood.getRef().child("alamat_singkat").setValue(mShortAddress);
                                                mFirebaseAllFood.getRef().child("id_restoran").setValue(mIdRestoranSemuaMakanan);
                                                mFirebaseAllFood.getRef().child("jenis_makanan").setValue(mJenisMakananSemuaMakanan);
                                                mFirebaseAllFood.getRef().child("nama_restoran").setValue(mRestaurantName);
                                                mFirebaseAllFood.getRef().child("thumbnail_restoran").setValue(mRestaurantThumbnail);
                                                mFirebaseAllFood.getRef().child("waktu_akses").setValue(mDateTime);

                                                mImageFavorite.setImageResource(R.drawable.ic_favorite_red);
                                                Snackbar.make(mConstraintLayout, "Ditambah ke favorit", Snackbar.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });

                        mImageMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MenuFragment mMenuFragment = new MenuFragment();
                                FragmentManager mFragmentManager = getSupportFragmentManager();
                                mFragmentManager.beginTransaction().replace(R.id.menu_container, mMenuFragment).addToBackStack(null).commit();

                                Bundle mBundle = new Bundle();
                                mBundle.putString("MenuArgs", mMenu);
                                mMenuFragment.setArguments(mBundle);
                            }
                        });

                        mButtonMaps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent mGotoMaps = new Intent(FoodDetailActivity.this, MapsActivity.class);
                                mGotoMaps.putExtra("LatArgs", mLat);
                                mGotoMaps.putExtra("LngArgs", mLng);
                                mGotoMaps.putExtra("NamaRestoranArgs", mRestaurantName);
                                startActivity(mGotoMaps);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        // Activities
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // Method mendapatkan data user aktif
    private void getUsername() {
        String USERNAME_KEY = "username_key";
        String UsernameKeyArgs = "";
        SharedPreferences mSharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString(UsernameKeyArgs, "");
    }
}
