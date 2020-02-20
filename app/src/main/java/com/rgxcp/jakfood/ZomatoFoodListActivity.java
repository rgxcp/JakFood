package com.rgxcp.jakfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ZomatoFoodListActivity extends AppCompatActivity {

    // Deklarasi variable global
    private ProgressBar mProgressBar;

    // Setup recycler view
    private List<ZomatoFoodList> mList;
    private RecyclerView mRecyclerView;
    private ZomatoFoodListAdapter mZomatoFoodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zomato_food_list);

        // Assign variable global
        mProgressBar = findViewById(R.id.pbr_azfl_loading);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = findViewById(R.id.btn_azfl_back);
        SearchView mSearchView = findViewById(R.id.srv_azfl_food);
        TextView mTextCategory = findViewById(R.id.txt_azfl_category);

        // Recycler view
        mList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rcv_azfl_food_list);

        // Setup layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Menangkap data dari Intent
        Bundle mBundle = getIntent().getExtras();
        String mCategory = Objects.requireNonNull(mBundle).getString("CategoryArgs");
        String mRequestURL = Objects.requireNonNull(mBundle).getString("RequestURLArgs");

        // Setup hint search view
        mTextCategory.setText(mCategory);
        mSearchView.setQueryHint("Cari restoran");

        // Parsing JSON
        parseJSON(mRequestURL);

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
                mZomatoFoodListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void parseJSON(String mRequestURL) {
        // Object request
        JsonObjectRequest mJSONObjectRequest = new JsonObjectRequest(Request.Method.GET, mRequestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Mendapatkan nama JSON array
                    JSONArray mJSONArray = response.getJSONArray("restaurants");

                    // Looping
                    for (int position = 0; position < mJSONArray.length(); position++) {
                        // Mendapatkan nama JSON object
                        JSONObject mJSONObject = mJSONArray.getJSONObject(position);
                        JSONObject mRestaurantObject = mJSONObject.getJSONObject("restaurant");
                        JSONObject mLocationObject = mRestaurantObject.getJSONObject("location");
                        JSONObject mUserRatingObject = mRestaurantObject.getJSONObject("user_rating");

                        // Mengirim data ke Setter
                        ZomatoFoodList mZomatoFoodList = new ZomatoFoodList();
                        mZomatoFoodList.setId(mRestaurantObject.getString("id"));
                        mZomatoFoodList.setName(mRestaurantObject.getString("name"));
                        mZomatoFoodList.setThumb(mRestaurantObject.getString("thumb"));
                        mZomatoFoodList.setLocality_verbose(mLocationObject.getString("locality_verbose"));
                        mZomatoFoodList.setAggregate_rating(mUserRatingObject.getString("aggregate_rating"));

                        // Menambahkan semua data ke model
                        mList.add(mZomatoFoodList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    // Setup adapter
                    mZomatoFoodListAdapter = new ZomatoFoodListAdapter(ZomatoFoodListActivity.this, mList);
                    mRecyclerView.setAdapter(mZomatoFoodListAdapter);

                    // Progress bar
                    mProgressBar.setVisibility(View.INVISIBLE);
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
}
