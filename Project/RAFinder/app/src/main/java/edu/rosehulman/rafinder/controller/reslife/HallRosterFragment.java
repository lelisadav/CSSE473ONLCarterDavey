package edu.rosehulman.rafinder.controller.reslife;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.FloorRosterArrayAdapter;
import edu.rosehulman.rafinder.model.Hall;
import edu.rosehulman.rafinder.model.RoomEntry;
import edu.rosehulman.rafinder.model.dummy.DummyData;

/**
 * The RA's view of a listing for a floor's residents
 */
public class HallRosterFragment extends Fragment
        implements AbsListView.OnItemClickListener, HallHeader.HallHeaderListener {

    private Hall hall;
    private int floorIndex; //not necessarily the floor number
    private List<RoomEntry> rooms= DummyData.getRooms();

    private HallRosterListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with Views.
     */
    private ListAdapter mAdapter;

    public static HallRosterFragment newInstance() {
        return new HallRosterFragment();
    }

    public HallRosterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FloorRosterArrayAdapter(getActivity(), android.R.id.text1, rooms);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_life_floor_roster_grid, container, false);
        FrameLayout frame = (FrameLayout) view.findViewById(R.id.headerFrame);
        HallHeader header = new HallHeader();
//        frame.addView(header.getView()); // TODO: NullPointer
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (HallRosterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HallRosterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(rooms.get(position).toString());
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

    @Override
    public Hall getHall() {
        return this.hall;
    }

    @Override
    public int getCurrentFloorIndex() {
        return floorIndex;
    }

    @Override
    public void setCurrentFloorIndex(int index) {
        this.floorIndex = index;
    }

    public interface HallRosterListener {
        public void onHallRosterInteraction();
    }

}
