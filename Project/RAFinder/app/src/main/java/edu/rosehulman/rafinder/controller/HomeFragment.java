package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.RAListArrayAdapter;
import edu.rosehulman.rafinder.model.person.Employee;

/**
 * The Home Page.
 */
public class HomeFragment extends Fragment
        implements RAListArrayAdapter.RAListArrayAdapterCallbacks {
    private HomeListener mListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (HomeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void moreDetailsRequested(Employee RA) {
        mListener.switchToProfile(RA);
    }

    public interface HomeListener {
        public void switchToProfile(Employee res);

    }

}
