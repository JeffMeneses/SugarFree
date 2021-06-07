package com.example.sugarfree.utils;

import android.graphics.Bitmap;

public class FoodItem {
    private String mId;
    private String mFoodName;
    private String mQuantity;
    private String mUnit;

    public FoodItem(String id, String foodName, String quantity, String unit)
    {
        mId = id;
        mFoodName = foodName;
        mQuantity = quantity;
        mUnit = unit;
    }

    public String getId()
    {
        return mId;
    }

    public String getFoodName()
    {
        return mFoodName;
    }

    public String getQuantity()
    {
        return mQuantity;
    }

    public String getUnit()
    {
        return mUnit;
    }
}
