package com.example.iamsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TipsToEscapeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_to_escape);
    }
        public void displayPepperSprayUsage(View view) {
            Toast.makeText(this, "Pepper Spray Usage:\n1. Hold the pepper spray canister firmly in your hand.\n2. Aim for the attacker's face, specifically the eyes and nose.\n3. Press down on the nozzle to release the spray.\n4. Move away quickly and seek help.", Toast.LENGTH_LONG).show();
        }

        public void displayMartialArtsMoves(View view) {
            Toast.makeText(this, "Basic Martial Arts Moves:\n1. Palm Strike: Use the base of your palm to strike the attacker's nose or chin.\n2. Groin Kick: Lift your knee and aim to kick the attacker's groin area.\n3. Elbow Strike: Use your elbow to strike the attacker's ribs or face.\n4. Escape Techniques: Learn basic escapes from grabs or holds.", Toast.LENGTH_LONG).show();
        }

        public void displaySafetyDevices(View view) {
            Toast.makeText(this, "Personal Safety Devices:\n1. Personal Alarm: Carry a small, loud alarm that can be activated in emergencies.\n2. Whistle: Use a whistle to attract attention and deter attackers.\n3. Stun Gun: Learn how to safely use a stun gun for self-defense.", Toast.LENGTH_LONG).show();
        }
    }
