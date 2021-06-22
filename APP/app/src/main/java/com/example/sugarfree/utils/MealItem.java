package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class MealItem {
    private String mId;
    private String mName;
    private String mType;
    private String mCategory;

    public MealItem(String id, String name, String type, String category)
    {
        mId = id;
        mName = name;
        mType = type;
        mCategory = category;
    }

    public String getId()
    {
        return mId;
    }

    public String getName()
    {
        return mName;
    }

    public String getType()
    {
        return mType;
    }

    public String getCategory()
    {
        return mCategory;
    }
}
