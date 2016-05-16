package com.maxwellsport.maxwellsportapp.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.TrainingListAdapter;

import java.util.ArrayList;

public class TrainingDayFragment extends Fragment {

//    TODO: zebrac wlasciwe dane i zrobic clickable imageView
    // testowe dane
    String [] exeNameArray = {"Exercise 1","Exercise 2","Exercise 3","Exercise 4","Exercise 5",
            "Exercise 6","Exercise 7","Exercise 8","Exercise 9","Exercise 10"};
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training_day, container, false);

        ListView listView = (ListView) v.findViewById(R.id.training_list_view);
        for(String s: exeNameArray){
            arrayList.add(s);
        }
        TrainingListAdapter adapter = new TrainingListAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);


        return v;
    }

}
