package edu.rosehulman.rafinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.RoomEntry;
import edu.rosehulman.rafinder.model.person.Resident;

public class FloorRosterArrayAdapter extends ArrayAdapter<RoomEntry> {
    private final Context context;
    private final int layout;


    public FloorRosterArrayAdapter(Context context, int textViewResourceId, List<RoomEntry> objects) {
        super(context, R.layout.entry_floor_roster, textViewResourceId, objects);
        this.layout = R.layout.entry_floor_roster;
        this.context = context;
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

        TextView roomNumberText = (TextView) view.findViewById(R.id.roomNumberTextView);

        TextView roommate1 = (TextView) view.findViewById(R.id.roommate1);
        TextView roommate2 = (TextView) view.findViewById(R.id.roommate2);
        TextView roommate3 = (TextView) view.findViewById(R.id.roommate3);
        TextView roommate4 = (TextView) view.findViewById(R.id.roommate4);
        TextView roommate5 = (TextView) view.findViewById(R.id.roommate5);

        TextView[] textViews = { roommate1, roommate2, roommate3, roommate4, roommate5 };
        RoomEntry item = super.getItem(position);
        if (item instanceof RoomEntry.Lobby) {
            for (TextView textView : textViews) {
                textView.setVisibility(View.GONE);
                roomNumberText.setTextSize(42);
            }
        } else {
            final Resident[] residents = item.getResidents();
            int numResidents = residents.length;
            for (int i = 0; i < 5; i++) {
                if (numResidents <= i) {
                    textViews[i].setVisibility(View.GONE);
                } else {
                    textViews[i].setText(residents[i].getName());
                }
            }
        }
        roomNumberText.setText(item.getRoomNumber());
        view.refreshDrawableState();
        return view;
    }
}
