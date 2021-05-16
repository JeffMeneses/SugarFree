package com.example.sugarfree.utils;

public class Constants {
    public static final String ALERT_INVALID_INPUT = "Entrada inválida!";
    public static final String MSG_INVALID_EMAIL = "Email inválido. Seu email parece não seguir o padrão correto\n";
    public static final String MSG_INVALID_NAME = "Nome inválido. Seu nome deve conter pelomenos"+ Constants.MIN_NAME_LENGTH +"caracteres\n";
    public static final String MSG_INVALID_PASSWORD = "Senha inválida. Sua senha deve conter pelomenos"+ Constants.MIN_PASSWORD_LENGTH +"caracteres.\n";
    public static final String MSG_PASSWORD_MISMATCH = "As senhas devem coincidir.";

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MIN_PASSWORD_LENGTH = 3;

    public static final int IMAGE_GALLERY_REQUEST = 20;
}
