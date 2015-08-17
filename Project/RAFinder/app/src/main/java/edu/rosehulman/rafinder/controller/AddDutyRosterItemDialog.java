package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.ConfigKeys;
import edu.rosehulman.rafinder.R;

public class AddDutyRosterItemDialog extends DialogFragment {


    private AddDutyRosterItemListener mListener;
    private LocalDate last;
    EditText fri;
    EditText sat;
    Spinner spinner;
    String hall;

    public static AddDutyRosterItemDialog newInstance(LocalDate last, String hall) {
        AddDutyRosterItemDialog fragment = new AddDutyRosterItemDialog();
        Bundle args = new Bundle();
        args.putString("LAST", last.toString(ConfigKeys.formatter));
        args.putString("HALL", hall);
        fragment.setArguments(args);
        return fragment;
    }

    public AddDutyRosterItemDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            last = LocalDate.parse(getArguments().getString("LAST", null), ConfigKeys.formatter);
            hall = getArguments().getString("HALL");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_duty_roster_item_dialog, null);
        fri = (EditText) view.findViewById(R.id.friField);
        sat = (EditText) view.findViewById(R.id.satField);

        builder.setView(createView(view));
        return builder.setIcon(R.drawable.ic_phone)
                .setTitle(getResources().getString(R.string.edit_duty_roster))
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                LocalDate date = (LocalDate) spinner.getSelectedItem();
                                mListener.onAddDutyRosterItem(hall, date, fri.getText().toString(), sat.getText().toString());
                            }
                        }
                )
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();

    }

    private View createView(View view) {
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(new DutyRosterSpinnerAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item,
                last));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddDutyRosterItemListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                                         + " must implement AddDutyRosterDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface AddDutyRosterItemListener {

        public void onAddDutyRosterItem(String hall, LocalDate friday, String fri, String sat);
    }

    private List<LocalDate> getDates(LocalDate last) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate next = last;
        for (int i = 0; i < 12; i++) {
            next = next.plusDays(7);
            dates.add(next);
        }
        return dates;
    }

    public class DutyRosterSpinnerAdapter extends ArrayAdapter<LocalDate> {
        private final int mLayout;
        private final Context mContext;
        private final int mDropdown;


        public DutyRosterSpinnerAdapter(Context context, int resource, int dropdownresource, LocalDate last) {
            super(context, resource, getDates(last));
            super.setDropDownViewResource(dropdownresource);
            mLayout = resource;
            mContext = context;
            mDropdown = dropdownresource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = convertView != null ? convertView : inflater.inflate(mLayout, parent, false);
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(getItem(position).toString(ConfigKeys.formatter));
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = convertView != null ? convertView : inflater.inflate(mDropdown, parent, false);
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(getItem(position).toString(ConfigKeys.formatter));
            return view;
        }
    }

}
