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

public class EmployeeListArrayAdapter<T extends Employee> extends ArrayAdapter<T> {
    private final Context mContext;
    private final List<T> mObjects;
    private final int mLayout;
    private final EmployeeListArrayAdapterListener mListener;

    public EmployeeListArrayAdapter(Context context, int textViewResourceId, List<T> objects, EmployeeListArrayAdapterListener callbacks) {
        super(context, R.layout.layout_ra_item, textViewResourceId, objects);
        mLayout = R.layout.layout_ra_item;
        mContext = context;
        mObjects = objects;
        mListener = callbacks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView != null ? convertView : inflater.inflate(mLayout, parent, false);
        TextView nameTextView = (TextView) view.findViewById(R.id.myRATextView);
        TextView statusTextView = (TextView) view.findViewById(R.id.status);
        ImageButton button = (ImageButton) view.findViewById(R.id.raMoreDetails);
        final T ra = mObjects.get(position);
        nameTextView.setText(ra.getName());
        statusTextView.setText(ra.getStatus());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.switchToProfile(ra);
            }
        });
        view.refreshDrawableState();
        return view;
    }

    public interface EmployeeListArrayAdapterListener {
        public void switchToProfile(Employee ra);
    }
}
