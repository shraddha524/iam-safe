package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button userLoginButton, policeLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userLoginButton = findViewById(R.id.userLoginButton);
        policeLoginButton = findViewById(R.id.policeLoginButton);

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserLoginActivity();
            }
        });

        policeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPoliceLoginActivity();
            }
        });
    }

    private void openUserLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, UserLoginActivity.class);
        startActivity(intent);
    }

    private void openPoliceLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, PoliceLoginActivity.class);
        startActivity(intent);
    }
}
