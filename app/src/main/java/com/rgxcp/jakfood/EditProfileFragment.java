package com.rgxcp.jakfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    // Deklarasi variable global
    private Button mButtonSave;
    private EditText mEditFullName, mEditEmail, mEditUsername, mEditPassword;
    private DatabaseReference mDatabaseReference;

    // Validasi user
    private String mUsername;
    private String mFullName, mEmail, mPassword;

    // Validasi state
    private String mLoadingState = "TUNGGU...";
    private String mNormalState = "SIMPAN";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Generate layout
        View mView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Menjalankan method
        getUsername();

        // Assign variable global
        mButtonSave = mView.findViewById(R.id.btn_aep_save);
        mEditFullName = mView.findViewById(R.id.edt_aep_full_name);
        mEditEmail = mView.findViewById(R.id.edt_aep_email);
        mEditUsername = mView.findViewById(R.id.edt_aep_username);
        mEditPassword = mView.findViewById(R.id.edt_aep_password);

        // Menampilkan data ke EditText
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(mUsername);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEditFullName.setText(Objects.requireNonNull(dataSnapshot.child("full_name").getValue()).toString());
                mEditEmail.setText(Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString());
                mEditUsername.setHint(Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString());
                mEditPassword.setText(Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString());
                mEditUsername.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
            }
        });

        // Activities
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengubah state menjadi loading
                mButtonSave.setEnabled(false);
                mButtonSave.setText(mLoadingState);

                // Mengambil data dari EditText
                mFullName = mEditFullName.getText().toString();
                mEmail = mEditEmail.getText().toString();
                mPassword = mEditPassword.getText().toString();

                // Validasi apakah ada data yang kosong
                if (mFullName.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "Tidak boleh ada data yang kosong!", Toast.LENGTH_SHORT).show();
                    mButtonSave.setEnabled(true);
                    mButtonSave.setText(mNormalState);
                } else {
                    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Menyimpan data ke storage Firebase
                            dataSnapshot.getRef().child("full_name").setValue(mFullName);
                            dataSnapshot.getRef().child("email").setValue(mEmail);
                            dataSnapshot.getRef().child("password").setValue(mPassword);

                            FragmentManager mFragmentManager = getFragmentManager();
                            if (mFragmentManager != null) {
                                getFragmentManager().popBackStack();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Ada kesalahan dalam database.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Menampilkan layout
        return mView;
    }

    // Method mendapatkan data user aktif
    private void getUsername() {
        String USERNAME_KEY = "username_key";
        String UsernameKeyArgs = "";
        SharedPreferences mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE);
        mUsername = mSharedPreferences.getString(UsernameKeyArgs, "");
    }
}
