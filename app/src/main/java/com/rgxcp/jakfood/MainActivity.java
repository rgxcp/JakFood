package com.rgxcp.jakfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000;
    private static long TIME_BACK_PRESSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deklarasi dan assign variable lokal
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigation_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener);

        // Menjalankan home fragment secara otomatis
        HomeFragment mHomeFragment = new HomeFragment();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, mHomeFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (TIME_INTERVAL + TIME_BACK_PRESSED > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        TIME_BACK_PRESSED = System.currentTimeMillis();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment mSelectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.home:
                    mSelectedFragment = new HomeFragment();
                    break;
                case R.id.favorite:
                    mSelectedFragment = new FavoriteFragment();
                    break;
                case R.id.profile:
                    mSelectedFragment = new ProfileFragment();
                    break;
            }

            if (mSelectedFragment != null) {
                FragmentManager mFragmentManager = getSupportFragmentManager();
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, mSelectedFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }

            return true;
        }
    };
}
