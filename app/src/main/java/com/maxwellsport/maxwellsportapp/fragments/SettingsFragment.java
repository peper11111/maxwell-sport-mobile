package com.maxwellsport.maxwellsportapp.fragments;

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
import com.maxwellsport.maxwellsportapp.adapters.SettingsListAdapter;
import com.maxwellsport.maxwellsportapp.models.AppTheme;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        ArrayList<AppTheme> array = new ArrayList<>();

        TypedArray colors = getResources().obtainTypedArray(R.array.theme_color);
        String[] names = getResources().getStringArray(R.array.theme_color_name);

        for (int i = 0; i < names.length; i++) {
            array.add(new AppTheme(names[i], colors.getColor(i, 0)));
        }
        colors.recycle();

        SettingsListAdapter adapter = new SettingsListAdapter(getActivity(), array);
        ListView listView = (ListView) v.findViewById(R.id.theme_color_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int style = 7;
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
                SharedPreferencesService.saveValue(getActivity(), "app-theme", style);
                refreshAppTheme(style);
            }
        });

        return v;
    }

    private void refreshAppTheme(int style) {
        getActivity().setTheme(style);
        getActivity().recreate();
        Toast.makeText(getActivity(), R.string.theme_change_info, Toast.LENGTH_SHORT).show();
    }
}
