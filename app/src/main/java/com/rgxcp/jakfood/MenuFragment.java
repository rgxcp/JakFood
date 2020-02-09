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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_menu, container, false);

        // Deklarasi dan assign variable lokal
        Button mButtonBack = mView.findViewById(R.id.btn_fm_back);
        PhotoView mImageMenu = mView.findViewById(R.id.img_fm_menu);
        RequestOptions mRequestOptions = new RequestOptions().placeholder(R.color.graySecondary).error(R.color.graySecondary);

        // Menangkap data dari Fragment
        if (getArguments() != null) {
            String mMenu = getArguments().getString("MenuArgs");
            Glide.with(this).load(mMenu).apply(mRequestOptions).into(mImageMenu);
        }

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
