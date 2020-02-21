package com.rgxcp.jakfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ZomatoFoodDetailActivity extends AppCompatActivity {

    // Deklarasi variable global
    private ConstraintLayout mConstraintLayout;
    private Double mLat, mLng;
    private ImageView mImageFavorite, mImageRestaurant;
    private String mName, mRestaurantId, mShortAddress, mThumbnail;
    private TextView mTextName, mTextFullAddress, mTextTiming, mTextStarReview, mTextApproxPrice;
    private DatabaseReference mFirebase;

    // Setup recycler view
    private List<ZomatoPhoto> mListPhoto;
    private List<ZomatoReview> mListReview;
    private RecyclerView mRecyclerViewPhoto;
    private RecyclerView mRecyclerViewReview;
    private ZomatoPhotoAdapter mZomatoPhotoAdapter;
    private ZomatoReviewAdapter mZomatoReviewAdapter;

    // Validasi user
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zomato_food_detail);

        // Menjalankan method
        getUsername();

        // Assign variable global
        mConstraintLayout = findViewById(R.id.cnt_azfd_layout);
        mImageFavorite = findViewById(R.id.img_azfd_favorite);
        mImageRestaurant = findViewById(R.id.img_azfd_restaurant_image);
        mTextName = findViewById(R.id.txt_azfd_restaurant_name);
        mTextFullAddress = findViewById(R.id.txt_azfd_full_address);
        mTextTiming = findViewById(R.id.txt_azfd_open_day_hour);
        mTextStarReview = findViewById(R.id.txt_azfd_star_review);
        mTextApproxPrice = findViewById(R.id.txt_azfd_approx_price);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = findViewById(R.id.btn_azfd_back);
        Button mButtonMaps = findViewById(R.id.btn_azfd_maps);
        ProgressBar mProgressBar = findViewById(R.id.pbr_azfd_loading);

        // Recycler view
        mListPhoto = new ArrayList<>();
        mListReview = new ArrayList<>();
        mRecyclerViewPhoto = findViewById(R.id.rcv_azfd_photo);
        mRecyclerViewReview = findViewById(R.id.rcv_azfd_review);

        // Setup layout manager
        LinearLayoutManager mLLMPhoto = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mLLMReview = new LinearLayoutManager(this);
        mRecyclerViewPhoto.setLayoutManager(mLLMPhoto);
        mRecyclerViewReview.setLayoutManager(mLLMReview);

        // Menangkap data dari Intent
        Bundle mBundle = getIntent().getExtras();
        mRestaurantId = Objects.requireNonNull(mBundle).getString("RestaurantIdArgs");

        // Handler favorite
        if (mUsername.isEmpty()) {
            mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
        } else {
            mFirebase = FirebaseDatabase.getInstance().getReference().child("user_favorite_zomato").child(mUsername).child(mRestaurantId);
            mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
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

        // Parsing JSON
        parseJSON(mRestaurantId);
        parseJSONReview(mRestaurantId);

        // Progress bar
        mProgressBar.setVisibility(View.INVISIBLE);

        // Activities
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mButtonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoMaps = new Intent(ZomatoFoodDetailActivity.this, MapsActivity.class);
                mGotoMaps.putExtra("LatArgs", mLat);
                mGotoMaps.putExtra("LngArgs", mLng);
                mGotoMaps.putExtra("NamaRestoranArgs", mName);
                startActivity(mGotoMaps);
            }
        });

        mImageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsername.isEmpty()) {
                    Intent mGotoEmptyUser = new Intent(ZomatoFoodDetailActivity.this, EmptyUserActivity.class);
                    startActivity(mGotoEmptyUser);
                } else {
                    mFirebase = FirebaseDatabase.getInstance().getReference().child("user_favorite_zomato").child(mUsername).child(mRestaurantId);
                    mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                mFirebase.getRef().child("restaurant_id").removeValue();
                                mFirebase.getRef().child("restaurant_name").removeValue();
                                mFirebase.getRef().child("restaurant_short_address").removeValue();
                                mFirebase.getRef().child("restaurant_thumbnail").removeValue();
                                mFirebase.getRef().child("access_time").removeValue();

                                mImageFavorite.setImageResource(R.drawable.ic_favorite_gray);
                                Snackbar.make(mConstraintLayout, "Dihapus dari favorit", Snackbar.LENGTH_SHORT).show();
                            } else {
                                SimpleDateFormat mSDF = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                                String mDateTime = mSDF.format(new Date());

                                mFirebase.getRef().child("restaurant_id").setValue(mRestaurantId);
                                mFirebase.getRef().child("restaurant_name").setValue(mName);
                                mFirebase.getRef().child("restaurant_short_address").setValue(mShortAddress);
                                mFirebase.getRef().child("restaurant_thumbnail").setValue(mThumbnail);
                                mFirebase.getRef().child("access_time").setValue(mDateTime);

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
    }

    private void parseJSON(String mRestaurantId) {
        // Request
        String mRequestURL = "https://developers.zomato.com/api/v2.1/restaurant?res_id=" + mRestaurantId;

        // Object request
        JsonObjectRequest mJSONObjectRequest = new JsonObjectRequest(Request.Method.GET, mRequestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Mendapatkan nama JSON object
                    JSONObject mLocationObject = response.getJSONObject("location");
                    JSONObject mUserRatingObject = response.getJSONObject("user_rating");

                    mLat = Double.valueOf(mLocationObject.getString("latitude"));
                    mLng = Double.valueOf(mLocationObject.getString("longitude"));
                    mName = response.getString("name");
                    mShortAddress = mLocationObject.getString("locality_verbose");
                    mThumbnail = response.getString("thumb");

                    String mStarReview = "Bintang " + mUserRatingObject.getString("aggregate_rating") + " dari " + response.getInt("all_reviews_count") + " ulasan";
                    String mApproxPrice = String.valueOf(response.getInt("average_cost_for_two") / response.getInt("price_range"));
                    String mRestaurantImage = mThumbnail.substring(0, mThumbnail.length() - 50);
                    RequestOptions mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_placeholder);

                    // Validasi harga
                    if (mApproxPrice.length() == 5) {
                        String mPrice = "Rp " + mApproxPrice.substring(0, 2) + "." + mApproxPrice.substring(2, 5) + " per orang";
                        mTextApproxPrice.setText(mPrice);
                    } else if (mApproxPrice.length() == 6) {
                        String mPrice = "Rp " + mApproxPrice.substring(0, 3) + "." + mApproxPrice.substring(3, 6) + " per orang";
                        mTextApproxPrice.setText(mPrice);
                    } else {
                        String mPrice = "Rp " + mApproxPrice + " per orang";
                        mTextApproxPrice.setText(mPrice);
                    }

                    mTextName.setText(mName);
                    mTextFullAddress.setText(mLocationObject.getString("address"));
                    mTextTiming.setText(response.getString("timings"));
                    mTextStarReview.setText(mStarReview);
                    Glide.with(ZomatoFoodDetailActivity.this).load(mRestaurantImage).apply(mRequestOptions).into(mImageRestaurant);

                    // Mendapatkan nama JSON array
                    JSONArray mJSONArray = response.getJSONArray("photos");

                    // Looping
                    for (int position = 0; position < mJSONArray.length(); position++) {
                        // Mendapatkan nama JSON object
                        JSONObject mJSONObject = mJSONArray.getJSONObject(position);
                        JSONObject mPhotoObject = mJSONObject.getJSONObject("photo");

                        // Mengirim data ke Setter
                        ZomatoPhoto mZomatoPhoto = new ZomatoPhoto();
                        mZomatoPhoto.setThumb_url(mPhotoObject.getString("thumb_url"));
                        mZomatoPhoto.setUrl(mPhotoObject.getString("url"));

                        // Menambahkan semua data ke model
                        mListPhoto.add(mZomatoPhoto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    // Setup adapter
                    mZomatoPhotoAdapter = new ZomatoPhotoAdapter(ZomatoFoodDetailActivity.this, mListPhoto);
                    mRecyclerViewPhoto.setAdapter(mZomatoPhotoAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> mHeader = new HashMap<>();
                mHeader.put("Accept", "application/json");
                mHeader.put("user-key", getString(R.string.zomato_key));
                return mHeader;
            }
        };

        // Request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(mJSONObjectRequest);
    }

    private void parseJSONReview(String mRestaurantId) {
        // Request
        String mRequestURL = "https://developers.zomato.com/api/v2.1/reviews?res_id=" + mRestaurantId;

        // Object request
        JsonObjectRequest mJSONObjectRequest = new JsonObjectRequest(Request.Method.GET, mRequestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Mendapatkan nama JSON array
                    JSONArray mJSONArray = response.getJSONArray("user_reviews");

                    // Looping
                    for (int position = 0; position < mJSONArray.length(); position++) {
                        // Mendapatkan nama JSON object
                        JSONObject mJSONObject = mJSONArray.getJSONObject(position);
                        JSONObject mReviewObject = mJSONObject.getJSONObject("review");
                        JSONObject mUserObject = mReviewObject.getJSONObject("user");

                        // Mengirim data ke Setter
                        ZomatoReview mZomatoReview = new ZomatoReview();
                        mZomatoReview.setRating(mReviewObject.getInt("rating"));
                        mZomatoReview.setReview_text(mReviewObject.getString("review_text"));
                        mZomatoReview.setReview_time_friendly(mReviewObject.getString("review_time_friendly"));
                        mZomatoReview.setName(mUserObject.getString("name"));
                        mZomatoReview.setProfile_image(mUserObject.getString("profile_image"));

                        // Menambahkan semua data ke model
                        mListReview.add(mZomatoReview);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    // Setup adapter
                    mZomatoReviewAdapter = new ZomatoReviewAdapter(ZomatoFoodDetailActivity.this, mListReview);
                    mRecyclerViewReview.setAdapter(mZomatoReviewAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> mHeader = new HashMap<>();
                mHeader.put("Accept", "application/json");
                mHeader.put("user-key", getString(R.string.zomato_key));
                return mHeader;
            }
        };

        // Request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(mJSONObjectRequest);
    }

    private void getUsername() {
        String USERNAME_KEY = "username_key";
        String UsernameKeyArgs = "";
        SharedPreferences mSharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString(UsernameKeyArgs, "");
    }
}
