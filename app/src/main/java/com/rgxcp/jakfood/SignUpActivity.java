package com.rgxcp.jakfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    // Deklarasi variable global
    private Button mButtonSignUp;
    private EditText mInputFullName, mInputEmail, mInputUsername, mInputPassword;
    private DatabaseReference mDatabaseReference;

    // Validasi user
    private String USERNAME_KEY = "username_key";
    private String UsernameKeyArgs = "";
    private String mFullName, mEmail, mUsername, mPassword;

    // Validasi state
    private String mLoadingState = "TUNGGU...";
    private String mNormalState = "DAFTAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Assign variable global
        mButtonSignUp = findViewById(R.id.btn_asu_sign_up);
        mInputFullName = findViewById(R.id.edt_asu_full_name);
        mInputEmail = findViewById(R.id.edt_asu_email);
        mInputUsername = findViewById(R.id.edt_asu_username);
        mInputPassword = findViewById(R.id.edt_asu_password);

        // Activities
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengubah state menjadi loading
                mButtonSignUp.setEnabled(false);
                mButtonSignUp.setText(mLoadingState);

                mFullName = mInputFullName.getText().toString();
                mEmail = mInputEmail.getText().toString();
                mUsername = mInputUsername.getText().toString();
                mPassword = mInputPassword.getText().toString();

                // Validasi apakah semua data sudah diisi
                if (mFullName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama lengkap harus diisi!", Toast.LENGTH_SHORT).show();
                    mButtonSignUp.setEnabled(true);
                    mButtonSignUp.setText(mNormalState);
                } else if (mEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Alamat email harus diisi!", Toast.LENGTH_SHORT).show();
                    mButtonSignUp.setEnabled(true);
                    mButtonSignUp.setText(mNormalState);
                } else if (mUsername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username harus diisi!", Toast.LENGTH_SHORT).show();
                    mButtonSignUp.setEnabled(true);
                    mButtonSignUp.setText(mNormalState);
                } else if (mPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kata sandi harus diisi!", Toast.LENGTH_SHORT).show();
                    mButtonSignUp.setEnabled(true);
                    mButtonSignUp.setText(mNormalState);
                } else {
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(mUsername);
                    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Validasi apakah username sudah dipakai
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Username sudah dipakai.", Toast.LENGTH_SHORT).show();
                                mButtonSignUp.setEnabled(true);
                                mButtonSignUp.setText(mNormalState);
                            } else {
                                // Menyimpan data ke storage Firebase
                                dataSnapshot.getRef().child("full_name").setValue(mInputFullName.getText().toString());
                                dataSnapshot.getRef().child("email").setValue(mInputEmail.getText().toString());
                                dataSnapshot.getRef().child("username").setValue(mInputUsername.getText().toString());
                                dataSnapshot.getRef().child("password").setValue(mInputPassword.getText().toString());

                                // Menyimpan username ke storage lokal
                                SharedPreferences mSharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                                mEditor.putString(UsernameKeyArgs, mUsername);
                                mEditor.apply();

                                Intent mGotoSignUpSuccess = new Intent(SignUpActivity.this, SignUpSuccessActivity.class);
                                startActivity(mGotoSignUpSuccess);
                                finishAffinity();
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
