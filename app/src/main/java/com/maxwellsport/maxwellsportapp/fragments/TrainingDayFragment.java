package com.maxwellsport.maxwellsportapp.fragments;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.TrainingListAdapter;

import java.util.ArrayList;

public class TrainingDayFragment extends Fragment {

//    TODO: zebrac wlasciwe dane i zrobic clickable
    // testowe dane
    String [] exeNameArray = {"Zginanie przedramion ze sztangielkami trzymanymi neutralnie","Zginanie przedramion ze sztangielkami z obrotem nadgarstka","Exercise 3","Exercise 4","Exercise 5",
            "Exercise 6","Exercise 7","Exercise 8","Exercise 9","Exercise 10"};
    ArrayList<String> arrayList = new ArrayList<>();
    ImageView image;

    // lista kliknietych pozycji, tam gdzie 0 to nie zrobione cwiczenie
    ArrayList<Integer> positionList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training_day, container, false);

//         inicjujemy liste z pozycjami
        for (int i=0; i < exeNameArray.length; i++){
            positionList.add(0);
        }

        final ListView listView = (ListView) v.findViewById(R.id.training_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // jesli klikniete i zaznaczone to w liscie jest pozycja kliknieta
                // i teraz po drugim kliknieciu trzeba to wyczyscic
                image = (ImageView) view.findViewById(R.id.row_imageView1);
                if(positionList.get(position) != 0) {
                    positionList.set(position, 0);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view.findViewById(R.id.row_imageView1),"rotationY",0f,180f);
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                    image.setColorFilter(null);
                    image.setImageResource(R.drawable.maxwell);
                }
                else { // jak nie zaznaczone to zaznaczamy
                    if (position == 0)
                        positionList.set(position, 1);
                    else
                        positionList.set(position, position);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view.findViewById(R.id.row_imageView1), "rotationY", 180f, 0f);
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                    image.setImageResource(R.drawable.ic_checked);
                    image.setColorFilter(Color.GREEN);
                }

            }
        });


        for(String s: exeNameArray){
            arrayList.add(s);
        }
        TrainingListAdapter adapter = new TrainingListAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        return v;
    }

}
