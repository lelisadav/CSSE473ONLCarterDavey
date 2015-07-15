package edu.rosehulman.rafinder.controller.reslife;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.rafinder.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class DutyRosterFragment extends Fragment {

    public DutyRosterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_duty_roster, container, false);
    }
}
