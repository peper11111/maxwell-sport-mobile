package com.maxwellsport.maxwellsportapp.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.SettingsThemeListAdapter;
import com.maxwellsport.maxwellsportapp.models.AppTheme;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

import java.util.ArrayList;

public class SettingsThemeFragment extends Fragment {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = getActivity();
        View v = inflater.inflate(R.layout.default_list_view, container, false);

        TypedArray colors = getResources().obtainTypedArray(R.array.theme_color);
        String[] names = getResources().getStringArray(R.array.theme_color_name);
        ArrayList<AppTheme> labels = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            labels.add(new AppTheme(names[i], colors.getColor(i, 0)));
        }
        colors.recycle();

        SettingsThemeListAdapter adapter = new SettingsThemeListAdapter(mContext, labels);

        ListView listView = (ListView) v.findViewById(R.id.default_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int style = R.style.CyanAccentColorTheme;
                switch (position) {
                    case 0:
                        style = R.style.RedAccentColorTheme;
                        break;
                    case 1:
                        style = R.style.PinkAccentColorTheme;
                        break;
                    case 2:
                        style = R.style.PurpleAccentColorTheme;
                        break;
                    case 3:
                        style = R.style.DeepPurpleAccentColorTheme;
                        break;
                    case 4:
                        style = R.style.IndigoAccentColorTheme;
                        break;
                    case 5:
                        style = R.style.BlueAccentColorTheme;
                        break;
                    case 6:
                        style = R.style.LightBlueAccentColorTheme;
                        break;
                    case 7:
                        style = R.style.CyanAccentColorTheme;
                        break;
                    case 8:
                        style = R.style.TealAccentColorTheme;
                        break;
                    case 9:
                        style = R.style.GreenAccentColorTheme;
                        break;
                    case 10:
                        style = R.style.LightGreenAccentColorTheme;
                        break;
                    case 11:
                        style = R.style.LimeAccentColorTheme;
                        break;
                    case 12:
                        style = R.style.YellowAccentColorTheme;
                        break;
                    case 13:
                        style = R.style.AmberAccentColorTheme;
                        break;
                    case 14:
                        style = R.style.OrangeAccentColorTheme;
                        break;
                    case 15:
                        style = R.style.DeepOrangeAccentColorTheme;
                        break;
                    case 16:
                        style = R.style.BrownAccentColorTheme;
                        break;
                }
                SharedPreferencesService.putValue(mContext, SharedPreferencesService.settings_theme_key, style);
                getActivity().setTheme(style);
                getActivity().recreate();
                Toast.makeText(mContext, R.string.toast_msg_theme_changed, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
