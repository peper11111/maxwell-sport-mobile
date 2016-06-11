package com.maxwellsport.maxwellsportapp.services;

import com.maxwellsport.maxwellsportapp.R;

import java.util.Locale;

public class DataConversionService {
    private static Locale mLocale = Locale.US;

    public static String convertTime(long time) {
        /* Wywietlanie w minutach */
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(mLocale, "%02d:%02d", minutes, seconds);
    }

    public static String convertDistance(float distance) {
        /* Wyswietlanie w kilometrach */
        float kilometers = distance / 1000;
        return String.format(mLocale, "%.02f", kilometers);
    }

    public static String convertPace(float pace) {
        return String.format(mLocale, "%.02f", pace);
    }

    public static int convertLanguage(String code) {
        if (code.equals("pl"))
            return R.string.settings_language_polish;
        else
            return R.string.settings_language_english;
    }

    public static int convertTheme(int style) {
        int theme = R.style.CyanAccentColorTheme;
        switch (style) {
            case 0:
                theme = R.style.RedAccentColorTheme;
                break;
            case 1:
                theme = R.style.PinkAccentColorTheme;
                break;
            case 2:
                theme = R.style.PurpleAccentColorTheme;
                break;
            case 3:
                theme = R.style.DeepPurpleAccentColorTheme;
                break;
            case 4:
                theme = R.style.IndigoAccentColorTheme;
                break;
            case 5:
                theme = R.style.BlueAccentColorTheme;
                break;
            case 6:
                theme = R.style.LightBlueAccentColorTheme;
                break;
            case 7:
                theme = R.style.CyanAccentColorTheme;
                break;
            case 8:
                theme = R.style.TealAccentColorTheme;
                break;
            case 9:
                theme = R.style.GreenAccentColorTheme;
                break;
            case 10:
                theme = R.style.LightGreenAccentColorTheme;
                break;
            case 11:
                theme = R.style.LimeAccentColorTheme;
                break;
            case 12:
                theme = R.style.YellowAccentColorTheme;
                break;
            case 13:
                theme = R.style.AmberAccentColorTheme;
                break;
            case 14:
                theme = R.style.OrangeAccentColorTheme;
                break;
            case 15:
                theme = R.style.DeepOrangeAccentColorTheme;
                break;
            case 16:
                theme = R.style.BrownAccentColorTheme;
                break;
        }
        return theme;
    }

    public static int convertTab(int tab) {
        int value = R.string.nav_profile;
        switch (tab) {
            case 0:
                value = R.string.nav_profile;
                break;
            case 1:
                value = R.string.nav_training;
                break;
            case 2:
                value = R.string.nav_cardio;
                break;
            case 3:
                value = R.string.nav_atlas;
                break;
        }
        return value;
    }
}
