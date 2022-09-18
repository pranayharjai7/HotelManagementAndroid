package com.pranayharjai7.hotelmanagementandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView hotelManagementTextView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, LoginActivity.class);
        hotelManagementTextView = findViewById(R.id.hotelManagementTextView);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();

            }
        };

        hotelManagementTextView.postDelayed(task,2000);
    }


    public void screenClicked(View view) {
        startActivity(intent);
        finish();
    }
}