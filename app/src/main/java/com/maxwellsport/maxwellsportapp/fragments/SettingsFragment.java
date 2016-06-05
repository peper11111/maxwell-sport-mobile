package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;

import java.util.ArrayList;

//TODO: Naprawic animacje i ustawianie tytułu po recreate
public class SettingsFragment extends Fragment {
    MainActivity mContext;
    int[] items = {R.string.settings_language_label, R.string.settings_theme_label, R.string.settings_default_tab_label, R.string.settings_stats_label};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_settings_title));

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
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new SettingsLanguageFragment();
                        break;
                    case 1:
                        fragment = new SettingsThemeFragment();
                        break;
                    case 2:
                        fragment = new SettingsTabFragment();
                        break;
                    case 3:
                        //Clear statistics
                        break;
                }
                if (fragment != null)
                    mContext.addFragment(fragment);
            }
        });
        return v;
    }
}
