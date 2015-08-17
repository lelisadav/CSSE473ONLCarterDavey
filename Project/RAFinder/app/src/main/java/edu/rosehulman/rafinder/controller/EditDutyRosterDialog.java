package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import org.joda.time.LocalDate;
import org.w3c.dom.Text;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.DutyRosterItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditDutyRosterDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditDutyRosterDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDutyRosterDialog extends DialogFragment {


    private OnFragmentInteractionListener mListener;
    private LocalDate friday;
    private String friDuty;
    private String satDuty;
    EditText fri;
    EditText sat;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment EditDutyRosterDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static EditDutyRosterDialog newInstance(LocalDate friday, String friDuty, String satDuty) {
        EditDutyRosterDialog fragment = new EditDutyRosterDialog();
        Bundle args=new Bundle();
        args.putString("friday", friday.toString(ConfigKeys.formatter));
        args.putString("friDuty", friDuty);
        args.putString("satDuty", satDuty);
        fragment.setArguments(args);
        return fragment;
    }

    public EditDutyRosterDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            friday=LocalDate.parse(getArguments().getString("friday", null), ConfigKeys.formatter);
            friDuty=getArguments().getString("friDuty");
            satDuty=getArguments().getString("satDuty");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.edit_duty_roster_dialog, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(createView(view));
        return builder.setIcon(R.drawable.ic_phone)
                .setTitle(getResources().getString(R.string.edit_duty_roster))
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onEditConfirm(fri.getText().toString(), sat.getText().toString());
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditDutyRosterDialog.this.dismiss();
                            }
                        }
                )
                .create();

    }

    private View createView(View view){
        String date=friday.toString(ConfigKeys.formatter)+" - ";
        LocalDate d2=friday.plusDays(2);
        date=date+d2.toString(ConfigKeys.formatter);
        TextView weekend=(TextView) view.findViewById(R.id.weekendLabel);
        weekend.setText(date);
        fri=(EditText) view.findViewById(R.id.friField);
        fri.setText(friDuty);
        sat=(EditText) view.findViewById(R.id.satField);
        sat.setText(satDuty);
        return view;

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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onEditConfirm(String fri, String sat);

    }

}
