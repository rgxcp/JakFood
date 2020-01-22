package com.rgxcp.jakfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SignUpSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_success);

        // Membuat window transparent
        Window mWindow = getWindow();
        mWindow.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Deklarasi dan assign variable lokal
        Button mButtonStart = findViewById(R.id.btn_asus_start);
        ImageView mImageSuccess = findViewById(R.id.img_asus_success);

        // Deklarasi dan assign variable lokal untuk animasi
        Animation mSuccess = AnimationUtils.loadAnimation(this, R.anim.anim_success);

        // Menjalankan animasi
        mImageSuccess.startAnimation(mSuccess);

        // Activities
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mGotoMain = new Intent(SignUpSuccessActivity.this, MainActivity.class);
                startActivity(mGotoMain);
                finishAffinity();
            }
        });
    }
}
