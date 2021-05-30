package com.example.sugarfree.utils;

public class RecipeItem {
    private int mImageResource;
    private String mTitle;
    private String mLikes;

    public RecipeItem(int imageResource, String title, String likes)
    {
        mImageResource = imageResource;
        mTitle = title;
        mLikes = likes;
    }

    public void changeText1(String text)
    {
        mTitle = text;
    }

    public int getImageResource()
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
