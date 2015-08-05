package edu.rosehulman.rafinder.controller.reslife;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.Resident;

/**
 * The RA view of the Profile Page.
 * TODO: this version should only appear to an RA, after he/she clicks the edit button.
 * TODO: Profile Pic long press to select image
 * TODO: location and status detail long press to editText dialog
 * TODO: status long press to status spinner dialog
 */
public class ProfileFragment extends Fragment {
    private ProfileListener mListener;
    private Resident resident;
    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener!=null){
            resident=mListener.getSelectedResident();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ProfileListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ProfileListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ProfileListener {
        public Employee getSelectedResident();
    }

}
