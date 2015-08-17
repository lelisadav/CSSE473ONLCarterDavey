package edu.rosehulman.rafinder.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.rafinder.MainActivity;
import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.person.Employee;

public class SearchResultArrayAdapter extends ArrayAdapter<Employee> {
    private final Context mContext;
    private final List<Employee> mObjects;
    private final int mLayout;

    public SearchResultArrayAdapter(Context context, int resource, List<Employee> objects) {
        super(context, resource, objects);
        mLayout = resource;
        mContext = context;
        mObjects = objects;
    }

    public void refresh(List<Employee> newResults) {
        mObjects.clear();
        mObjects.addAll(newResults);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = (convertView != null) ? convertView : inflater.inflate(mLayout, parent, false);
        ImageView typeIcon = (ImageView) view.findViewById(R.id.typeIcon);
        TextView nameText = (TextView) view.findViewById(R.id.nameTextView);
        TextView roomText = (TextView) view.findViewById(R.id.roomTextView);
        TextView statusText = (TextView) view.findViewById(R.id.dutyStatusTextView);
        Drawable personDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            personDrawable = mContext.getResources().getDrawable(R.drawable.ic_person, mContext.getTheme());
        } else {
            personDrawable = mContext.getResources().getDrawable(R.drawable.ic_person);
        }

        final Employee emp = mObjects.get(position);
        nameText.setText(emp.getName());
        roomText.setText(mContext.getString(R.string.profile_room_format, emp.getHall(), emp.getRoom()));
        statusText.setText(emp.getStatus());
        typeIcon.setImageBitmap(emp.getProfilePicture());
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).switchToProfile(emp);
            }
        });

        view.refreshDrawableState();
        return view;
    }
}
