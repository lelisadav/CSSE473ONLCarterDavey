package edu.rosehulman.rafinder.controller.reslife;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.rafinder.R;


/**
 * The RA view of the Duty Roster (which is editable for RAs, but not for SAs).
 */
public class DutyRosterFragment extends Fragment {
    private DutyRosterListener mListener;

    public static DutyRosterFragment newInstance() {
        return new DutyRosterFragment();
    }

    public DutyRosterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_duty_roster, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DutyRosterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DutyRosterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DutyRosterListener {
        public void onDutyRosterInteraction();
    }
}
