package com.maxwellsport.maxwellsportapp.services;

import java.util.Locale;

public class DataConversionService {
    public static String convertTime(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
