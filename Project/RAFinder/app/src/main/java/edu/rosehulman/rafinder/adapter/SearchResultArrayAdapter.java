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

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.model.Hall;
import edu.rosehulman.rafinder.model.SearchResultItem;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.Resident;

public class SearchResultArrayAdapter extends ArrayAdapter<SearchResultItem> {
    private final Context context;
    private final List<SearchResultItem> objects;
    private final int layout;

    public SearchResultArrayAdapter(Context context, int textViewResourceId, List<SearchResultItem> objects) {
        super(context, R.layout.layout_search_item, textViewResourceId, objects);
        this.layout = R.layout.layout_search_item;
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
        ImageView typeIcon = (ImageView) view.findViewById(R.id.typeIcon);
        TextView nameText = (TextView) view.findViewById(R.id.nameTextView);
        TextView roomText = (TextView) view.findViewById(R.id.roomTextView);
        TextView statusText = (TextView) view.findViewById(R.id.dutyStatusTextView);
        if (objects.get(position) instanceof Resident) {
            Drawable personDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                personDrawable = context.getResources().getDrawable(R.drawable.ic_person, context.getTheme());
            } else {
                personDrawable = context.getResources().getDrawable(R.drawable.ic_person);
            }


            if (objects.get(position) instanceof Employee) {
                Employee emp = (Employee) objects.get(position);
                nameText.setText(emp.getName());
                roomText.setText(emp.getFloor());
                statusText.setText(emp.getLocation());
                statusText.setVisibility(View.VISIBLE);
            } else {
                Resident res = (Resident) objects.get(position);
                nameText.setText(res.getName());
                roomText.setText(res.getRoom());
                statusText.setVisibility(View.GONE);
            }
            typeIcon.setBackground(personDrawable);
        } else {
            Drawable hallDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                hallDrawable = context.getResources().getDrawable(R.drawable.ic_phone, context.getTheme());
            } else {
                hallDrawable = context.getResources().getDrawable(R.drawable.ic_phone);
            }
            Hall hall = (Hall) objects.get(position);
            nameText.setVisibility(View.GONE);
            roomText.setVisibility(View.GONE);
            statusText.setText(hall.getName());
            statusText.setVisibility(View.VISIBLE);
            typeIcon.setBackground(hallDrawable);
        }
        view.refreshDrawableState();
        return view;
    }
}
