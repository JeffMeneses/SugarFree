package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
    private ImageView mReturnArrow;

    String mRecipeMenuName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menu_details);

        mContext = getApplicationContext();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        mRecipeMenuName = getIntent().getStringExtra("recipeMenuName");
        updateToolbar();

        buildRecyclerView();
        initiateRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
    }

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_MEAL_BY_ID+"/"+"fd292e5e9ad24c69ace29106e3a52588"+"/"+"breakfast", "meal", new APIrequests.VolleyGETResponseListener() {
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
        //mMealList.add(new MealItem("1", "Refeição 1", "Entrada", "Café da Manhã"));
        //mMealList.add(new MealItem("1", "Refeição 1", "Prato Principal", "Café da Manhã"));
        //mMealList.add(new MealItem("1", "Refeição 1", "Sobremesa", "Café da Manhã"));
        //initiateAdapter();
    }

    public void initiateAdapter()
    {
        mAdapter = new MealAdapter(mMealList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        /*mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                RecipeMenuItem clickedItem = mRecipeMenuList.get(position);

                APIrequests apiRequests = new APIrequests();
                apiRequests.getMethod(mContext, Constants.GET_RECIPE_BY_ID+"/"+clickedItem.getId(), "recipes", new APIrequests.VolleyGETResponseListener() {
                    @Override
                    public void onError(String message)  {

                    }

                    @Override
                    public void onResponse(JSONArray jsonArray) throws JSONException {
                        JSONObject recipeJson = jsonArray.getJSONObject(0);

                        String title = recipeJson.getString("title");
                        String ingredients = recipeJson.getString("ingredients");
                        String category = recipeJson.getString("category");
                        String instructions = recipeJson.getString("instructions");
                        String tags = recipeJson.getString("tags");
                        String likes = recipeJson.getString("likes");
                        String image = recipeJson.getString("image");

                        //Bitmap imageBitmap = ImageHandler.convert(image);

                        intent.putExtra(Constants.EXTRA_TITLE, title);
                        intent.putExtra(Constants.EXTRA_INGREDIENTS, ingredients);
                        intent.putExtra(Constants.EXTRA_CATEGORY, category);
                        intent.putExtra(Constants.EXTRA_INSTRUCTIONS, instructions);
                        intent.putExtra(Constants.EXTRA_TAGS, tags);
                        intent.putExtra(Constants.EXTRA_LIKES, likes);
                        intent.putExtra(Constants.EXTRA_IMAGE, image);

                        startActivity(intent);
                    }
                });
            }
        });*/
    }

    public void updateToolbar()
    {
        mTitle.setText(mRecipeMenuName);

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void onClickAddMeal(View v)
    {
        Intent intent = new Intent(mContext, AddMealActivity.class);
        //intent.putExtra("idRecipeMenu", mIdRecipeMenu);
        //intent.putExtra("category", mCategory);
        startActivity(intent);
        finish();
    }
}