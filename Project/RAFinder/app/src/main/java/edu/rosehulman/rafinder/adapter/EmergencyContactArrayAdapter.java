package edu.rosehulman.rafinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.person.EmergencyContact;

public class EmergencyContactArrayAdapter extends ArrayAdapter<EmergencyContact> {

    private final Context mContext;
    private final List<EmergencyContact> mObjects;
    private final int mLayout;
    private final EmergencyContactCallbacks mCallbacks;

    public EmergencyContactArrayAdapter(Context context, int textViewResourceId,
                                        List<EmergencyContact> objects, EmergencyContactCallbacks callbacks) {
        super(context, R.layout.layout_emergency_contact, textViewResourceId, objects);
        mLayout = R.layout.layout_emergency_contact;
        mContext = context;
        mObjects = objects;
        mCallbacks = callbacks;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView != null ? convertView : inflater.inflate(mLayout, parent, false);
        TextView nameTV = (TextView) view.findViewById(R.id.nameTextView);
        Button callButton = (Button) view.findViewById(R.id.callButton);
        Button emailButton = (Button) view.findViewById(R.id.emailButton);
        final EmergencyContact selected = mObjects.get(position);
        String name = selected.getName();
        switch (selected.getPosition()) {
        case RA:
            name += " (RA)";
            if (selected.getPriority() == EmergencyContact.Priority.ON_DUTY) {
                name += " (On Duty)";
            }
            break;
        case SA:
            name += " (SA)";
            break;
        case GA:
            name += " (GA)";
            break;
        case ADMIN:
            name += " (Office of ResLife)";
            break;

        }


        nameTV.setText(name);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.makePhoneCall(selected.getPhone());
            }
        });
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.sendEmail(selected.getEmail());
            }
        });
        view.refreshDrawableState();
        return view;
    }

    public interface EmergencyContactCallbacks {
        public void makePhoneCall(String phoneNumber);

        public void sendEmail(String emailAddress);
    }
}
