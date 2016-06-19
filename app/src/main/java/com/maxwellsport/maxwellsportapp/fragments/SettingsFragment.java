package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        subtitles[0] = getResources().getString(R.string.settings_language_subtitle) + " " + getResources().getString(DataConversionHelper.convertLanguage(SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.settings_language_key, "en")));
        subtitles[1] = getResources().getString(R.string.settings_theme_subtitle) + " " + color_names[SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_theme_key, 7)];
        subtitles[2] = getResources().getString(R.string.settings_default_tab_subtitle) + " " + getResources().getString(DataConversionHelper.convertTab(SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_default_tab_key, 0)));
        subtitles[3] = getResources().getString(R.string.settings_stats_subtitle);
        subtitles[4] = getResources().getString(R.string.settings_server_subtitle) + " " + String.format(Locale.getDefault(), "%s:%s", SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.settings_server_address_key, "10.42.0.1"), SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_server_port_key, 8081));

        SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_theme_key, R.style.CyanAccentColorTheme);

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
                        clearStatistics();
                        Toast.makeText(mContext, R.string.toast_msg_stats_cleared, Toast.LENGTH_SHORT).show();
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

    private void clearStatistics() {
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_run_number_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_last_run_date_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_last_run_duration_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_last_run_distance_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_last_run_pace_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_average_run_duration_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_average_run_distance_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_average_run_pace_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_longest_run_duration_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_longest_run_distance_key);
        SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.profile_stats_biggest_run_pace_key);
    }
}
