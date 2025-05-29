package com.example.iamsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.Volley;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;


public class UserHomeActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_SEND_SMS_PERMISSION = 2;

    private EditText userNameEditText;
    private EditText mobileNumberEditText;
    GPSHandler gpsHandler;
    private Button saveUserButton;
    private Button addGuardianButton;
    private Button viewRegisteredButton;
    private Button sosEmergencyButton;
    private Button panic_button;
    private Button nearbyPoliceButton;
    private Button createComplaintsButton;
    private Button tipsToEscapeButton;

    private UserDBHelper userDBHelper;
    private GuardianDBHelper guardianDBHelper;
    private TextView locationlext;
    private static final String PHONE_NUMBER = "7349731803";
    private static final int CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        // Initialize database helpers
        userDBHelper = new UserDBHelper(this);
        guardianDBHelper = new GuardianDBHelper(UserHomeActivity.this);
        gpsHandler = new GPSHandler(this);
        String location = gpsHandler.getCurrentAddress();
        locationlext = findViewById(R.id.locationlext);
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), location, Toast.LENGTH_SHORT).show();
                locationlext.setText(location);
            }
        }, 3000);
        // Initialize views
        // userNameEditText = findViewById(R.id.userNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        //  saveUserButton = findViewById(R.id.saveUserButton);
//        addGuardianButton = findViewById(R.id.addGuardianButton);
//        viewRegisteredButton = findViewById(R.id.viewRegisteredButton);
//        sosEmergencyButton = findViewById(R.id.sosEmergencyButton);
//        callPoliceButton = findViewById(R.id.callPoliceButton);
//        nearbyPoliceButton = findViewById(R.id.nearbyPoliceButton);
//        tipsForWomenSafetyButton = findViewById(R.id.tipsForWomenSafetyButton);
//        tipsToEscapeButton = findViewById(R.id.tipsToEscapeButton);

        // Set click listeners
//        saveUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUser();
//            }
//        });

        addGuardianButton = findViewById(R.id.addGuardianButton);
        addGuardianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddGuardianActivity();
            }
        });
        viewRegisteredButton = findViewById(R.id.view_registered_button);

        viewRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewRegisteredActivity();
            }
        });

        sosEmergencyButton = findViewById(R.id.sosButton);
        sosEmergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guardianDBHelper.getAllGuardians().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please add at least one guardian", Toast.LENGTH_LONG).show();
                } else {
                    sendSOSAlert();
                }

            }
        });
        panic_button = findViewById(R.id.panic_button);
        panic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                panic_button();
            }
        });
        nearbyPoliceButton = findViewById(R.id.nearbyPoliceButton);
        nearbyPoliceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNearbyPoliceStations();
            }
        });
        createComplaintsButton = findViewById(R.id.createComplaintsButton);
        createComplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateComplaints();
            }


        });
        tipsToEscapeButton = findViewById(R.id.tipsToEscapeButton);
        tipsToEscapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTipsToEscape();
            }
        });
    }

    private void panic_button() {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, start the call intent
            startActivity(callIntent);
        }


    }





    private void saveUser() {
        String userName = userNameEditText.getText().toString().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();

        if (!userName.isEmpty() && !mobileNumber.isEmpty()) {

            int id = 0;
            User user = new User(id, userName, mobileNumber);

            userDBHelper.insertUser(user);
            Toast.makeText(this, "User saved successfully!", Toast.LENGTH_SHORT).show();
            userNameEditText.setText("");
            mobileNumberEditText.setText("");
        } else {
            Toast.makeText(this, "Please enter user details", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAddGuardianActivity() {
        Intent intent = new Intent(this, AddGuardianActivity.class);
        startActivity(intent);
    }

    private void openViewRegisteredActivity() {
        Intent intent = new Intent(this, ViewRegisteredActivity.class);
        startActivity(intent);
    }

    private void sendSOSAlert() {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Get user's current location

            double latitude = gpsHandler.getLatitude();
            double longitude = gpsHandler.getLongitude();
            String url = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;

            String location =locationlext.getText().toString() + "  "+" click the given link :" + url;
            if (location != null) {
                // Get user's details
                User user = userDBHelper.getUser();

                // Get registered guardians
                List<Guardian> registeredGuardians = guardianDBHelper.getAllGuardians();

                // Send SOS alert to registered guardians
                for (Guardian guardian : registeredGuardians) {
                    // Implement your SOS alert logic here
                    // You can use the user's details, guardian's details, and location
                    // to send an emergency alert
                    sendEmergencyAlert(user, guardian, location);

                }

                // Also, send SOS alert to the police
                final Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },3000);
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }


    private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check if location services are enabled
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Check location permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Request location updates
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        // Location update received
                        // You can handle the location update here or store it in a variable
                        // for later use
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        // Not needed for this implementation
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // Not needed for this implementation
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        // Not needed for this implementation
                    }
                });

                // Get the last known location
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    return lastKnownLocation;
                }
            } else {
                // Request location permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            }
        }

        // Return null if location cannot be obtained
        return null;
    }


    private void sendEmergencyAlert(User user, Guardian guardian, String location) {
        // Check if the necessary objects are not null

        if (user != null && guardian != null && location != null) {
            String message = "Emergency alert: " + user.getUserName() + " needs help! Current location: " +location;

            // Use an SMS gateway or library to send the message to the guardian's phone number
            String guardianPhoneNumber = guardian.getMobileNumber();
            // Implement your code to send an SMS to the guardian with the emergency alert message
            sendSMS(guardianPhoneNumber, message);
            //Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_SHORT).show();
            // You can also use other methods to send the emergency alert, such as push notifications, email, etc.
            // Implement the code for your preferred method here
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Volume up button was pressed
            if(guardianDBHelper.getAllGuardians().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please add at least one guardian", Toast.LENGTH_LONG).show();
            }
            else {
                sendSOSAlert();
            }
            return true; // Consumes the event, preventing it from being dispatched further
        }

        return super.onKeyDown(keyCode, event);
    }

    private void sendSMS(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, send the SMS
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(this, "Emergency alert sent!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to send emergency alert!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            // Request the SEND_SMS permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS_PERMISSION);
        }



    }




    private void showNearbyPoliceStations() {
        // Replace with your current location coordinates
        double latitude = 37.7749;
        double longitude = -122.4194;

        // Create the search query
        String query = "Near by police station";
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + query);

        // Create an Intent to launch Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // Check if there is a compatible app to handle the Intent
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // Launch Google Maps
            startActivity(mapIntent);
        } else {
            // Handle the case where Google Maps is not installed
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
        }
    }



    private void showCreateComplaints() {
        Intent intent = new Intent(UserHomeActivity.this, ComplaintActivity.class);
        startActivity(intent);
    }

    private void showTipsToEscape() {
        // Create an Intent to start the TipsToEscapeActivity
        Intent intent = new Intent(this, TipsToEscapeActivity.class); // Replace 'this' with your current activity

        // Start the TipsToEscapeActivity
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, handle the logic here
                sendSOSAlert();
            } else {
                // Permission denied, handle the logic here
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==REQUEST_SEND_SMS_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the SMS
                Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connections
        userDBHelper.close();
        guardianDBHelper.close();
    }
}
