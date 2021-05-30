package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class RecipeItem {
    private Bitmap mImageResource;
    private String mTitle;
    private String mLikes;

    public RecipeItem(Bitmap imageResource, String title, String likes)
    {
        mImageResource = imageResource;
        mTitle = title;
        mLikes = likes;
    }

    public void changeText1(String text)
    {
        mTitle = text;
    }

    public Bitmap getImageResource()
    {
        return mImageResource;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getLikes()
    {
        return mLikes;
    }
}
