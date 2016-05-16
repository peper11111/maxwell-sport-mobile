package com.maxwellsport.maxwellsportapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class TrainingListAdapter extends BaseAdapter {

    private ArrayList<String> exeNameList;
    private ArrayList<String> weightList;
    private ArrayList<String> setsList;
    private ArrayList<String> repsList;
    private Context context;

//    TODO: zrobic odpowiedni konstruktor dla adaptera
    public TrainingListAdapter(Context applicationContext, ArrayList<String> exeName){
        super();
        context = applicationContext;
        this.exeNameList = exeName;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.training_day_list_item,null);
        }

        TextView exeName = (TextView) convertView.findViewById(R.id.row_textView1);
//        TODO: dodac powtorzenia, serie, ciezar do listView_item
        TextView weight = (TextView) convertView.findViewById(R.id.weight);
        TextView sets = (TextView) convertView.findViewById(R.id.sets);
        TextView reps = (TextView) convertView.findViewById(R.id.reps);

        ImageView exeImg = (ImageView) convertView.findViewById(R.id.row_imageView1);
        ImageView popup = (ImageView) convertView.findViewById(R.id.row_click_imageView1);

//        TODO: podpiac odpowiednie listy
        try {
            exeName.setText(exeNameList.get(position));
            weight.setText("weight: 60% CM");
            sets.setText("sets: 3");
            reps.setText("reps: 12");
            popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.row_click_imageView1:
                            PopupMenu popupMenu = new PopupMenu(context,v);
                            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_training_item,popupMenu.getMenu());
                            popupMenu.show();
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch(item.getItemId()){
                                        case R.id.exercise_description:
                                            // TODO odpalic opis cwiczenia w popup
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
}
