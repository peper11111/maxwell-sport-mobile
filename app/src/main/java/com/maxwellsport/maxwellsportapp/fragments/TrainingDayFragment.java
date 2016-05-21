package com.maxwellsport.maxwellsportapp.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.TrainingListAdapter;

import java.util.ArrayList;

public class TrainingDayFragment extends Fragment {

//    TODO: zebrac wlasciwe dane
    // testowe dane
    String [] exeNameArray = {"Zginanie przedramion ze sztangielkami trzymanymi neutralnie","Zginanie przedramion ze sztangielkami z obrotem nadgarstka","Exercise 3","Exercise 4","Exercise 5",
            "Exercise 6","Exercise 7","Exercise 8","Exercise 9","Exercise 10"};
    ArrayList<String> arrayList = new ArrayList<>();
    ListView listView;
    TrainingListAdapter adapter;

    // TODO: Pobrac liste z adaptera i przekazac do statystyk
    /* Lista skonczonych cwiczen */
    ArrayList<Integer> positionList;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training_day, container, false);


        listView = (ListView) v.findViewById(R.id.training_list_view);

        for(String s: exeNameArray){
            arrayList.add(s);
        }

        /*
        * Setting adapter
        */

        adapter = new TrainingListAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        return v;


    }

    /*
     * Save Fragment state
    */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /* Zapisanie pozycji listy */
        int index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();

        outState.putIntegerArrayList("positionList",adapter.getPositionList());
        outState.putInt("index", index);
        outState.putInt("top", top);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int index = -1;
        int top = 0;

        if(savedInstanceState != null){
            /* Przywrocenie poprzedniego stanu (pozycii) listy */
            index = savedInstanceState.getInt("index",-1);
            top = savedInstanceState.getInt("top",0);
            adapter.setPositionList(savedInstanceState.getIntegerArrayList("positionList"));
        }
        if(index != -1){
            listView.setSelectionFromTop(index, top);
        }

    }
}
