package edu.rosehulman.rafinder.controller.student;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.Resident;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;

/**
 * The Standard view of the Profile Page, with an edit button only visible to the owner of the Page.
 * TODO: consider merging this with {@link edu.rosehulman.rafinder.controller.reslife.ProfileFragment} and  controlling
 * access
 */
public class StudentProfileFragment extends Fragment {
    private StudentProfileListener mListener;
    private Resident resident;
    public StudentProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener!=null){
            resident=mListener.getSelectedResident();
        }
    }

    public static StudentProfileFragment newInstance() {
        return new StudentProfileFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_student, container, false);
        TextView nameTextView= (TextView) view.findViewById(R.id.nameTextView);
        TextView roomTextView= (TextView) view.findViewById(R.id.roomTextView);
        TextView locationTextView= (TextView) view.findViewById(R.id.locationTextView);
        TextView statusTextView= (TextView) view.findViewById(R.id.statusTextView);
        TextView phoneTextView= (TextView) view.findViewById(R.id.phoneTextView);
        TextView emailTextView= (TextView) view.findViewById(R.id.emailTextView);
        nameTextView.setText(resident.getName());
        roomTextView.setText("Room: "+resident.getHall()+" "+resident.getRoom());

        if (resident instanceof Employee){
            Employee RA= (Employee) resident;
            locationTextView.setText("Location: "+RA.getLocation());
            statusTextView.setVisibility(View.GONE);
            phoneTextView.setText("Phone: " + RA.getPhoneNumber());
            emailTextView.setText("Email: "+RA.getEmailAddress());
        }
        else{
            locationTextView.setVisibility(View.GONE);
            statusTextView.setVisibility(View.GONE);
            phoneTextView.setVisibility(View.GONE);
            emailTextView.setVisibility(View.GONE);

        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StudentProfileListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface StudentProfileListener {
        // TODO: Update argument type and name
        public void onStudentProfileInteraction(Uri uri);
        public Resident getSelectedResident();
    }

}
