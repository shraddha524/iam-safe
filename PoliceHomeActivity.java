package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class PoliceHomeActivity extends AppCompatActivity {
    private ListView listViewComplaints;
    private Button buttonViewComplaints;


    private ComplaintDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_home);

        listViewComplaints = findViewById(R.id.listViewComplaints);
        buttonViewComplaints = findViewById(R.id.buttonViewComplaints);

        dbHelper = new ComplaintDBHelper(this);

        buttonViewComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewComplaintActivity.class));
            }
        });

    }
}