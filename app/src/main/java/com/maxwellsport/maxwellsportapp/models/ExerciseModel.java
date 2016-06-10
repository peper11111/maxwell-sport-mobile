package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import java.io.Serializable;

public class ExerciseModel extends DefaultListItemModel implements Serializable {
    private static final long serialVersionUID = -1234551469151804394L;
    private int mGroup;
    private String mDescription;
    private int mDifficulty;

    public ExerciseModel(String title, Drawable icon, int group, String description, int difficulty) {
        mTitle = title;
        mSubtitle = null;
        mColor = 0;
        mIcon = icon;

        mGroup = group;
        mDescription = description;
        mDifficulty = difficulty;
    }

    public int getGroup() {
        return mGroup;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getDifficulty() {
        return mDifficulty;
    }
}
