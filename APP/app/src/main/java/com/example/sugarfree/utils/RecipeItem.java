package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class RecipeItem {
    private String mId;
    private Bitmap mImageResource;
    private String mTitle;
    private String mLikes;

    public RecipeItem(String id, Bitmap imageResource, String title, String likes)
    {
        mId = id;
        mImageResource = imageResource;
        mTitle = title;
        mLikes = likes;
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

    public String getLikes()
    {
        return mLikes;
    }
}
