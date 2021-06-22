package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class RecipeMenuItem {
    private String mId;
    private String mName;
    private String mIdUser;
    private String mWeekDays;

    public RecipeMenuItem(String id, String name, String idUser, String weekDays)
    {
        mId = id;
        mName = name;
        mIdUser = idUser;
        mWeekDays = weekDays;
    }

    public String getId()
    {
        return mId;
    }

    public String getName()
    {
        return mName;
    }

    public String getIdUser()
    {
        return mIdUser;
    }

    public String getWeekDays()
    {
        return mWeekDays;
    }
}
