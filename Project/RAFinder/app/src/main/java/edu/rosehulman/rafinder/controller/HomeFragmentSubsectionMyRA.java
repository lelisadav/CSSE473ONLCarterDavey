package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.EmployeeListArrayAdapter;
import edu.rosehulman.rafinder.model.person.Employee;

public class HomeFragmentSubsectionMyRA extends Fragment
        implements EmployeeListArrayAdapter.EmployeeListArrayAdapterListener {

    private HomeMyRAListener mListener;

    public static HomeFragmentSubsectionMyRA newInstance() {
        return new HomeFragmentSubsectionMyRA();
    }

    public HomeFragmentSubsectionMyRA() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_subsection_my_ra, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.myRAView);

        List<Employee> myRAs = mListener.getMyRAs();

        if (myRAs.size() > 1) {
            ((TextView) view.findViewById(R.id.titleTextView)).setText(getString(R.string.my_ras));
        }

        EmployeeListArrayAdapter<Employee> mAdapter2 = new EmployeeListArrayAdapter<>(
                getActivity(),
                R.layout.fragment_home,
                myRAs,
                this);
        listView.setAdapter(mAdapter2);
        setListViewHeightBasedOnChildren(listView);

        final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.myRAexpander);
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
            mListener = (HomeMyRAListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HomeMyRAListener");
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
    public void switchToProfile(Employee ra) {
        mListener.switchToProfile(ra);
    }

    public interface HomeMyRAListener {
        public void switchToProfile(Employee res);
        public List<Employee> getMyRAs();
    }

}
