package com.maxwellsport.maxwellsportapp.adapters;


import android.content.Context;
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
import com.maxwellsport.maxwellsportapp.animations.FlipAnimation;

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

            /* Inicjalizacja zmiennych do holdera */
            holder.exeName = (TextView) convertView.findViewById(R.id.row_textView1);
            holder.weight = (TextView) convertView.findViewById(R.id.weight);
            holder.sets = (TextView) convertView.findViewById(R.id.sets);
            holder.reps = (TextView) convertView.findViewById(R.id.reps);
            holder.imageFront = (ImageView) convertView.findViewById(R.id.flip_front);
            holder.imageBack = (ImageView) convertView.findViewById(R.id.flip_back);
            holder.popup = (ImageButton) convertView.findViewById(R.id.row_click_imageView1);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


//        TODO: podpiac odpowiednie listy
        try {

            /* Zapisanie wartości pól tekstowych */
            holder.exeName.setText(exeNameList.get(position));

            /* Zmiana widoczności obrazka na podstawie pozycji, zeby recycler nie zmieniał widoku */

            if(positionList.get(position) != 0) {
                /* jeśli cwiczenie odznaczone */
                holder.imageFront.setVisibility(View.GONE);
                holder.imageBack.setVisibility(View.VISIBLE);
            }
            else {
                /* jesli cwiczenie jeszcze nie zrobione */
                holder.imageBack.setVisibility(View.GONE);
                holder.imageFront.setVisibility(View.VISIBLE);
            }

            /* Animacja obrazka */

            final ViewHolder finalHolder = holder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(positionList.get(position) != 0) {
                        positionList.set(position, 0);
                        flipCard(finalConvertView);
                    }
                    else {
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
                    switch (v.getId()){
                        case R.id.row_click_imageView1:
                            v.findViewById(v.getId()).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()){
                                        case MotionEvent.ACTION_DOWN:
                                            finalHolder.popup.setBackgroundColor(context.getResources().getColor(R.color.profile_tile_color));
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
                                                        default:
                                                            break;
                                                    }
                                                    return true;
                                                }
                                            });
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            finalHolder.popup.setBackgroundColor(context.getResources().getColor(R.color.profile_tile_color_secondary));
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

    /* Animation methods */

    public void onCardClick(View view)
    {
        flipCard(view);
    }

    private void flipCard(View v)
    {
        View rootLayout =  v.findViewById(R.id.item_list_root_laytout);
        View imageFace =  v.findViewById(R.id.flip_front);
        View imageBack =  v.findViewById(R.id.flip_back);

        FlipAnimation flipAnimation = new FlipAnimation(imageFace, imageBack);

        if (imageFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public class ViewHolder{
        TextView exeName;
        TextView weight;
        TextView sets;
        TextView reps;
        ImageView imageFront;
        ImageView imageBack;
        ImageButton popup;
    }
}
