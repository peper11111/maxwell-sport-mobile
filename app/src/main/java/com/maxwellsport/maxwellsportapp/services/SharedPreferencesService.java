package com.maxwellsport.maxwellsportapp.services;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesService {
    public static String TAG = "maxwellsport";

    public static void saveValue(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveValue(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
}
