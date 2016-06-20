package com.maxwellsport.maxwellsportapp.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.animations.FlipAnimation;
import com.maxwellsport.maxwellsportapp.fragments.AtlasExerciseDetailsFragment;
import com.maxwellsport.maxwellsportapp.models.ExerciseModel;

import java.util.ArrayList;

public class TrainingDayListAdapter extends BaseAdapter {

    private Context mContext;
    private MainActivity mainActivityContext;

    private ArrayList<ExerciseModel> mExerciseList;

    /* Lista kliknietych pozycji, tam gdzie 0 to nie zrobione cwiczenie */
    ArrayList<Integer> positionList = new ArrayList<>();


    public TrainingDayListAdapter(Context applicationContext, ArrayList<ExerciseModel> exerciseList) {
        super();
        mContext = applicationContext;
        mainActivityContext = (MainActivity) applicationContext;
        this.mExerciseList = exerciseList;

        for (int i = 0; i < mExerciseList.size(); i++) {
            positionList.add(0);
        }
    }

    public ArrayList<Integer> getPositionList() {
        return positionList;
    }

    public void setPositionList(ArrayList<Integer> positionList) {
        this.positionList = positionList;
    }

    @Override
    public int getCount() {
        return mExerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mExerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_training_day, null);

            /* Inicjalizacja zmiennych do holdera */
            holder.exeName = (TextView) convertView.findViewById(R.id.training_list_item_exercise_name);
            holder.weight = (TextView) convertView.findViewById(R.id.training_list_item_weight);
            holder.sets = (TextView) convertView.findViewById(R.id.training_list_item_sets);
            holder.reps = (TextView) convertView.findViewById(R.id.training_list_item_reps);
            holder.imageFront = (ImageView) convertView.findViewById(R.id.training_flip_front);
            holder.imageBack = (ImageView) convertView.findViewById(R.id.training_flip_back);
            holder.popup = (ImageButton) convertView.findViewById(R.id.training_list_item_popup);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


//        TODO: podpiac odpowiednie listy
        try {

            /* Zapisanie wartości pól tekstowych */
            holder.exeName.setText(mExerciseList.get(position).getmName());
            holder.weight.setText(Integer.toString(mExerciseList.get(position).getmWeight()));
            holder.sets.setText(Integer.toString(mExerciseList.get(position).getmSets()));
            holder.reps.setText(Integer.toString(mExerciseList.get(position).getmReps()));


            /* Zmiana widoczności obrazka na podstawie pozycji, zeby recycler nie zmieniał widoku */

            if (positionList.get(position) != 0) {
                /* jeśli cwiczenie odznaczone */
                holder.imageFront.setVisibility(View.GONE);
                holder.imageBack.setVisibility(View.VISIBLE);
            } else {
                /* jesli cwiczenie jeszcze nie zrobione */
                holder.imageBack.setVisibility(View.GONE);
                holder.imageFront.setVisibility(View.VISIBLE);
            }

            /* Animacja obrazka */

            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (positionList.get(position) != 0) {
                        positionList.set(position, 0);
                        flipCard(finalConvertView);
                    } else {
                        if (position == 0)
                            positionList.set(position, 1);
                        else
                            positionList.set(position, position);
                        flipCard(finalConvertView);
                    }
                }
            });

            /* Popup menu */
            holder.popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext, v);
                    popupMenu.getMenuInflater().inflate(R.menu.fragment_training_day_popup, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.training_exercise_description:
                                    Bundle bundle = new Bundle();
                                    ExerciseModel exercise = (ExerciseModel) getItem(position);
                                    bundle.putSerializable("exercise-class", exercise);
                                    Fragment fragment = new AtlasExerciseDetailsFragment();
                                    fragment.setArguments(bundle);
                                    mainActivityContext.addFragment(fragment);
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    /* Animation methods */

    private void flipCard(View v) {
        View rootLayout = v.findViewById(R.id.training_flip_item_layout);
        View imageFace = v.findViewById(R.id.training_flip_front);
        View imageBack = v.findViewById(R.id.training_flip_back);


        FlipAnimation flipAnimation = new FlipAnimation(imageFace, imageBack);

        if (imageFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    private class ViewHolder {
        TextView exeName;
        TextView weight;
        TextView sets;
        TextView reps;
        ImageView imageFront;
        ImageView imageBack;
        ImageButton popup;
    }
}
