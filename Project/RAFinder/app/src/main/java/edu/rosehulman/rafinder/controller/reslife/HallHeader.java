package edu.rosehulman.rafinder.controller.reslife;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.Hall;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link HallHeader.OnFragmentInteractionListener} interface to handle interaction events. Use the
 * {@link HallHeader#newInstance} factory method to create an instance of this fragment.
 */
public class HallHeader extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private Hall hall;
    private int currentFloor;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1
     *         Parameter 1.
     * @param param2
     *         Parameter 2.
     * @return A new instance of fragment HallHeader.
     */
    // TODO: Rename and change types and number of parameters
    public static HallHeader newInstance(String param1, String param2) {
        HallHeader fragment = new HallHeader();

        return fragment;
    }

    public HallHeader() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hall_header, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        if (mListener != null) {
            this.hall = mListener.getHall();
            this.currentFloor = mListener.getCurrentFloorIndex();
            int[] floorNumbers = hall.getFloorNumbers();
            for (int i = 0; i < floorNumbers.length; i++) {
                final int floorNum = i;
                ToggleButton b = new ToggleButton(this.getActivity());
                b.setText(floorNumbers[i] + "");
                b.setPadding(5, 5, 5, 5);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.setCurrentFloorIndex(floorNum);
                    }
                });
                b.setVisibility(View.VISIBLE);
                if (i == currentFloor) {
                    b.setChecked(true);
                } else {
                    b.setChecked(false);
                }
                layout.addView(b);
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this
     * fragment to be communicated to the activity and potentially other fragments contained in that activity.
     * <p/>
     * See the Android Training lesson
     * <a href= "http://developer.android.com/training/basics/fragments/communicating.html">Communicating with Other Fragments</a>
     * for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        public Hall getHall();

        public int getCurrentFloorIndex();

        public void setCurrentFloorIndex(int i);
    }

}
