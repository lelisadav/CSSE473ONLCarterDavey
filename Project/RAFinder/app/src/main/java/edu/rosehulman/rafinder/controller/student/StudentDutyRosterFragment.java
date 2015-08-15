package edu.rosehulman.rafinder.controller.student;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.DutyRosterArrayAdapter;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.DutyRosterItem;

/**
 * The Student view of the Duty Roster.
 */
public class StudentDutyRosterFragment extends Fragment implements AbsListView.OnItemClickListener {
    private DutyRoster roster;
    private DutyRosterListener mListener;
    private String hallName;
    private LocalDate date;

    private AbsListView mListView;

    private ListAdapter mAdapter;

    public static StudentDutyRosterFragment newInstance(String hall, LocalDate date) {
        StudentDutyRosterFragment fragment = new StudentDutyRosterFragment();
        Bundle args = new Bundle();
        args.putString(ConfigKeys.HALL, hall);
        args.putString(ConfigKeys.DATE, date.toString(ConfigKeys.dateFormat));
        fragment.setArguments(args);
        return fragment;
    }

    public StudentDutyRosterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            hallName = savedInstanceState.getString(ConfigKeys.HALL, null);
            String dateStr = savedInstanceState.getString(ConfigKeys.DATE, null);
            if (dateStr != null) {

                date = LocalDate.parse(dateStr, ConfigKeys.formatter);
            }
        } else if (getArguments() != null) {
            hallName = getArguments().getString(ConfigKeys.HALL, null);
            String dateStr = getArguments().getString(ConfigKeys.DATE, null);
            if (dateStr != null) {

                date = LocalDate.parse(dateStr, ConfigKeys.formatter);
            }
        }
        if (mListener != null) {
            roster = mListener.getDutyRoster();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_duty_roster_widget, container, false);
        List<DutyRosterItem> rosterItems = new ArrayList<>();
        rosterItems.addAll(roster.getRoster().values());
        mAdapter = new DutyRosterArrayAdapter(getActivity(), R.layout.fragment_student_duty_roster_widget, rosterItems);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        (mListView).setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DutyRosterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(rosterItems[position]);
        }
    }

    public interface DutyRosterListener {
        public DutyRoster getDutyRoster();
    }

    private LocalDate getNextFriday(LocalDate date) {
        LocalDate nextFriday = LocalDate.fromDateFields(date.toDate());
        switch (date.getDayOfWeek()) {
        case DateTimeConstants.THURSDAY:
            nextFriday = nextFriday.plusDays(1);
            break;
        case DateTimeConstants.WEDNESDAY:
            nextFriday = nextFriday.plusDays(2);
            break;
        case DateTimeConstants.TUESDAY:
            nextFriday = nextFriday.plusDays(3);
            break;
        case DateTimeConstants.MONDAY:
            nextFriday = nextFriday.plusDays(4);
            break;
        case DateTimeConstants.SUNDAY:
            nextFriday = nextFriday.plusDays(5);
            break;
        case DateTimeConstants.SATURDAY:
            nextFriday = nextFriday.minusDays(1);
            break;
        default:
            break;
        }
        return nextFriday;
    }

    private List<DutyRosterItem> populateDutyRoster() {
        LocalDate date = getNextFriday(LocalDate.now());
        return null;
    }
}
