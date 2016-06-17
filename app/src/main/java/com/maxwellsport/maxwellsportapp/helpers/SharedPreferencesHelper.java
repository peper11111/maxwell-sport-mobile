package com.maxwellsport.maxwellsportapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

public class SharedPreferencesHelper {
    private static final String TAG = "maxwell-sport";
    private static final int MODE = Context.MODE_PRIVATE;

    public static final String app_user_id_key = "app-user-id";
    public static final String app_username_key = "app-username";
    public static final String profile_user_name_key = "profile-user-name";
    public static final String profile_user_email_key = "profile-user-email";
    public static final String profile_user_active_from_key = "profile-user-active-from";
    public static final String profile_stats_last_training_date_key = "profile-stats-last-training-date";
    public static final String profile_stats_last_training_duration_key = "profile-stats-last-training-duration";
    public static final String profile_stats_average_training_duration_key = "profile-stats-average-training-duration";
    public static final String profile_stats_longest_training_duration_key = "profile-stats-longest-training-duration";
    public static final String profile_stats_last_exercise_amount_key = "profile-stats-last-exercise-amount";
    public static final String profile_stats_average_exercise_amount_key = "profile-stats-average-exercise-amount";
    public static final String profile_stats_biggest_exercise_amount_key = "profile-stats-biggest-exercise-amount";
    public static final String profile_stats_run_number_key = "profile-stats-run-number";
    public static final String profile_stats_last_run_date_key = "profile-stats-last-run-date";
    public static final String profile_stats_last_run_duration_key = "profile-stats-last-run-duration";
    public static final String profile_stats_average_run_duration_key = "profile-stats-average-run-duration";
    public static final String profile_stats_longest_run_duration_key = "profile-stats-longest-run-duration";
    public static final String profile_stats_last_run_distance_key = "profile-stats-last-run-distance";
    public static final String profile_stats_average_run_distance_key = "profile-stats-average-run-distance";
    public static final String profile_stats_longest_run_distance_key = "profile-stats-longest-run-distance";
    public static final String profile_stats_last_run_pace_key = "profile-stats-last-run-pace";
    public static final String profile_stats_average_run_pace_key = "profile-stats-average-run-pace";
    public static final String profile_stats_biggest_run_pace_key = "profile-stats-biggest-run-pace";
    public static final String settings_theme_key = "settings-theme";
    public static final String settings_language_key = "settings-language";
    public static final String settings_default_tab_key = "settings-default-tab";
    public static final String settings_server_address_key = "settings-settings-server-address";
    public static final String settings_server_port_key = "settings-settings-server-port";


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(TAG, MODE);
    }

    private static Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static void putValue(Context context, String key, String value) {
        getEditor(context).putString(key, value).apply();
    }

    public static void putValue(Context context, String key, Set<String> values) {
        getEditor(context).putStringSet(key, values).apply();
    }

    public static void putValue(Context context, String key, int value) {
        getEditor(context).putInt(key, value).apply();
    }

    public static void putValue(Context context, String key, long value) {
        getEditor(context).putLong(key, value).apply();
    }

    public static void putValue(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).apply();
    }

    public static void putValue(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).apply();
    }

    public static void remove(Context context, String key) {
        getEditor(context).remove(key).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static Set<String> getStringSet(Context context, String key, Set<String> defValue) {
        return getSharedPreferences(context).getStringSet(key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static boolean contains(Context context, String key) {
        return getSharedPreferences(context).contains(key);
    }
}
