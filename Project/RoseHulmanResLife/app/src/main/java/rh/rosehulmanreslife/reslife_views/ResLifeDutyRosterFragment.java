package rh.rosehulmanreslife.reslife_views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rh.rosehulmanreslife.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ResLifeDutyRosterFragment extends Fragment {

    public ResLifeDutyRosterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_duty_roster, container, false);
    }
}
