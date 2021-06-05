package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodMenuActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTitle;
    private ImageView mReturnArrow;

    private TextView mTxtCard1, mTxtCard2, mTxtCard3, mTxtCard4, mTxtCard5, mTxtCard6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        mContext = getApplicationContext();
        mTxtCard1 = findViewById(R.id.txtCard1);
        mTxtCard2 = findViewById(R.id.txtCard2);
        mTxtCard3 = findViewById(R.id.txtCard3);
        mTxtCard4 = findViewById(R.id.txtCard4);
        mTxtCard5 = findViewById(R.id.txtCard5);
        mTxtCard6 = findViewById(R.id.txtCard6);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();
    }

    public void updateToolbar()
    {
        mTitle.setText("Refeições");

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void onClickBreakfast(View v)
    {
        Intent intent = new Intent(mContext, MealActivity.class);
        intent.putExtra("categoryName", mTxtCard1.getText().toString());
        startActivity(intent);
    }

    public void onClickMorningSnack(View v)
    {
        Intent intent = new Intent(mContext, MealActivity.class);
        intent.putExtra("categoryName", mTxtCard2.getText().toString());
        startActivity(intent);
    }

    public void onClickLunch(View v)
    {
        Intent intent = new Intent(mContext, MealActivity.class);
        intent.putExtra("categoryName", mTxtCard3.getText().toString());
        startActivity(intent);
    }

    public void onClickAfternoonSnack(View v)
    {
        Intent intent = new Intent(mContext, MealActivity.class);
        intent.putExtra("categoryName", mTxtCard4.getText().toString());
        startActivity(intent);
    }

    public void onClickDinner(View v)
    {
        Intent intent = new Intent(mContext, MealActivity.class);
        intent.putExtra("categoryName", mTxtCard5.getText().toString());
        startActivity(intent);
    }

    public void onClickEveningSnack(View v)
    {
        Intent intent = new Intent(mContext, MealActivity.class);
        intent.putExtra("categoryName", mTxtCard6.getText().toString());
        startActivity(intent);
    }
}