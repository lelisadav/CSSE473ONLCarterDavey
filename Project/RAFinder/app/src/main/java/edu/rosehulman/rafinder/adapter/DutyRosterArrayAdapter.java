package edu.rosehulman.rafinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.List;

import edu.rosehulman.rafinder.MainActivity;
import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.DutyRosterItem;
import edu.rosehulman.rafinder.model.person.Employee;

/**
 * Created by daveyle on 7/11/2015.
 */
public class DutyRosterArrayAdapter extends ArrayAdapter<DutyRosterItem> {
    private final Context context;
    private final List<DutyRosterItem> objects;
    private final int layout;

    public DutyRosterArrayAdapter(Context context, int textViewResourceId, List<DutyRosterItem> objects) {
        super(context, R.layout.fragment_student_duty_roster_widget, textViewResourceId, objects);
        this.layout = R.layout.fragment_student_duty_roster_widget;
        this.context = context;
        this.objects = objects;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * This method overrides the default getView method to show a two line view that has a due date. It also controls
     * the displaying of icons such as flags and trophies.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent, false);
        TextView weekendText = (TextView) view.findViewById(R.id.weekendTextView);
        TextView friNameText = (TextView) view.findViewById(R.id.nameTextView);
        TextView satNameText = (TextView) view.findViewById(R.id.nameTextView2);
        ImageButton friButton = (ImageButton) view.findViewById(R.id.callButton);
        ImageButton satButton = (ImageButton) view.findViewById(R.id.callButton2);
        DutyRosterItem item = super.getItem(position);
        final Employee fridayDuty = item.getFriDuty();
        final Employee saturdayDuty = item.getSatDuty();
        LocalDate friday = item.getFriday();
        LocalDate sunday = friday.plusDays(2);
        String weekend = friday.dayOfWeek().getAsShortText() + " " +
                friday.toString("M/d") + " - " +
                sunday.dayOfWeek().getAsShortText() + " " +
                sunday.toString("M/d");
        weekendText.setText(weekend);
        friNameText.setText(fridayDuty.getName());
        satNameText.setText(saturdayDuty.getName());
        friButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).dialPhoneNumber(fridayDuty.getPhoneNumber());
            }
        });
        satButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).dialPhoneNumber(saturdayDuty.getPhoneNumber());
            }
        });
        view.refreshDrawableState();
        return view;
    }
}
