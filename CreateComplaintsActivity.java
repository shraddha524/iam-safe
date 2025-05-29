package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateComplaintsActivity extends AppCompatActivity {

    private EditText editTextComplaintTitle;
    private EditText editTextComplaintDescription;
    private Button buttonCreateComplaint;
    private ComplaintDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaints);

        databaseHelper = new ComplaintDBHelper(this);

        editTextComplaintTitle = findViewById(R.id.editTextComplaintTitle);
        editTextComplaintDescription = findViewById(R.id.editTextComplaintDescription);
        buttonCreateComplaint = findViewById(R.id.buttonCreateComplaint);

        buttonCreateComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextComplaintTitle.getText().toString();
                String description = editTextComplaintDescription.getText().toString();

                if (!title.isEmpty() && !description.isEmpty()) {
                    storeComplaint(title, description);
                    Toast.makeText(CreateComplaintsActivity.this, "Complaint created successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateComplaintsActivity.this, "Please enter complaint details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeComplaint(String title, String description) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        db.insert("complaints", null, values);
    }
}