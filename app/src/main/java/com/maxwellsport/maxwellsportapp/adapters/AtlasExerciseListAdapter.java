package com.maxwellsport.maxwellsportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.models.Exercise;

import java.util.ArrayList;

public class AtlasExerciseListAdapter extends ArrayAdapter<Exercise> {
    public AtlasExerciseListAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Exercise exercise = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_atlas_exercise, parent, false);
        }
        // Lookup view for data population
        ImageView imageView = (ImageView) convertView.findViewById(R.id.atlas_list_view_item_drawable);
        TextView textView = (TextView) convertView.findViewById(R.id.atlas_list_view_item_string);
        // Populate the data into the template view using the data object
        imageView.setImageDrawable(exercise.getIcon());
        textView.setText(exercise.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
