package com.example.sugarfree.utils;

public class Constants {
    public static final String ALERT_INVALID_INPUT = "Entrada inválida!";
    public static final String MSG_INVALID_EMAIL = "Insira um endereço de e-mail válido.";
    public static final String MSG_INVALID_NAME = "Insira um nome com pelomenos "+ Constants.MIN_NAME_LENGTH +" caracteres\n";
    public static final String MSG_INVALID_PASSWORD = "Insira uma senha com pelomenos "+ Constants.MIN_PASSWORD_LENGTH +" caracteres.\n";
    public static final String MSG_PASSWORD_MISMATCH = "As senhas devem coincidir.";

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MIN_PASSWORD_LENGTH = 3;

    public static final int IMAGE_GALLERY_REQUEST = 20;

    // URL REQUESTS
    public static final String POST_REGISTER = "http://192.168.1.148:5000/user/signup";
    public static final String POST_LOGIN = "http://192.168.1.148:5000/user/login";
    public static final String GET_RECIPES = "http://192.168.1.148:5000/recipes";
    public static final String GET_RECIPES_CATEGORY = "http://192.168.1.148:5000/recipesCategory";
}
