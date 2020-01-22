package com.rgxcp.jakfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_about, container, false);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = mView.findViewById(R.id.btn_fa_back);

        // Activities
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager.popBackStack();
                }
            }
        });

        // Menampilkan layout
        return mView;
    }
}
