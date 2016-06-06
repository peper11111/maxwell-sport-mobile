package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.AtlasExerciseGroupListAdapter;
import com.maxwellsport.maxwellsportapp.models.ExerciseGroup;

import java.util.ArrayList;

public class AtlasExerciseGroupFragment extends Fragment {
    MainActivity mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_exercise_group_title));

        View v = inflater.inflate(R.layout.default_list_view, container, false);

        // Construct the data source
        ArrayList<ExerciseGroup> array = new ArrayList<>();
        String[] groupNames = getResources().getStringArray(R.array.atlas_exercise_group_name);
        TypedArray groupIcons = getResources().obtainTypedArray(R.array.atlas_exercise_group_icon);

        for (int i = 0; i < groupNames.length; i++) {
            array.add(new ExerciseGroup(groupNames[i], groupIcons.getDrawable(i)));
        }
        groupIcons.recycle();

        // Create the adapter to convert the array to views
        AtlasExerciseGroupListAdapter adapter = new AtlasExerciseGroupListAdapter(mContext, array);
        // Attach the adapter to a ListView
        ListView listView = (ListView) v.findViewById(R.id.default_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("exercise-group", position);
                Fragment fragment = new AtlasExerciseFragment();
                fragment.setArguments(bundle);
                mContext.addFragment(fragment);
            }
        });

        return v;
    }
}
