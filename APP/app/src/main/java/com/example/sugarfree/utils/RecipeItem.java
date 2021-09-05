package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class RecipeItem {
    private String mId;
    private Bitmap mImageResource;
    private String mTitle;
    private String mAvgRating;

    private boolean isChecked = false;

    public RecipeItem(String id, Bitmap imageResource, String title, String avgRating)
    {
        mId = id;
        mImageResource = imageResource;
        mTitle = title;
        mAvgRating = avgRating;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public String getId()
    {
        return mId;
    }

    public Bitmap getImageResource()
    {
        return mImageResource;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getAvgRating()
    {
        return mAvgRating;
    }
}
