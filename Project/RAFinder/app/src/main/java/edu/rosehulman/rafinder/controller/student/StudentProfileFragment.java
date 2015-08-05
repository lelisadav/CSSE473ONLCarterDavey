package edu.rosehulman.rafinder.controller.student;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.person.Employee;

/**
 * The Standard view of the Profile Page, with an edit button only visible to the owner of the Page.
 * TODO: consider merging this with {@link edu.rosehulman.rafinder.controller.reslife.ProfileFragment} and  controlling
 * access
 */
public class StudentProfileFragment extends Fragment {
    private StudentProfileListener mListener;
    private Employee resident;

    public StudentProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            resident = mListener.getSelectedResident();
        }
    }

    public static StudentProfileFragment newInstance() {
        return new StudentProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_student, container, false);
        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView roomTextView = (TextView) view.findViewById(R.id.roomTextView);
        TextView emailTextView = (TextView) view.findViewById(R.id.emailTextView);
        TextView phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
        TextView statusTextView = (TextView) view.findViewById(R.id.statusTextView);
        TextView statusDetailTextView = (TextView) view.findViewById(R.id.statusDetailTextView);

        nameTextView.setText(resident.getName());
        roomTextView.setText("Room: " + resident.getHall() + " " + resident.getRoom());
        emailTextView.setText("Email: " + resident.getEmail());
        phoneTextView.setText("Phone: " + resident.getPhoneNumber());
        statusTextView.setText("Location: " + resident.getStatus());
        statusDetailTextView.setText("\"" + resident.getStatusDetail() + "\"");

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
        public Employee getSelectedResident();
    }

}
