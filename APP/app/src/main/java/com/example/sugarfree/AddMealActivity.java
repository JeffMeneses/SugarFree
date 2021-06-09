package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddMealActivity extends AppCompatActivity {
    private Spinner mMealType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mMealType = findViewById(R.id.spinMealType);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mealType, android.R.layout.simple_spinner_dropdown_item);
        mMealType.setAdapter(adapter);
    }

    public void onClickConfirm(View v)
    {

    }
}