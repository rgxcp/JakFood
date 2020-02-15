package com.rgxcp.jakfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        // Deklarasi dan assign variable lokal
        ImageView mImagePopuler1 = mView.findViewById(R.id.img_fh_populer_1);
        ImageView mImagePopuler2 = mView.findViewById(R.id.img_fh_populer_2);
        ImageView mImagePopuler3 = mView.findViewById(R.id.img_fh_populer_3);
        ImageView mImageAyamBakar = mView.findViewById(R.id.img_fh_ayam_bakar);
        ImageView mImageBakso = mView.findViewById(R.id.img_fh_bakso);
        ImageView mImageBuburAyam = mView.findViewById(R.id.img_fh_bubur_ayam);
        ImageView mImageGadoGado = mView.findViewById(R.id.img_fh_gado_gado);
        ImageView mImageKepiting = mView.findViewById(R.id.img_fh_kepiting);
        ImageView mImageMartabak = mView.findViewById(R.id.img_fh_martabak);
        ImageView mImageMieAyam = mView.findViewById(R.id.img_fh_mie_ayam);
        ImageView mImageNasiGoreng = mView.findViewById(R.id.img_fh_nasi_goreng);
        ImageView mImageNasiUduk = mView.findViewById(R.id.img_fh_nasi_uduk);
        ImageView mImageRotiBakar = mView.findViewById(R.id.img_fh_roti_bakar);
        ImageView mImageSate = mView.findViewById(R.id.img_fh_sate);
        ImageView mImageSotoBetawi = mView.findViewById(R.id.img_fh_soto_betawi);

        // Activities untuk populer
        mImagePopuler1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodDetail = new Intent(getActivity(), FoodDetailActivity.class);
                mGotoFoodDetail.putExtra("IdPopulerArgs", "populer_1");
                startActivity(mGotoFoodDetail);
            }
        });

        mImagePopuler2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodDetail = new Intent(getActivity(), FoodDetailActivity.class);
                mGotoFoodDetail.putExtra("IdPopulerArgs", "populer_2");
                startActivity(mGotoFoodDetail);
            }
        });

        mImagePopuler3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodDetail = new Intent(getActivity(), FoodDetailActivity.class);
                mGotoFoodDetail.putExtra("IdPopulerArgs", "populer_3");
                startActivity(mGotoFoodDetail);
            }
        });

        // Activities untuk semua makanan
        mImageAyamBakar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "ayam_bakar");
                mGotoFoodList.putExtra("NamaMakananArgs", "Ayam Bakar");
                mGotoFoodList.putExtra("URLArgs", "https://developers.zomato.com/api/v2.1/search?entity_id=74&entity_type=city&q=ayam%20bakar&sort=rating&order=desc");
                startActivity(mGotoFoodList);
            }
        });

        mImageBakso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "bakso");
                mGotoFoodList.putExtra("NamaMakananArgs", "Bakso");
                startActivity(mGotoFoodList);
            }
        });

        mImageBuburAyam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "bubur_ayam");
                mGotoFoodList.putExtra("NamaMakananArgs", "Bubur Ayam");
                startActivity(mGotoFoodList);
            }
        });

        mImageGadoGado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "gado_gado");
                mGotoFoodList.putExtra("NamaMakananArgs", "Gado-Gado");
                startActivity(mGotoFoodList);
            }
        });

        mImageKepiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "kepiting");
                mGotoFoodList.putExtra("NamaMakananArgs", "Kepiting");
                startActivity(mGotoFoodList);
            }
        });

        mImageMartabak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "martabak");
                mGotoFoodList.putExtra("NamaMakananArgs", "Martabak");
                startActivity(mGotoFoodList);
            }
        });

        mImageMieAyam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "mie_ayam");
                mGotoFoodList.putExtra("NamaMakananArgs", "Mie Ayam");
                startActivity(mGotoFoodList);
            }
        });

        mImageNasiGoreng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "nasi_goreng");
                mGotoFoodList.putExtra("NamaMakananArgs", "Nasi Goreng");
                startActivity(mGotoFoodList);
            }
        });

        mImageNasiUduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "nasi_uduk");
                mGotoFoodList.putExtra("NamaMakananArgs", "Nasi Uduk");
                startActivity(mGotoFoodList);
            }
        });

        mImageRotiBakar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "roti_bakar");
                mGotoFoodList.putExtra("NamaMakananArgs", "Roti Bakar");
                startActivity(mGotoFoodList);
            }
        });

        mImageSate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "sate");
                mGotoFoodList.putExtra("NamaMakananArgs", "Sate");
                startActivity(mGotoFoodList);
            }
        });

        mImageSotoBetawi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoFoodList = new Intent(getActivity(), FoodListActivity.class);
                mGotoFoodList.putExtra("JenisMakananArgs", "soto_betawi");
                mGotoFoodList.putExtra("NamaMakananArgs", "Soto Betawi");
                startActivity(mGotoFoodList);
            }
        });

        // Menampilkan layout
        return mView;
    }
}
