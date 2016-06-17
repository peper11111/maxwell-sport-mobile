package com.maxwellsport.maxwellsportapp.models;

import android.graphics.drawable.Drawable;

public class DefaultListItemModel {
    public String mTitle;
    public String mSubtitle;
    public Drawable mIcon;
    public int mColor;

    public DefaultListItemModel() {
        
    }

    public DefaultListItemModel(String title, Drawable icon) {
        mTitle = title;
        mSubtitle = null;
        mIcon = icon;
        mColor = 0;
    }

    public DefaultListItemModel(String title, String subtitle) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = null;
        mColor = 0;
    }

    public DefaultListItemModel(String title, int color) {
        mTitle = title;
        mSubtitle = null;
        mColor = color;
        mIcon = null;
    }

    public String getItemTitle() {
        return mTitle;
    }

    public String getItemSubtitle() {
        return mSubtitle;
    }

    public Drawable getItemDrawable() {
        return mIcon;
    }

    public int getItemColor() {
        return mColor;
    }
}
