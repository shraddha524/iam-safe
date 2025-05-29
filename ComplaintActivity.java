package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComplaintActivity extends AppCompatActivity {
    private Button buttonCreateComplaint;
    private Button buttonViewComplaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        buttonCreateComplaint = findViewById(R.id.buttonCreateComplaint);
        buttonViewComplaints = findViewById(R.id.buttonViewComplaints);

        buttonCreateComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintActivity.this, CreateComplaintsActivity.class));
            }
        });

        buttonViewComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintActivity.this, ViewComplaintActivity.class));
            }
        });
    }
}