package com.rgxcp.jakfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EmptyUserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_empty_user, container, false);

        // Deklarasi dan assign variable lokal
        Button mButtonSignIn = mView.findViewById(R.id.btn_feu_sign_in);
        Button mButtonSignUp = mView.findViewById(R.id.btn_feu_sign_up);

        // Activities
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoSignIn = new Intent(getActivity(), SignInActivity.class);
                startActivity(mGotoSignIn);
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoSignUp = new Intent(getActivity(), SignUpActivity.class);
                startActivity(mGotoSignUp);
            }
        });

        // Menampilkan layout
        return mView;
    }
}
