package com.rgxcp.jakfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    // Deklarasi variable global
    private Button mButtonSignIn;
    private EditText mInputUsername, mInputPassword;
    private DatabaseReference mDatabaseReference;

    // Validasi user
    private String USERNAME_KEY = "username_key";
    private String UsernameKeyArgs = "";
    private String mUsername, mPassword, mPasswordFirebase;

    // Validasi state
    private String mLoadingState = "TUNGGU...";
    private String mNormalState = "MASUK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Assign variable global
        mButtonSignIn = findViewById(R.id.btn_asi_sign_in);
        mInputUsername = findViewById(R.id.edt_asi_username);
        mInputPassword = findViewById(R.id.edt_asi_password);

        // Activities
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengubah state menjadi loading
                mButtonSignIn.setEnabled(false);
                mButtonSignIn.setText(mLoadingState);

                mUsername = mInputUsername.getText().toString();
                mPassword = mInputPassword.getText().toString();

                // Validasi apakah semua data sudah diisi
                if (mUsername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username harus diisi!", Toast.LENGTH_SHORT).show();
                    mButtonSignIn.setEnabled(true);
                    mButtonSignIn.setText(mNormalState);
                } else if (mPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kata sandi harus diisi!", Toast.LENGTH_SHORT).show();
                    mButtonSignIn.setEnabled(true);
                    mButtonSignIn.setText(mNormalState);
                } else {
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(mUsername);
                    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Validasi apakah username terdaftar
                            if (dataSnapshot.exists()) {
                                mPasswordFirebase = Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString();
                                // Validasi apakah password benar
                                if (mPassword.equals(mPasswordFirebase)) {
                                    // Menyimpan username ke storage lokal
                                    SharedPreferences mSharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                                    mEditor.putString(UsernameKeyArgs, mUsername);
                                    mEditor.apply();

                                    Intent mGotoMain = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(mGotoMain);
                                    finishAffinity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Kata sandi salah.", Toast.LENGTH_SHORT).show();
                                    mButtonSignIn.setEnabled(true);
                                    mButtonSignIn.setText(mNormalState);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Username tidak terdaftar.", Toast.LENGTH_SHORT).show();
                                mButtonSignIn.setEnabled(true);
                                mButtonSignIn.setText(mNormalState);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
