package com.example.sugarfree.utils;

public class CurrentUser {
    public static String userID;

    public static void setCurrentUser(String iD)
    {
        userID = iD;
    }

    public static String getCurrentUser()
    {
        return userID;
    }
}
