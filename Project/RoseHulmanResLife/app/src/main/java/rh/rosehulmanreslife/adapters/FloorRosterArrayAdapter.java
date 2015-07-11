package rh.rosehulmanreslife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.List;

import rh.rosehulmanreslife.MainActivity;
import rh.rosehulmanreslife.R;
import rh.rosehulmanreslife.models.DutyRosterItem;
import rh.rosehulmanreslife.models.ResLifeWorker;
import rh.rosehulmanreslife.models.Resident;
import rh.rosehulmanreslife.models.RoomEntry;

/**
 * Created by daveyle on 7/11/2015.
 */
public class FloorRosterArrayAdapter extends ArrayAdapter<RoomEntry>{
    private final Context context;
    private final int layout;


    public FloorRosterArrayAdapter(Context context,
                                  int textViewResourceId, List<RoomEntry> objects) {
        super(context, R.layout.floor_roster_entry, textViewResourceId, objects);
        this.layout = R.layout.floor_roster_entry;
        this.context = context;



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
        TextView roomNumberText = (TextView) view.findViewById(R.id.roomNumberTextView);
        TextView roommate1= (TextView) view.findViewById(R.id.roommate1);
        TextView roommate2= (TextView) view.findViewById(R.id.roommate2);
        TextView roommate3= (TextView) view.findViewById(R.id.roommate3);
        TextView roommate4= (TextView) view.findViewById(R.id.roommate4);
        TextView roommate5= (TextView) view.findViewById(R.id.roommate5);
        TextView[] textViews={roommate1, roommate2, roommate3, roommate4, roommate5};
        RoomEntry item = super.getItem(position);
        if (item instanceof RoomEntry.Lobby){
            for (int i=0; i<textViews.length; i++){
                textViews[i].setVisibility(View.GONE);
                roomNumberText.setTextSize(42);

            }
        }
        else {
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
