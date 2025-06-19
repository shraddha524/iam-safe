package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintActivity extends AppCompatActivity {

    private ListView listViewComplaints;
    private ComplaintDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);

        databaseHelper = new ComplaintDBHelper(this);

        listViewComplaints = findViewById(R.id.listViewComplaints);
        displayComplaints();
    }

    private void displayComplaints() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM complaints", null);

        List<String> complaintsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                String complaint = title + ": " + description;
                complaintsList.add(complaint);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, complaintsList);
        listViewComplaints.setAdapter(adapter);

        listViewComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String complaint = complaintsList.get(position);
                deleteComplaint(complaint);
            }
        });
    }

    private void deleteComplaint(String complaint) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs = complaint.split(": "); // Split the complaint string to retrieve the title
        db.delete("complaints", "title=?", new String[]{whereArgs[0]});
        Toast.makeText(this, "Complaint deleted successfully!", Toast.LENGTH_SHORT).show();
        displayComplaints(); // Refresh the list after deletion
    }
}