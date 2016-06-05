package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import java.io.Serializable;

public class Exercise implements Serializable {
    private static final long serialVersionUID = -1234551469151804394L;
    private int mGroup;
    private int mOrderInGroup;
    private String mName;
    private transient Drawable mIcon;
    private String mDescription;
    private int mDifficulty;

    public Exercise(int group, Drawable icon, String name, String description, int difficulty) {
        mGroup = group;
        mIcon = icon;
        mName = name;
        mDescription = description;
        mDifficulty = difficulty;
    }

    public int getGroup() {
        return mGroup;
    }

    public String getName() {
        return mName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getDifficulty() {
        return mDifficulty;
    }
}
