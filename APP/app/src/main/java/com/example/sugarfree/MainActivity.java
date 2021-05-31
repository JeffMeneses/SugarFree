package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTxtCard1, mTxtCard2, mTxtCard3, mTxtCard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mTxtCard1 = findViewById(R.id.txtCard1);
        mTxtCard2 = findViewById(R.id.txtCard2);
        mTxtCard3 = findViewById(R.id.txtCard3);
        mTxtCard4 = findViewById(R.id.txtCard4);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onClickBreakfast(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard1.getText().toString());
        startActivity(intent);
    }

    public void onClickLunchDinner(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard2.getText().toString());
        startActivity(intent);
    }

    public void onClickSnacks(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard3.getText().toString());
        startActivity(intent);
    }

    public void onClickRecommended(View v)
    {
        //Intent intent = new Intent(mContext, CategoryActivity.class);
        //intent.putExtra("categoryName", mTxtCard4.getText().toString());
        //startActivity(intent);
        Toast.makeText(mContext, "Recomendados selecionado", Toast.LENGTH_LONG);
    }
}