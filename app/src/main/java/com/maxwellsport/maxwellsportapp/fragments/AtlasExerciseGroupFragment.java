package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.AtlasExerciseGroupListAdapter;
import com.maxwellsport.maxwellsportapp.models.ExerciseGroup;

import java.util.ArrayList;

public class AtlasExerciseGroupFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_atlas_exercise_group, container, false);

        // Construct the data source
        ArrayList<ExerciseGroup> array = new ArrayList<>();
        String[] strings = getResources().getStringArray(R.array.atlas_exercise_group_name);
        TypedArray drawables = getResources().obtainTypedArray(R.array.atlas_exercise_group_icon);

        for (int i = 0; i < strings.length; i++) {
            array.add(new ExerciseGroup(strings[i], drawables.getDrawable(i)));
        }
        drawables.recycle();

        // Create the adapter to convert the array to views
        AtlasExerciseGroupListAdapter adapter = new AtlasExerciseGroupListAdapter(getActivity(), array);
        // Attach the adapter to a ListView
        ListView listView = (ListView) v.findViewById(R.id.atlas_exercise_group_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("exercise-group", position);
                Fragment fragment = new AtlasExerciseFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
            }
        });

        listView.setAdapter(adapter);

        return v;
    }
}
