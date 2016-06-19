package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.DefaultListAdapter;
import com.maxwellsport.maxwellsportapp.models.DefaultListItemModel;
import com.maxwellsport.maxwellsportapp.helpers.DataConversionHelper;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;

import java.util.ArrayList;

public class SettingsThemeFragment extends Fragment {
    private MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();

        View v = inflater.inflate(R.layout.default_list_view, container, false);

        TypedArray colors = getResources().obtainTypedArray(R.array.theme_color);
        String[] names = getResources().getStringArray(R.array.theme_color_name);
        ArrayList<DefaultListItemModel> labels = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            labels.add(new DefaultListItemModel(names[i], colors.getColor(i, 0)));
        }
        colors.recycle();

        DefaultListAdapter adapter = new DefaultListAdapter(mContext, R.layout.list_item_settings_theme, labels);

        ListView listView = (ListView) v.findViewById(R.id.default_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.settings_theme_key, position);
                int style = DataConversionHelper.convertTheme(position);
                mContext.setTheme(style);
                mContext.recreate();
                Toast.makeText(mContext, R.string.toast_msg_theme_changed, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mContext.setTitle(getResources().getString(R.string.toolbar_settings_theme_title));
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
