package com.rgxcp.jakfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmptyUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_user);

        // Deklarasi dan assign variable lokal
        Button mButtonSignIn = findViewById(R.id.btn_aeu_sign_in);
        Button mButtonSignUp = findViewById(R.id.btn_aeu_sign_up);

        // Activities
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoSignIn = new Intent(EmptyUserActivity.this, SignInActivity.class);
                startActivity(mGotoSignIn);
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoSignUp = new Intent(EmptyUserActivity.this, SignUpActivity.class);
                startActivity(mGotoSignUp);
            }
        });
    }
}
