package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PoliceLoginActivity extends AppCompatActivity {
    EditText username, password, repassword, mobileNumberEditText;
    Button signup;
    PoliceDBHelper DB;
    PoliceDBHelper policeDBHelper;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        textView = findViewById(R.id.myTextView);
        policeDBHelper = new PoliceDBHelper(this);
        DB = new PoliceDBHelper(this);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mobilenum = mobileNumberEditText.getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(mobilenum)) {
                    Toast.makeText(PoliceLoginActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        boolean checkUser = DB.checkUsername(user);
                        if (!checkUser) {
                            boolean insert = DB.insertData(user, pass, mobilenum);
                            if (insert) {
                                Toast.makeText(PoliceLoginActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PoliceLoginActivity.this, PoliceHomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PoliceLoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PoliceLoginActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PoliceLoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoliceLoginActivity.this, PoliceActivity.class);
                startActivity(intent);
            }
        });
    }
}

