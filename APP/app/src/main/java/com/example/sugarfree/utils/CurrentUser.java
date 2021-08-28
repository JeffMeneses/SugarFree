package com.example.sugarfree.utils;

public class CurrentUser {
    public static String userID;
    public static String selectedRecipes;

    public static void setCurrentUser(String iD, String recipes)
    {
        userID = iD;
        selectedRecipes = recipes;
    }

    public static String getCurrentUser()
    {
        return userID;
    }
    public static String getCurrentUserSelectedRecipes()
    {
        return selectedRecipes;
    }
}
