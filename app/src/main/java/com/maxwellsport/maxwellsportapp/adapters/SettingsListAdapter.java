package com.maxwellsport.maxwellsportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.models.AppTheme;

import java.util.ArrayList;


public class SettingsListAdapter extends ArrayAdapter<AppTheme> {
    public SettingsListAdapter(Context context, ArrayList<AppTheme> exercises) {
        super(context, 0, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AppTheme appTheme = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_settings, parent, false);
        }
        // Lookup view for data population
        ImageView imageView = (ImageView) convertView.findViewById(R.id.theme_color_icon);
        TextView textView = (TextView) convertView.findViewById(R.id.theme_color_name);
        // Populate the data into the template view using the data object

        imageView.setBackgroundColor(appTheme.getColor());
        textView.setText(appTheme.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
