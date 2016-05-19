package com.maxwellsport.maxwellsportapp.adapters;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.R;

import java.util.ArrayList;

public class TrainingListAdapter extends BaseAdapter {

    private ArrayList<String> exeNameList;
    private ArrayList<String> weightList;
    private ArrayList<String> setsList;
    private ArrayList<String> repsList;
    private Context context;

    /* Lista kliknietych pozycji, tam gdzie 0 to nie zrobione cwiczenie */
    ArrayList<Integer> positionList = new ArrayList<>();

//    TODO: zrobic odpowiedni konstruktor dla adaptera
    public TrainingListAdapter(Context applicationContext, ArrayList<String> exeName){
        super();
        context = applicationContext;
        this.exeNameList = exeName;

        /*
        *   Inicjalizacja listy z pozycjami
        */
        for (int i=0; i < exeNameList.size(); i++){
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
        return exeNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return exeNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.training_day_list_item,null);

            holder.exeName = (TextView) convertView.findViewById(R.id.row_textView1);
            holder.weight = (TextView) convertView.findViewById(R.id.weight);
            holder.sets = (TextView) convertView.findViewById(R.id.sets);
            holder.reps = (TextView) convertView.findViewById(R.id.reps);
            holder.image = (ImageView) convertView.findViewById(R.id.row_imageView1);
            holder.popup = (ImageButton) convertView.findViewById(R.id.row_click_imageView1);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


//        TODO: podpiac odpowiednie listy
        try {
            /* Zapisanie wartości pól tekstowych */
            holder.exeName.setText(exeNameList.get(position));
            holder.weight.setText("weight: 60% CM");
            holder.sets.setText("sets: 3");
            holder.reps.setText("reps: 12");

            if(convertView.isSelected()){
                convertView.setBackgroundColor(Color.GRAY);
            }else{
                convertView.setBackgroundColor(Color.WHITE);
            }

//          TODO: ustawienie obrazkow cwiczen


            /* Zmiana obrazka z zapisaniem pozycji */

            if(positionList.get(position) != 0) {
                holder.image.setImageResource(R.drawable.ic_checked);
                holder.image.setColorFilter(Color.GREEN);
            }
            else {
                holder.image.setImageResource(R.drawable.maxwell);
                holder.image.setColorFilter(null);
            }

            final ViewHolder finalHolder = holder;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(positionList.get(position) != 0) {
                        positionList.set(position, 0);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v.findViewById(R.id.row_imageView1),"rotationY",0f,180f);
                        objectAnimator.setDuration(400);
                        objectAnimator.start();
                        finalHolder.image.setColorFilter(null);
                        finalHolder.image.setImageResource(R.drawable.maxwell);
                    }
                    else {
                        if (position == 0)
                            positionList.set(position, 1);
                        else
                            positionList.set(position, position);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v.findViewById(R.id.row_imageView1), "rotationY", 180f, 0f);
                        objectAnimator.setDuration(400);
                        objectAnimator.start();
                        finalHolder.image.setImageResource(R.drawable.ic_checked);
                        finalHolder.image.setColorFilter(Color.GREEN);
                    }
                }
            });


            /* Popup menu */

            holder.popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.row_click_imageView1:
                            v.findViewById(v.getId()).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()){
                                        case MotionEvent.ACTION_DOWN:
                                            finalHolder.popup.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                                            PopupMenu popupMenu = new PopupMenu(context,v);
                                            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_training_item,popupMenu.getMenu());
                                            popupMenu.show();
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch(item.getItemId()){
                                                        case R.id.exercise_description:
                                                            // TODO odpalic opis cwiczenia w popup
                                                            Toast.makeText(context,"w popup", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case R.id.exe_sec:
                                                            // jakas druga czynnosc
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            finalHolder.popup.setBackgroundColor(Color.WHITE);
                                            break;
                                    }
                                    return true;
                                }
                            });

                        break;

                        default:
                            break;
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    public class ViewHolder{
        TextView exeName;
        TextView weight;
        TextView sets;
        TextView reps;
        ImageView image;
        ImageButton popup;
    }
}
