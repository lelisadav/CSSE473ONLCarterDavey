package edu.rosehulman.rafinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.person.Employee;

public class RAListArrayAdapter<T extends Employee> extends ArrayAdapter<T> {
    private final Context context;
    private final List<T> objects;
    private final int layout;
    private RAListArrayAdapterCallbacks mListener;

    public RAListArrayAdapter(Context context, int textViewResourceId, List<T> objects, RAListArrayAdapterCallbacks callbacks) {
        super(context, R.layout.layout_ra_item, textViewResourceId, objects);
        this.layout = R.layout.layout_ra_item;
        this.context = context;
        this.objects = objects;
        this.mListener= callbacks;
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
        TextView nameTV= (TextView) view.findViewById(R.id.myRATextView);
        TextView statusTV= (TextView) view.findViewById(R.id.status);
        ImageButton button= (ImageButton) view.findViewById(R.id.raMoreDetails);
        final T RA= objects.get(position);
        nameTV.setText(RA.getName());
        statusTV.setText(RA.getStatus());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.moreDetailsRequested(RA);
            }
        });
        view.refreshDrawableState();
        return view;
    }
    public interface RAListArrayAdapterCallbacks{
        public void moreDetailsRequested(Employee RA);
    }
}
