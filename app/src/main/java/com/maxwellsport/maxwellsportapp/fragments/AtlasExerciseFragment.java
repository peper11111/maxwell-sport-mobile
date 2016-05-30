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
import com.maxwellsport.maxwellsportapp.adapters.AtlasExerciseListAdapter;
import com.maxwellsport.maxwellsportapp.models.Exercise;

import java.util.ArrayList;

public class AtlasExerciseFragment extends Fragment {
    private AtlasExerciseListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_atlas_exercise, container, false);

        /* Obtain selected exercise group */
        Bundle bundle = getArguments();
        int group = bundle.getInt("exercise-group");

        TypedArray icons = getResources().obtainTypedArray(R.array.atlas_exercise_icon);
        int icons_id = icons.getResourceId(group, 0);
        icons.recycle();
        TypedArray exercise_icons = getResources().obtainTypedArray(icons_id);

        TypedArray names = getResources().obtainTypedArray(R.array.atlas_exercise_name);
        int names_id = names.getResourceId(group, 0);
        names.recycle();
        String[] exercise_names = getResources().getStringArray(names_id);

        TypedArray descriptions = getResources().obtainTypedArray(R.array.atlas_exercise_description);
        int descriptions_id = descriptions.getResourceId(group, 0);
        descriptions.recycle();
        String[] exercise_descriptions = getResources().getStringArray(descriptions_id);

        TypedArray difficulties = getResources().obtainTypedArray(R.array.atlas_exercise_difficulty);
        int difficulties_id = difficulties.getResourceId(group, 0);
        difficulties.recycle();
        int[] exercise_difficulties = getResources().getIntArray(difficulties_id);

        ArrayList<Exercise> array = new ArrayList<>();
        for (int i = 0; i < exercise_names.length; i++) {
            array.add(new Exercise(group, exercise_icons.getDrawable(i), exercise_names[i], exercise_descriptions[i], exercise_difficulties[i]));
        }
        exercise_icons.recycle();

        // Create the adapter to convert the array to views
        mAdapter = new AtlasExerciseListAdapter(getActivity(), array);
        // Attach the adapter to a ListView
        ListView listView = (ListView) v.findViewById(R.id.atlas_exercise_list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Exercise exercise = mAdapter.getItem(position);
                bundle.putSerializable("exercise-class", exercise);
                Fragment fragment = new AtlasExerciseDetailsFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
            }
        });

        return v;
    }
}
