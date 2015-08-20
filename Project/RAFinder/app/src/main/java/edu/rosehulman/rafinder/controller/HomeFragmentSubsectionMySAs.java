package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.EmployeeListArrayAdapter;
import edu.rosehulman.rafinder.model.person.Employee;

public class HomeFragmentSubsectionMySAs extends HomeFragmentSubsection
        implements EmployeeListArrayAdapter.EmployeeListArrayAdapterListener {

    private HomeMySAListener mListener;

    public static HomeFragmentSubsectionMySAs newInstance() {
        return new HomeFragmentSubsectionMySAs();
    }

    public HomeFragmentSubsectionMySAs() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_subsection_my_sas, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.mySAsView);

        List<Employee> hallSAs = mListener.getMySAs();
        EmployeeListArrayAdapter mAdapter2 = new EmployeeListArrayAdapter(
                getActivity(),
                R.layout.fragment_home,
                hallSAs,
                this);
        listView.setAdapter(mAdapter2);
        setListViewHeightBasedOnChildren(listView);

        final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.mySAExpander);
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
            mListener = (HomeMySAListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HomeMySAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void switchToProfile(Employee employee) {
        mListener.switchToProfile(employee);
    }

    public interface HomeMySAListener extends HomeSubsectionListener {
        public List<Employee> getMySAs();
    }

}
