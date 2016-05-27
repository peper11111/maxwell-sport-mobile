package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;

public class ExerciseGroup {
    private String mName;
    private Drawable mIcon;

    public ExerciseGroup(String string, Drawable drawable) {
        mName = string;
        mIcon = drawable;
    }

    public String getName() {
        return mName;
    }

    public Drawable getIcon() {
        return mIcon;
    }
}
