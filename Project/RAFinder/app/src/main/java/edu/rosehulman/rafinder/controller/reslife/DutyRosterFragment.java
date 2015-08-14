package edu.rosehulman.rafinder.controller.reslife;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.MainActivity;
import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.DutyRosterArrayAdapter;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.DutyRosterItem;



/**
 * The RA view of the Duty Roster (which is editable for RAs, but not for SAs).
 */
public class DutyRosterFragment extends Fragment {
    private DutyRosterListener mListener;
    private DutyRosterArrayAdapter mAdapter;
    private String hallName;
    private LocalDate date;
    private DutyRoster roster;
    public static final String DATE="DATE";


    public static DutyRosterFragment newInstance(String hall, LocalDate date) {
        DutyRosterFragment fragment= new DutyRosterFragment();
        Bundle args=new Bundle();
        args.putString(MainActivity.HALL, hall);
        args.putString(DATE, date.toString(MainActivity.dateFormatter));
        fragment.setArguments(args);
        return fragment;
    }

    public DutyRosterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_duty_roster, container, false);
        //Log.i("RA Finder", DummyData.getDutyRoster().size() + "");
        ListView listView = (ListView) view.findViewById(R.id.dutyRosterListView);
        List<DutyRosterItem> rosterItems=new ArrayList<>();
        rosterItems.addAll(roster.getRoster().values());
//        Toast.makeText(this.getActivity(), DummyData.getDutyRoster().size() + "", Toast.LENGTH_LONG).show();
        mAdapter = new DutyRosterArrayAdapter(this.getActivity(), R.layout.fragment_student_duty_roster_widget, rosterItems);
        listView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            hallName=savedInstanceState.getString(MainActivity.HALL, null);
            String dateStr=savedInstanceState.getString(DATE, null);
            if (dateStr!=null) {

                date = LocalDate.parse(dateStr, MainActivity.formatter);
            }
        }
        else if(getArguments()!=null){
            hallName=getArguments().getString(MainActivity.HALL, null);
            String dateStr=getArguments().getString(DATE, null);
            if (dateStr!=null) {

                date = LocalDate.parse(dateStr, MainActivity.formatter);
            }
        }
        if (mListener!=null){
            roster=mListener.getDutyRoster(hallName, date);
        }
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
        public DutyRoster getDutyRoster(String hall, LocalDate date);
    }
}
