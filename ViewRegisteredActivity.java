package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.iamsafe.GuardianDBHelper;
import com.example.iamsafe.RegisteredGuardianAdapter;

import java.util.List;

public class ViewRegisteredActivity extends AppCompatActivity {
    private ListView registeredGuardiansListView;
    private RegisteredGuardianAdapter registeredGuardianAdapter;
    private GuardianDBHelper guardianDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered);

        // Initialize database helper
        guardianDBHelper = new GuardianDBHelper(this);

        // Initialize views
        registeredGuardiansListView = findViewById(R.id.registeredGuardiansListView);

        // Get all registered guardians
        List<Guardian> registeredGuardians = guardianDBHelper.getAllGuardians();

        // Set up the adapter
        registeredGuardianAdapter = new RegisteredGuardianAdapter(this, registeredGuardians);
        registeredGuardiansListView.setAdapter(registeredGuardianAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connection
        guardianDBHelper.close();
    }

}
