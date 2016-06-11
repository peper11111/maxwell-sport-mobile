package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.LoginActivity;
import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

import java.util.ArrayList;

public class SettingsLanguageFragment extends Fragment {
    private MainActivity mContext;
    private int[] items = {R.string.settings_language_polish, R.string.settings_language_english};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_settings_language_title));

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
                String choice = null;
                switch (position) {
                    case 0:
                        choice = "pl";
                        break;
                    case 1:
                        choice = "en";
                        break;
                }
                SharedPreferencesService.putValue(mContext, SharedPreferencesService.settings_language_key, choice);
                LoginActivity.setLocale(mContext, choice);
                mContext.recreate();
                Toast.makeText(mContext, R.string.toast_mgs_language_changed, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
