package com.pranayharjai7.hotelmanagementandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pranayharjai7.hotelmanagementandroid.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ActivityResultLauncher registerActivityLauncher;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {

                }
        );

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            return;
        }
        Toast.makeText(this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }

    public void loginButtonClicked(View view) {
        String emailAddress = binding.emailAddressEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (emailAddress.isEmpty()) {
            binding.emailAddressEditText.setError("Email Address is required!");
            binding.emailAddressEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            binding.emailAddressEditText.setError("Please provide valid Email!");
            binding.emailAddressEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.passwordEditText.setError("Password is required!");
            binding.passwordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });

    }

    public void registerButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        //startActivityForResult(intent, RESULT_OK);
        registerActivityLauncher.launch(intent);
    }

    public void forgotPasswordClicked(View view) {
    }
}