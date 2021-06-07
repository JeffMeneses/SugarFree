package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;

public class AddFoodActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTitle;
    private ImageView mReturnArrow;

    private String mMealName;
    private Spinner mUnits;

    private TextView mFoodName, mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mContext = getApplicationContext();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);

        mFoodName = findViewById(R.id.inputFoodName);
        mQuantity = findViewById(R.id.inputQuantity);

        mUnits = findViewById(R.id.spinUnit);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_dropdown_item);
        mUnits.setAdapter(adapter);

        getIncomingIntent();
        updateToolbar();
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

    public String getEnglishMealName()
    {
        switch (mMealName)
        {
            case "Café da Manhã": return "breakfast";
            case "Lanche da Manhã": return "morningSnack";
            case "Almoço": return "lunch";
            case "Lanche da Tarde": return "afternoonSnack";
            case "Jantar": return "dinner";
            case "Lanche da Noite": return "eveningSnack";
        }
        return "Café da Manhã";
    }

    public void onClickConfirm(View v)
    {
        APIrequests apiRequests = new APIrequests();

        String foodName = mFoodName.getText().toString();
        String quantity = mQuantity.getText().toString();
        String unit = mUnits.getSelectedItem().toString();

        String food = "{"+
                "\"idUser\":" + "\"" + 1 + "\","+
                "\"mealName\":" + "\"" + getEnglishMealName() + "\","+
                "\"foodName\":" + "\"" + foodName + "\","+
                "\"quantity\":" + "\"" + quantity + "\","+
                "\"unit\":" + "\"" + unit + "\""+
                "}";

        apiRequests.postMethod(mContext, food, Constants.POST_FOOD, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, MealActivity.class);
                intent.putExtra("mealName", mMealName);
                startActivity(intent);
            }
        });
    }
}