package com.maxwellsport.maxwellsportapp.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.TrainingListAdapter;
import com.maxwellsport.maxwellsportapp.services.TimerService;

import java.util.ArrayList;

public class TrainingDayFragment extends Fragment implements View.OnClickListener{

    private ImageButton pauseButton;
    private ImageButton resumeButton;
    private ImageButton stopButton;
    private View v;

    /* time count */
    protected final static String STATUS_KEY = "status-key";
    public String status;
    private TimerService timerService;

//    TODO: zebrac wlasciwe dane do wyswietlenia
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
        v = inflater.inflate(R.layout.fragment_training_day, container, false);

        /* Inicjalizacja widoków */
        listView = (ListView) v.findViewById(R.id.training_list_view);
        resumeButton = (ImageButton) v.findViewById(R.id.training_resume);
        pauseButton = (ImageButton) v.findViewById(R.id.training_pause);
        stopButton = (ImageButton) v.findViewById(R.id.training_stop);

        for(String s: exeNameArray){
            arrayList.add(s);
        }

        /* Ustawienie adaptera */
        adapter = new TrainingListAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);

        /* Ustawienie timera do przycisków */
        timerService = new TimerService(getActivity() , (TextView) v.findViewById(R.id.time_count));
        onRestoreInstanceState(savedInstanceState);
        status = "running";
        timerService.setupTimerService(status);
        setupTimeButtonListeners();
        timerService.startTimer();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupTimeButtonListeners(){
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "stopped";
                timerService.stopTimer();
                pauseButton.setVisibility(View.INVISIBLE);
                resumeButton.setVisibility(View.VISIBLE);
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "running";
                resumeButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                timerService.startTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    status = "paused";
                    pauseButton.setVisibility(View.INVISIBLE);
                    resumeButton.setVisibility(View.VISIBLE);
                    timerService.pauseTimer();
            }
        });
    }

    @Override
    public void onClick(View v) { }

    /* Fragment lifecycle methods */

    /* Pomocnicza metoda do wczytania danych z Bundle */
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        timerService.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            status = savedInstanceState.getString(STATUS_KEY);
        } else {
            status = "stopped";
        }
    }

    /* Zastopowowanie i rozpoczecie liczenia czasu po zapauzowaniu aplikacji */
    @Override
    public void onResume() {
        super.onResume();
        timerService.startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        timerService.pauseTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        timerService.onSaveInstanceState(outState);
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
