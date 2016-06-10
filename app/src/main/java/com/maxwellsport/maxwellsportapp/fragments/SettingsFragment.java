package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.DefaultListAdapter;
import com.maxwellsport.maxwellsportapp.models.DefaultListItemModel;
import com.maxwellsport.maxwellsportapp.services.DataConversionService;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

import java.util.ArrayList;
import java.util.Locale;

//TODO: Naprawic animacje i ustawianie tytułu po recreate
public class SettingsFragment extends Fragment {
    MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_settings_title));

        View v = inflater.inflate(R.layout.default_list_view, container, false);

        String[] items = getResources().getStringArray(R.array.settings_options);
        String[] color_names = getResources().getStringArray(R.array.theme_color_name);
        String[] subtitles = new String[items.length];

        subtitles[0] = getResources().getString(R.string.settings_language_subtitle) + " " + getResources().getString(DataConversionService.convertLanguage(SharedPreferencesService.getString(mContext, SharedPreferencesService.settings_language_key, "en")));
        subtitles[1] = getResources().getString(R.string.settings_theme_subtitle) + " " + color_names[SharedPreferencesService.getInt(mContext, SharedPreferencesService.settings_theme_key, 7)];
        subtitles[2] = getResources().getString(R.string.settings_default_tab_subtitle) + " " + getResources().getString(DataConversionService.convertTab(SharedPreferencesService.getInt(mContext, SharedPreferencesService.settings_default_tab_key, 0)));
        subtitles[3] = getResources().getString(R.string.settings_stats_subtitle);
        subtitles[4] = getResources().getString(R.string.settings_server_subtitle) + " " + String.format(Locale.getDefault(), "%s:%s", SharedPreferencesService.getString(mContext, SharedPreferencesService.settings_server_address_key, "10.42.0.1"), SharedPreferencesService.getInt(mContext, SharedPreferencesService.settings_server_port_key, 10000));

        SharedPreferencesService.getInt(mContext, SharedPreferencesService.settings_theme_key, R.style.CyanAccentColorTheme);

        ArrayList<DefaultListItemModel> labels = new ArrayList<>();
        for (int i = 0; i < items.length; i++)
            labels.add(new DefaultListItemModel(items[i], subtitles[i]));

        DefaultListAdapter adapter = new DefaultListAdapter(mContext, R.layout.list_item_settings, labels);

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
                        //TODO: Wyczyscic statystyki
                        break;
                    case 4:
                        fragment = new SettingsServerFragment();
                        break;
                }
                if (fragment != null)
                    mContext.addFragment(fragment);
            }
        });
        return v;
    }
}
