package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickConfirm(View v)
    {
        //TODO: validar dados de cadastro
    }

    public void onClickAddPicture(View v)
    {
        //TODO: abrir galeria para escolher foto
    }

    public void onClickReturn(View v){
        finish();
    }
}