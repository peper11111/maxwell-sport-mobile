package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class ExerciseModel extends DefaultListItemModel implements Serializable {
    private static final long serialVersionUID = -1234551469151804394L;
    private int mGroup;
    private String mDescription;
    private int mDifficulty;
    private int mReps;
    private int mWeight;
    private int mSets;


    public ExerciseModel(String title, Drawable icon, int group, String description, int difficulty) {
        mTitle = title;
        mSubtitle = null;
        mColor = 0;
        mIcon = icon;

        mGroup = group;
        mDescription = description;
        mDifficulty = difficulty;
    }

    public ExerciseModel(int reps, int sets, int weight, String title) {
        mReps = reps;
        mSets = sets;
        mWeight = weight;
        mTitle = title;
    }


    public String getmName() {
        return mTitle;
    }

    public void setmName(String mName) {
        this.mTitle = mName;
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

    public String getDescription() {
        return mDescription;
    }

    public int getDifficulty() {
        return mDifficulty;
    }
}
