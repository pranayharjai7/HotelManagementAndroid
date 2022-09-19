package com.pranayharjai7.hotelmanagementandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pranayharjai7.hotelmanagementandroid.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ActivityResultLauncher registerActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {

                }
        );

    }

    public void loginButtonClicked(View view) {

    }

    public void registerButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        //startActivityForResult(intent, RESULT_OK);
        registerActivityLauncher.launch(intent);
    }

    public void forgotPasswordClicked(View view) {
    }
}