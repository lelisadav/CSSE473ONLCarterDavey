package edu.rosehulman.rafinder.controller.reslife;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.DutyRosterArrayAdapter;
import edu.rosehulman.rafinder.model.dummy.DummyData;


/**
 * The RA view of the Duty Roster (which is editable for RAs, but not for SAs).
 */
public class DutyRosterFragment extends Fragment {
    private DutyRosterListener mListener;
    private DutyRosterArrayAdapter mAdapter;

    public static DutyRosterFragment newInstance() {
        return new DutyRosterFragment();
    }

    public DutyRosterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_duty_roster, container, false);
        Log.i("RA Finder", DummyData.getDutyRoster().size() + "");
        ListView listView = (ListView) view.findViewById(R.id.dutyRosterListView);
//        Toast.makeText(this.getActivity(), DummyData.getDutyRoster().size() + "", Toast.LENGTH_LONG).show();
        mAdapter = new DutyRosterArrayAdapter(this.getActivity(), R.layout.fragment_student_duty_roster_widget, DummyData.getDutyRoster());
        listView.setAdapter(mAdapter);
        return view;
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
    }
}
