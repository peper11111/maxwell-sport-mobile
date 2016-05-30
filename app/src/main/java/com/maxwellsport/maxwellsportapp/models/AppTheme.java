package com.maxwellsport.maxwellsportapp.models;

public class AppTheme {
    private String mName;
    private int mColor;

    public AppTheme(String name, int color) {
        mName = name;
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public String getName() {
        return mName;
    }
}
