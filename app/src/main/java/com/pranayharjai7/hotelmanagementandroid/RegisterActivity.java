package com.pranayharjai7.hotelmanagementandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pranayharjai7.hotelmanagementandroid.databinding.ActivityRegisterBinding;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStoreDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();
    }

    public void registerButtonClicked(View view) {
        String userName = binding.usernameRegisterEditText.getText().toString();
        String emailAddress = binding.emailAddressRegisterEditText.getText().toString();
        String password = binding.passwordRegisterEditText.getText().toString();
        String key = binding.keyRegisterEditText.getText().toString();

        if (userName.isEmpty()) {
            binding.usernameRegisterEditText.setError("This field is required!");
            binding.usernameRegisterEditText.requestFocus();
            return;
        }
        if (emailAddress.isEmpty()) {
            binding.emailAddressRegisterEditText.setError("This field is required!");
            binding.emailAddressRegisterEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.passwordRegisterEditText.setError("This field is required!");
            binding.passwordRegisterEditText.requestFocus();
            return;
        }
        if (key.isEmpty()) {
            binding.keyRegisterEditText.setError("This field is required!");
            binding.keyRegisterEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            binding.emailAddressRegisterEditText.setError("Please provide valid Email!");
            binding.emailAddressRegisterEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.passwordRegisterEditText.setError("Password length should be min 6 characters!");
            binding.passwordRegisterEditText.requestFocus();
            return;
        }

        fireStoreDatabase.collection("KeyStorage")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (doc.getId().equals("Keys")) {
                                if (!key.equals(doc.getData().get("key1").toString())) {
                                    binding.keyRegisterEditText.setError("You entered wrong key!");
                                    binding.keyRegisterEditText.requestFocus();
                                }
                                break;
                            }
                        }
                    }
                });

        if(!key.equals("lol")) {
            binding.keyRegisterEditText.setError("You entered wrong key!");
            binding.keyRegisterEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailAddress,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(this, "Database Error! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}