package com.rgxcp.jakfood;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = findViewById(R.id.btn_am_back);

        // Setup support map fragment
        SupportMapFragment mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps_container);
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(this);
        }

        // Activities
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Menangkap data dari Intent
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            double mLat = mBundle.getDouble("LatArgs");
            double mLng = mBundle.getDouble("LngArgs");
            String mNamaRestoran = mBundle.getString("NamaRestoranArgs");

            LatLng mLatLng = new LatLng(mLat, mLng);

            // Menambahkan marker lokasi
            MarkerOptions mMarkerOptions = new MarkerOptions();
            mMarkerOptions.position(mLatLng).title(mNamaRestoran);
            googleMap.addMarker(mMarkerOptions);

            // Membuat zoom lokasi
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 17.0f));
        }
    }
}
