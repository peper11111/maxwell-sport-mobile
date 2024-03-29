package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;

import java.util.ArrayList;

public class SettingsTabFragment extends Fragment {
    private int[] items = {R.string.nav_profile, R.string.nav_training, R.string.nav_cardio, R.string.nav_atlas};
    private MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_settings_tab_title));

        View v = inflater.inflate(R.layout.default_list_view, container, false);

        ArrayList<String> labels = new ArrayList<>();
        for (int item : items)
            labels.add(getResources().getString(item));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, labels);

        ListView listView = (ListView) v.findViewById(R.id.default_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.settings_default_tab_key, position);
                Toast.makeText(mContext, R.string.toast_msg_default_tab_changed, Toast.LENGTH_SHORT).show();
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
