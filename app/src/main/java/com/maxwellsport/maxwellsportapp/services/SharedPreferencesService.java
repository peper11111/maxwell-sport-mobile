package com.maxwellsport.maxwellsportapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

public class SharedPreferencesService {
    private static final String TAG = "maxwell-sport";
    private static final int MODE = Context.MODE_PRIVATE;

    public static final String app_user_id_key = "app-user-id";
    public static final String app_username_key = "app-username";
    public static final String profile_user_name_key = "profile-user-name";
    public static final String profile_user_email_key = "profile-user-email";
    public static final String profile_user_active_from_key = "profile-user-active-from";
    public static final String profile_stats_last_training_date_key = "profile-training-last_date";
    public static final String profile_stats_last_training_duration_key = "profile-training-last-training-duration";
    public static final String profile_stats_average_training_duration_key = "profile-training-average-training-duration";
    public static final String profile_stats_longest_training_duration_key ="profile-training-longest-training-duration";
    public static final String profile_stats_last_exercise_amount_key = "profile-training-last-exercise-amount";
    public static final String profile_stats_average_exercise_amount_key ="profile-training-average-exercise-amount";
    public static final String profile_stats_biggest_exercise_amount_key ="profile-training-biggest-exercise-amount";
    public static final String profile_stats_last_run_date_key ="profile-running-last-run-date";
    public static final String profile_stats_last_run_duration_key ="profile-running-last-run-duration";
    public static final String profile_stats_average_run_duration_key ="profile-running-average-run-duration";
    public static final String profile_stats_longest_run_duration_key ="profile-running-longest-run-duration";
    public static final String profile_stats_last_run_distance_key ="profile-running-last-run-distance";
    public static final String profile_stats_average_run_distance_key ="profile-running-average-run-distance";
    public static final String profile_stats_longest_run_distance_key ="profile-running-longest-run-distance";
    public static final String profile_stats_last_run_pace_key ="profile-running-last-run-pace";
    public static final String profile_stats_average_run_pace_key ="profile-running-average-run-pace";
    public static final String profile_stats_biggest_run_pace_key ="profile-running-biggest-run-pace";
    public static final String settings_theme_key = "settings-theme";
    public static final String settings_language_key = "settings-language";
    public static final String settings_default_tab_key = "settings-default-tab";
    /* json data */
    public static final String downloaded_training_json_key = "training_json";
    public static final String current_training_number_key = "current_training";
    public static final String is_training_downloaded_key = "training_done";
    public static final String training_body_part_key = "body_part_list";

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

    public static void saveValue(Context context, String key, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }
}
