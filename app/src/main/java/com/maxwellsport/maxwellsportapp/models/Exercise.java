package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import java.io.Serializable;

public class Exercise implements Serializable {
    private static final long serialVersionUID = -1234551469151804394L;
    private int mGroup;
    private String mName;
    private transient Drawable mIcon;
    private String mDescription;
    private int mDifficulty;
    private int mReps;
    private int mWeight;
    private int mSets;


    public Exercise(int group, Drawable icon, String name, String description, int difficulty) {
        mGroup = group;
        mIcon = icon;
        mName = name;
        mDescription = description;
        mDifficulty = difficulty;
    }

    public Exercise(int reps, int sets, int weight, String name){
        mReps = reps;
        mSets = sets;
        mWeight = weight;
        mName = name;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmSets() {
        return mSets;
    }

    public void setmSets(int mSets) {
        this.mSets = mSets;
    }

    public int getmWeight() {
        return mWeight;
    }

    public void setmWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public int getmReps() {
        return mReps;
    }

    public void setmReps(int mReps) {
        this.mReps = mReps;
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
