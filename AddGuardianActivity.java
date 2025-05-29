package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGuardianActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText mobileNumberEditText;
    private Button saveGuardianButton;

    private GuardianDBHelper guardianDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guardian);

        // Initialize database helper
        guardianDBHelper = new GuardianDBHelper(this);

        // Initialize views
        nameEditText = findViewById(R.id.guardianNameEditText);
        mobileNumberEditText = findViewById(R.id.guardianMobileEditText);
        saveGuardianButton = findViewById(R.id.saveGuardianButton);

        // Set click listener
        saveGuardianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGuardian();
            }
        });
    }

    private void saveGuardian() {
        String name = nameEditText.getText().toString().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();

        if (!name.isEmpty() && !mobileNumber.isEmpty()) {
            Guardian guardian = new Guardian(0, name, mobileNumber);
            guardianDBHelper.insertGuardian(guardian);
            Toast.makeText(this, "Guardian saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity and go back to the previous screen
        } else {
            Toast.makeText(this, "Please enter guardian details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connection
        guardianDBHelper.close();
    }
}
