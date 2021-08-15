package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;

import static com.example.sugarfree.utils.CurrentUser.getCurrentUser;

public class AddMealActivity extends AppCompatActivity {
    private Context mContext;

    private Spinner mMealType;
    private TextView mMealName;

    private TextView mTitle;
    private ImageView mReturnArrow;

    String mIdRecipeMenu, mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mContext = getApplicationContext();

        mMealType = findViewById(R.id.spinMealType);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mealType, android.R.layout.simple_spinner_dropdown_item);
        mMealType.setAdapter(adapter);
        mMealName = findViewById(R.id.txtName);

        mIdRecipeMenu = getIntent().getStringExtra("idRecipeMenu");
        mCategory = getIntent().getStringExtra("category");

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();
    }

    public void updateToolbar()
    {
        mTitle.setText("Adicionar");

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void onClickConfirm(View v)
    {
        APIrequests apiRequests = new APIrequests();

        //String idUser =
        String mealName = mMealName.getText().toString();
        String type = mMealType.getSelectedItem().toString();

        String meal = "{"+
                "\"idRecipeMenu\":" + "\"" + mIdRecipeMenu + "\","+
                "\"idUser\":" + "\"" + getCurrentUser() + "\","+
                "\"category\":" + "\"" + mCategory + "\","+
                "\"mealName\":" + "\"" + mealName + "\","+
                "\"type\":" + "\"" + type + "\""+
                "}";

        apiRequests.postMethod(mContext, meal, Constants.POST_MEAL, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(mContext, RecipeMenuDetailsActivity.class);
                //intent.putExtra("RecipeMenuName", recipeMenuName);
                //startActivity(intent);
                finish();
            }
        });
    }
}