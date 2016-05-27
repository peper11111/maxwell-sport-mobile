package com.maxwellsport.maxwellsportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.models.ExerciseGroup;

import java.util.ArrayList;

public class AtlasExerciseGroupListAdapter extends ArrayAdapter<ExerciseGroup> {

    public AtlasExerciseGroupListAdapter(Context context, ArrayList<ExerciseGroup> exerciseGroups) {
        super(context, 0, exerciseGroups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ExerciseGroup exerciseGroup = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_atlas_exercise_group, parent, false);
        }
        // Lookup view for data population
        ImageView imageView = (ImageView) convertView.findViewById(R.id.atlas_exercise_group_list_view_item_icon);
        TextView textView = (TextView) convertView.findViewById(R.id.atlas_exercise_group_list_view_item_name);
        // Populate the data into the template view using the data object
        imageView.setImageDrawable(exerciseGroup.getIcon());
        textView.setText(exerciseGroup.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}