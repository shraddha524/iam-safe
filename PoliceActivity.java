package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PoliceActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button signinButton;
    PoliceDBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        usernameEditText = findViewById(R.id.username1);
        passwordEditText = findViewById(R.id.password1);
        signinButton = findViewById(R.id.signin1);

        DB = new PoliceDBHelper(this);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(PoliceActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    boolean loginSuccessful = DB.login(username, password);
                    if (loginSuccessful) {
                        Toast.makeText(PoliceActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PoliceActivity.this, PoliceHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(PoliceActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

