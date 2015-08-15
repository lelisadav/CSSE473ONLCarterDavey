package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.RAListArrayAdapter;
import edu.rosehulman.rafinder.model.person.Employee;

public class HomeFragmentSubsectionMyHallRAs extends Fragment implements RAListArrayAdapter.RAListArrayAdapterCallbacks {

    private HomeMyHallListener mListener;

    public static HomeFragmentSubsectionMyHallRAs newInstance() {
        return new HomeFragmentSubsectionMyHallRAs();
    }

    public HomeFragmentSubsectionMyHallRAs() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_subsection_my_hall_ras, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.myHallRAsFragment);
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        List<Employee> hallRAs = mListener.getMyRAs();
        RAListArrayAdapter<Employee> mAdapter2 = new RAListArrayAdapter<Employee>(this.getActivity(), R.layout.fragment_home, hallRAs, this);
        listView.setAdapter(mAdapter2);
        setListViewHeightBasedOnChildren(listView);
        final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.myHallexpander);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundResource(R.drawable.expander_close_holo_light);
                    toggleButton.refreshDrawableState();
                    listView.setVisibility(View.VISIBLE);
                } else {
                    toggleButton.setBackgroundResource(R.drawable.expander_open_holo_light);
                    toggleButton.refreshDrawableState();
                    listView.setVisibility(View.GONE);
                }
            }
        });
        return view;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (HomeMyHallListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HomeMyHallListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void moreDetailsRequested(Employee RA) {
        mListener.switchToProfile(RA);
    }

    public interface HomeMyHallListener {
        public void switchToProfile(Employee res);

        public List<Employee> getMyRAs();

    }

}
