package com.maxwellsport.maxwellsportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.models.DefaultListItemModel;

import java.util.ArrayList;

public class DefaultListAdapter extends ArrayAdapter<DefaultListItemModel> {
    private int mResource;

    public DefaultListAdapter(Context context, int resource, ArrayList<DefaultListItemModel> items) {
        super(context, 0, items);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DefaultListItemModel item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
        }

        if (item.getItemTitle() != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.default_list_item_title);
            textView.setText(item.getItemTitle());
        }
        if (item.getItemSubtitle() != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.default_list_item_subtitle);
            textView.setText(item.getItemSubtitle());
        }
        if (item.getItemColor() != 0) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.default_list_item_color);
            imageView.setBackgroundColor(item.getItemColor());
        }
        if (item.getItemDrawable() != null) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.default_list_item_drawable);
            imageView.setImageDrawable(item.getItemDrawable());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
