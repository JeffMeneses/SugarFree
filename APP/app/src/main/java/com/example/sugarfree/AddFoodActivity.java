package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddFoodActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTitle;
    private ImageView mReturnArrow;

    private String mMealName;
    private Spinner mUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);

        getIncomingIntent();
        updateToolbar();

        mUnits = findViewById(R.id.spinUnit);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_dropdown_item);
        mUnits.setAdapter(adapter);
    }

    public void updateToolbar()
    {
        mTitle.setText(mMealName);

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void getIncomingIntent()
    {
        if(getIntent().hasExtra("mealName"))
        {
            mMealName = getIntent().getStringExtra("mealName");
        }
    }

    public void onClickConfirm(View v)
    {

    }
}