package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class Recipe {
    private Bitmap mImageResource;
    private String mTitle;
    private String mLikes;
    private String mCategory;
    private String mTags;
    private String mInstructions;
    private String mIngredients;


    public Recipe(Bitmap imageResource, String title, String likes, String category, String tags, String instructions, String ingredients)
    {
        mImageResource = imageResource;
        mTitle = title;
        mLikes = likes;
        mCategory = category;
        mTags = tags;
        mInstructions = instructions;
        mIngredients = ingredients;
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

    public String getCategory()
    {
        return mCategory;
    }

    public String getTags()
    {
        return mTags;
    }

    public String getInstructions()
    {
        return mInstructions;
    }
}
