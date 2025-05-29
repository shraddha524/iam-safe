package com.example.iamsafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RegisteredGuardianAdapter extends ArrayAdapter<Guardian> {
    private Context context;
    private List<Guardian> registeredGuardians;

    public RegisteredGuardianAdapter(Context context, List<Guardian> registeredGuardians) {
        super(context, 0, registeredGuardians);
        this.context = context;
        this.registeredGuardians = registeredGuardians;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_registered_guardian, parent, false);
        }

        Guardian currentGuardian = registeredGuardians.get(position);

        TextView nameTextView = listItemView.findViewById(R.id.nameTextView);
        TextView mobileNumberTextView = listItemView.findViewById(R.id.mobileNumberTextView);

        nameTextView.setText(currentGuardian.getName());
        mobileNumberTextView.setText(currentGuardian.getMobileNumber());

        return listItemView;
    }
}
