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
import edu.rosehulman.rafinder.model.person.Administrator;
import edu.rosehulman.rafinder.model.person.EmergencyContact;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.GraduateAssistant;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;
import edu.rosehulman.rafinder.model.person.SophomoreAdvisor;

/**
 * Created by daveyle on 7/26/2015.
 */
public class EmergencyContactArrayAdapter  extends ArrayAdapter<EmergencyContact> {

    private final Context context;
    private final List<EmergencyContact> objects;
    private final int layout;
    private EmergencyContactCallbacks mCallbacks;

    public EmergencyContactArrayAdapter(Context context, int textViewResourceId,
                                        List<EmergencyContact> objects, EmergencyContactCallbacks callbacks ) {
        super(context, R.layout.layout_emergency_contact, textViewResourceId, objects);
        this.layout = R.layout.layout_emergency_contact;
        this.context = context;
        this.objects = objects;
        this.mCallbacks=callbacks;

    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent, false);
        TextView nameTV= (TextView) view.findViewById(R.id.nameTextView);
        Button callButton= (Button) view.findViewById(R.id.callButton);
        Button emailButton = (Button) view.findViewById(R.id.emailButton);
        final EmergencyContact selected=objects.get(position);
        String name= selected.getName();
        switch (selected.getPosition()){
            case ResA:
                name=name+" (RA)";
                if (selected.getPriority()== EmergencyContact.Priority.ONDUTY){
                    name=name+" (On Duty)";
                }
                break;
            case SA:
                name=name+" (SA)";
                break;
            case GA:
                name=name+" (GA)";
                break;
            case ADMIN:
                name=name+" (Office of ResLife)";
                break;

        }


        nameTV.setText(name);

        TextView callTV= (TextView) view.findViewById(R.id.callTextView);
        TextView emailTV= (TextView) view.findViewById(R.id.emailTextView);
        callTV.setText(selected.getPhone());
        emailTV.setText(selected.getEmail());

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
    public interface EmergencyContactCallbacks{
        public void makePhoneCall(String phoneNumber);
        public void sendEmail(String emailAddress);
    }
}
