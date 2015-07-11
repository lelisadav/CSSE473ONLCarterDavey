package rh.rosehulmanreslife.adapters;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalDate;

import rh.rosehulmanreslife.MainActivity;
import rh.rosehulmanreslife.R;
import rh.rosehulmanreslife.models.DutyRosterItem;
import rh.rosehulmanreslife.models.ResLifeWorker;
import rh.rosehulmanreslife.student_views.StudentDutyRosterFragment;

/**
 * Created by daveyle on 7/11/2015.
 */
public class DutyRosterArrayAdapter extends ArrayAdapter<DutyRosterItem> {
    private final Context context;
    private final List<DutyRosterItem> objects;
    private final int layout;


    public DutyRosterArrayAdapter(Context context,
                                  int textViewResourceId, List<DutyRosterItem> objects) {
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
     * This method overrides the default getView method to show a two line view
     * that has a due date. It also controls the displaying of icons such as
     * flags and trophies.
     */
    @Override
    public View getView(int position, View convertView,
                        android.view.ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent, false);
        TextView weekendText = (TextView) view.findViewById(R.id.weekendTextView);
        TextView friNameText = (TextView) view.findViewById(R.id.nameTextView);
        TextView satNameText = (TextView) view.findViewById(R.id.nameTextView2);
        ImageButton friButton = (ImageButton) view.findViewById(R.id.callButton);
        ImageButton satButton = (ImageButton) view.findViewById(R.id.callButton2);
        DutyRosterItem item = super.getItem(position);
        final ResLifeWorker fridayDuty = item.getFriDuty();
        final ResLifeWorker saturdayDuty = item.getSatDuty();
        LocalDate friday = item.getFriday();
        LocalDate sunday = friday.plusDays(2);
        String weekend = friday.dayOfWeek().getAsShortText() + " " + friday.toString("M/d") + " - " + sunday.dayOfWeek().getAsShortText() + " " + sunday.toString("M/d");
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
