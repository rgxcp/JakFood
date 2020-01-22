package com.rgxcp.jakfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GetStartedActivity extends AppCompatActivity {

    // Deklarasi variable global
    private Integer mPosition;
    private List<GetStarted> mList;
    private TextView mTextBack, mTextNext, mTextFinish;
    private ViewPager mViewPager;

    // Validasi get started
    private String GET_STARTED_KEY = "get_started_key";
    private String mIsOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // Menjalankan method
        isGetStartedOpened();

        // Assign variable global
        mTextBack = findViewById(R.id.txt_ags_back);
        mTextNext = findViewById(R.id.txt_ags_next);
        mTextFinish = findViewById(R.id.txt_ags_finish);
        mViewPager = findViewById(R.id.viewpager_container);

        // Deklarasi dan assign variable lokal
        TabLayout mDots = findViewById(R.id.dots_container);

        // Setup isi view pager
        mList = new ArrayList<>();
        mList.add(new GetStarted(R.drawable.vc_get_started_1, "Makan apa hari ini?", "Temukan makananmu dalam berbagai macam jenis makanan yang tersedia dalam JakFood."));
        mList.add(new GetStarted(R.drawable.vc_get_started_2, "Pilih restoran mana?", "Temukan restoran yang ingin kamu kunjungi berdasarkan lokasi terdekat, bintang, dan ulasan."));
        mList.add(new GetStarted(R.drawable.vc_get_started_3, "Detail makanan", "Kamu bisa melihat semua detail mulai dari lokasi, harga, sampai menu yang tersedia dalam detail."));
        mList.add(new GetStarted(R.drawable.vc_get_started_4, "Tambah ke favorite!", "Suka dengan restoran tersebut? Tambahkan ke favorite untuk mempermudah jika ingin kesana lagi."));
        mList.add(new GetStarted(R.drawable.vc_get_started_5, "Masih bingung?", "Klik menu populer pada tampilan awal. Ada tiga restoran favorite untukmu yang diubah perminggu."));

        // Setup adapter view pager
        GetStartedAdapter mGetStartedAdapter = new GetStartedAdapter(this, mList);
        mViewPager.setAdapter(mGetStartedAdapter);

        // Handler posisi
        mDots.setupWithViewPager(mViewPager);
        mDots.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mTextBack.setVisibility(View.INVISIBLE);
                    mTextNext.setVisibility(View.VISIBLE);
                    mTextFinish.setVisibility(View.INVISIBLE);
                } else if (tab.getPosition() == mList.size()-1) {
                    mTextBack.setVisibility(View.INVISIBLE);
                    mTextNext.setVisibility(View.INVISIBLE);
                    mTextFinish.setVisibility(View.VISIBLE);
                } else {
                    mTextBack.setVisibility(View.VISIBLE);
                    mTextNext.setVisibility(View.VISIBLE);
                    mTextFinish.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // null
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // null
            }
        });

        // Activities
        mTextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition = mViewPager.getCurrentItem();
                if (mPosition < mList.size()) {
                    mPosition--;
                    mViewPager.setCurrentItem(mPosition);
                }
            }
        });

        mTextNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition = mViewPager.getCurrentItem();
                if (mPosition < mList.size()) {
                    mPosition++;
                    mViewPager.setCurrentItem(mPosition);
                }
            }
        });

        mTextFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsOpened.equals("true")) {
                    onBackPressed();
                } else {
                    SharedPreferences mSharedPreferences = getSharedPreferences(GET_STARTED_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                    mEditor.putString(mIsOpened, "true");
                    mEditor.apply();

                    Intent mGotoMain = new Intent(GetStartedActivity.this, MainActivity.class);
                    startActivity(mGotoMain);
                    finish();
                }
            }
        });
    }

    // Method mengecek apakah get started sudah dibuka
    private void isGetStartedOpened() {
        String GET_STARTED_KEY = "get_started_key";
        String GetStartedKeyArgs = "";
        SharedPreferences mSharedPreferences = getSharedPreferences(GET_STARTED_KEY, Context.MODE_PRIVATE);
        mIsOpened = mSharedPreferences.getString(GetStartedKeyArgs, "");
    }
}
