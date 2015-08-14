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
import android.widget.TextView;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.MainActivity;
import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.DutyRosterArrayAdapter;
import edu.rosehulman.rafinder.model.DutyRoster;
import edu.rosehulman.rafinder.model.DutyRosterItem;
;


/**
 * The Student view of the Duty Roster.
 */
public class StudentDutyRosterFragment extends Fragment implements AbsListView.OnItemClickListener {

    private String firebaseURL;

    private DutyRoster roster;
    private DutyRosterListener mListener;
    private String hallName;
    private LocalDate date;
    public static final String DATE="DATE";

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static StudentDutyRosterFragment newInstance(String hall, LocalDate date) {
        StudentDutyRosterFragment fragment= new StudentDutyRosterFragment();
        Bundle args=new Bundle();
        args.putString(MainActivity.HALL, hall);
        args.putString(DATE, date.toString(MainActivity.dateFormatter));
        fragment.setArguments(args);
        return fragment;
    }

    public StudentDutyRosterFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_duty_roster_widget, container, false);
        List<DutyRosterItem> rosterItems=new ArrayList<>();
        rosterItems.addAll(roster.getRoster().values());
        mAdapter = new DutyRosterArrayAdapter(this.getActivity(), R.layout.fragment_student_duty_roster_widget, rosterItems);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        (mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
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
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(rosterItems[position]);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when the list is empty. If
     * you would like to change the text, call this method to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();
        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity. <p/> See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating
     * with Other Fragments</a> for more information.
     */
    public interface DutyRosterListener {
        public DutyRoster getDutyRoster(String hall, LocalDate date);
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
