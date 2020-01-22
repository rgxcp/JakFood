package com.rgxcp.jakfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
import java.util.Objects;

public class GetStartedAdapter extends PagerAdapter {

    // Setup constructor
    private Context mContext;
    private List<GetStarted> mList;

    // Constructor
    GetStartedAdapter(Context context, List<GetStarted> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Setup layout inflater
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Generate layout
        View mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.viewpager_get_started, container, false);

        // Deklarasi dan assign variable lokal
        ImageView mImageBackground = mView.findViewById(R.id.img_vgs_background);
        TextView mTextHeading = mView.findViewById(R.id.txt_vgs_heading);
        TextView mTextDescription = mView.findViewById(R.id.txt_vgs_description);

        // Mengisi view pager
        mImageBackground.setImageResource(mList.get(position).getBackground());
        mTextHeading.setText(mList.get(position).getHeading());
        mTextDescription.setText(mList.get(position).getDescription());

        // Mengisi layout
        container.addView(mView);

        // Menampilkan layout
        return mView;
    }

    // Methods
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
