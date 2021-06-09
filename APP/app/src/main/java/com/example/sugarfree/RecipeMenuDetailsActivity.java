package com.example.sugarfree;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.MealAdapter;
import com.example.sugarfree.utils.MealItem;
import com.example.sugarfree.utils.RecipeMenuAdapter;
import com.example.sugarfree.utils.RecipeMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeMenuDetailsActivity extends AppCompatActivity {
    private Context mContext;

    private ArrayList<MealItem> mMealList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MealAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mTitle;
    private ImageView mReturnArrow, mCircleBreakfast, mImgBreakfast, mCircleLunch, mImgLunch, mCircleDinner, mImgDinner;

    String mRecipeMenuName, mIdRecipeMenu, selectedCategory;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menu_details);

        mContext = getApplicationContext();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        mRecipeMenuName = getIntent().getStringExtra("recipeMenuName");
        mIdRecipeMenu = getIntent().getStringExtra("idRecipeMenu");
        updateToolbar();

        mCircleBreakfast = findViewById(R.id.circleBreakFast);
        mCircleLunch = findViewById(R.id.circleLunch);
        mCircleDinner = findViewById(R.id.circleDinner);
        mImgBreakfast = findViewById(R.id.imgBreakfast);
        mImgLunch = findViewById(R.id.imgLunch);
        mImgDinner = findViewById(R.id.imgDinner);

        buildRecyclerView();
        initiateRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickBreakfast(View v)
    {
        selectedCategory = "breakfast";
        mImgBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));
        mCircleBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.yellow));

        mImgLunch.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.red));
        mCircleLunch.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));

        mImgDinner.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.blue));
        mCircleDinner.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));

        updateMeals();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickLunch(View v)
    {
        selectedCategory = "lunch";
        mImgBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.yellow));
        mCircleBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));

        mImgLunch.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));
        mCircleLunch.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.red));

        mImgDinner.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.blue));
        mCircleDinner.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));
        updateMeals();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickDinner(View v)
    {
        selectedCategory = "dinner";
        mImgBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.yellow));
        mCircleBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));

        mImgLunch.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.red));
        mCircleLunch.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));

        mImgDinner.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));
        mCircleDinner.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.blue));

        updateMeals();
    }

    public void updateMeals()
    {
        mMealList.clear();
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_MEAL_BY_ID+"/"+mIdRecipeMenu+"/"+selectedCategory, "meal", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject meal = jsonArray.getJSONObject(i);

                    String id = meal.getString("idMeal");
                    String name = meal.getString("mealName");
                    String type = meal.getString("type");

                    mMealList.add(new MealItem(id, name, type, "breakfast"));
                }
                initiateAdapter();
            }
        });
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initiateRecyclerView()
    {
        selectedCategory = "breakfast";
        mImgBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.white));
        mCircleBreakfast.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.yellow));
        updateMeals();
    }

    public void initiateAdapter()
    {
        mAdapter = new MealAdapter(mMealList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO pesquisar receita
                Toast.makeText(mContext, "Pesquisando receita", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRemoveClick(int position) {
                removeItem(position);
            }
        });
    }

    public void removeItem(int position)
    {
        MealItem removedItem = mMealList.get(position);
        mMealList.remove(position);
        mAdapter.notifyItemRemoved(position);

        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_REMOVE_MEAL+"/"+mIdRecipeMenu+"/"+removedItem.getId()+"/"+selectedCategory, "recipeMenu", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {
                Toast.makeText(mContext, "Hum, algo deu errado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                Toast.makeText(mContext, "Refeição removida com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateToolbar()
    {
        mTitle.setText(mRecipeMenuName);

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void onClickAddMeal(View v)
    {
        if(mMealList.size() < 3) {
            Intent intent = new Intent(mContext, AddMealActivity.class);
            //intent.putExtra("idRecipeMenu", mIdRecipeMenu);
            //intent.putExtra("category", mCategory);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(mContext, "Ops, você chegou ao limite de refeições para essa categoria", Toast.LENGTH_LONG).show();
    }
}