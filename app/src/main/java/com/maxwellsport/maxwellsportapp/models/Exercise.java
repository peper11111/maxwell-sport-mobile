package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Exercise implements Serializable {
    private static final long serialVersionUID = -1234551469151804394L;
    private int mGroup;
    private String mName;
    private Drawable mIcon;
    private String mDescription;

    public Exercise(int group, String name, Drawable icon) {
        mGroup = group;
        mName = name;
        mIcon = icon;
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

    public void setDescription(String description) {
        mDescription = description;
    }
}
