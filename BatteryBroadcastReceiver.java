package com.example.iamsafe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.telephony.SmsManager;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;

        if (batteryPct <= 15) {
            // TODO: Replace with real guardian phone number from DB
            String phoneNumber = "9876543210";
            String message = "⚠️ Battery is low. I'm at risk. Please check on me.";

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(context, "Low Battery Alert Sent", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Failed to send alert", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
