package com.maxwellsport.maxwellsportapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
        ImageView imageView = (ImageView) convertView.findViewById(R.id.atlas_exercise_list_view_item_icon);
        TextView textView = (TextView) convertView.findViewById(R.id.atlas_exercise_list_view_item_name);
        // Populate the data into the template view using the data object

        imageView.setImageDrawable(exercise.getIcon());
        textView.setText(exercise.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
