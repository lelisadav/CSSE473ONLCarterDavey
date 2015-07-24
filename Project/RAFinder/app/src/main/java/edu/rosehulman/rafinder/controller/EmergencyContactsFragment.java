package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.rafinder.R;

/**
 * The Emergency Contacts list. Contains items of type {@link EmergencyContactFragment}.
 */
public class EmergencyContactsFragment extends Fragment {
    private EmergencyContactsListener mListener;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmergencyContactsFragment.
     */
    public static EmergencyContactsFragment newInstance() {
        EmergencyContactsFragment fragment = new EmergencyContactsFragment();
        return fragment;
    }

    public EmergencyContactsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency_contacts, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EmergencyContactsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                                         + " must implement EmergencyContactsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface EmergencyContactsListener {
        public void onEmergencyContactsInteraction();
    }

}
