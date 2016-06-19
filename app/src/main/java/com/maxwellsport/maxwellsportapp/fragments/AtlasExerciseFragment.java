package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.DefaultListAdapter;
import com.maxwellsport.maxwellsportapp.models.DefaultListItemModel;
import com.maxwellsport.maxwellsportapp.models.ExerciseModel;

import java.util.ArrayList;

public class AtlasExerciseFragment extends Fragment {
    private MainActivity mContext;
    private DefaultListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_exercise_title));

        View v = inflater.inflate(R.layout.default_list_view, container, false);

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

        ArrayList<DefaultListItemModel> array = new ArrayList<>();
        for (int i = 0; i < exercise_names.length; i++) {
            array.add(new ExerciseModel(exercise_names[i], exercise_icons.getDrawable(i), group, exercise_descriptions[i], exercise_difficulties[i]));
        }
        exercise_icons.recycle();

        // Create the adapter to convert the array to views
        mAdapter = new DefaultListAdapter(mContext, R.layout.list_item_atlas_exercise, array);
        // Attach the adapter to a ListView
        ListView listView = (ListView) v.findViewById(R.id.default_list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                ExerciseModel exercise = (ExerciseModel) mAdapter.getItem(position);
                bundle.putSerializable("exercise-class", exercise);
                Fragment fragment = new AtlasExerciseDetailsFragment();
                fragment.setArguments(bundle);
                mContext.addFragment(fragment);
            }
        });

        return v;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.abc_grow_fade_in_from_bottom);
        } else {
            return AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.abc_fade_out);
        }
    }
}
