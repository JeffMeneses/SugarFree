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
    public static final String POST_RECIPES = "http://192.168.1.148:5000/recipes";
    public static final String GET_RECIPES_CATEGORY = "http://192.168.1.148:5000/recipesCategory";
    public static final String GET_RECIPE_BY_ID = "http://192.168.1.148:5000/recipeId";
    public static final String POST_FOOD = "http://192.168.1.148:5000/food";
    public static final String GET_REMOVE_FOOD = "http://192.168.1.148:5000/removeFood";
    public static final String GET_ALL_RECIPE_MENU_BY_ID = "http://192.168.1.148:5000/recipeMenu";
    public static final String POST_RECIPE_MENU = "http://192.168.1.148:5000/recipeMenu";
    public static final String POST_MEAL = "http://192.168.1.148:5000/meal";
    public static final String GET_MEAL_BY_ID = "http://192.168.1.148:5000/mealCategory";
    public static final String GET_REMOVE_RECIPE_MENU = "http://192.168.1.148:5000/removeRecipeMenu";
    public static final String GET_REMOVE_MEAL = "http://192.168.1.148:5000/removeMeal";
    public static final String POST_UPDATE_WEEK_DAYS = "http://192.168.1.148:5000/updateWeekDays";
    public static final String GET_RECIPE_BY_TITLE = "http://192.168.1.148:5000/recipeTitle";
    public static final String GET_RECIPE_MENU_BY_ID = "http://192.168.1.148:5000/shareRecipeMenu";
    public static final String GET_SEARCH_RECIPE_BY_PARTIAL_TITLE = "http://192.168.1.148:5000/partialRecipeTitle";
    public static final String POST_CB_RECOMMENDATION = "http://192.168.1.148:5000/recommendation";
    public static final String POST_CF_RECOMMENDATION = "http://192.168.1.148:5000/cfRecommendation";
    public static final String GET_N_RANDOM_RECIPES = "http://192.168.1.148:5000/randomNRecipes";
    public static final String POST_RECIPE_REVIEW = "http://192.168.1.148:5000/recipeReview";
    public static final String POST_EXTRACT_TEXT_FROM_IMG = "http://192.168.1.148:5000/extractTextFromImage";

    // EXTRA INPUTS
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_INGREDIENTS = "ingredients";
    public static final String EXTRA_INSTRUCTIONS = "instructions";
    public static final String EXTRA_TAGS = "tags";
    public static final String EXTRA_LIKES = "likes";
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_CATEGORY = "category";
}
