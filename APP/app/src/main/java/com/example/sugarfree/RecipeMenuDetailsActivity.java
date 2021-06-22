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
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.MealAdapter;
import com.example.sugarfree.utils.MealItem;
import com.example.sugarfree.utils.RecipeItem;
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

    private CheckBox mBtnDom, mBtnSeg, mBtnTer, mBtnQua, mBtnQui, mBtnSex, mBtnSab;

    String mRecipeMenuName, mIdRecipeMenu, selectedCategory, mWeekDays;


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
        mWeekDays = getIntent().getStringExtra("weekDays");
        updateToolbar();

        mCircleBreakfast = findViewById(R.id.circleBreakFast);
        mCircleLunch = findViewById(R.id.circleLunch);
        mCircleDinner = findViewById(R.id.circleDinner);
        mImgBreakfast = findViewById(R.id.imgBreakfast);
        mImgLunch = findViewById(R.id.imgLunch);
        mImgDinner = findViewById(R.id.imgDinner);

        mBtnDom = findViewById(R.id.btnDom);
        mBtnSeg = findViewById(R.id.btnSeg);
        mBtnTer = findViewById(R.id.btnTer);
        mBtnQua = findViewById(R.id.btnQua);
        mBtnQui = findViewById(R.id.btnQui);
        mBtnSex = findViewById(R.id.btnSex);
        mBtnSab = findViewById(R.id.btnSab);
        updateCheckboxes();

        buildRecyclerView();
        initiateRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPause() {
        super.onPause();

        APIrequests apiRequests = new APIrequests();
        String weekDaysValue = getWeekDays();

        String weekDays = "{"+
                "\"idRecipeMenu\":" + "\"" + mIdRecipeMenu + "\","+
                "\"weekDays\":" + "\"" + weekDaysValue + "\""+
                "}";

        apiRequests.postMethod(mContext, weekDays, Constants.POST_UPDATE_WEEK_DAYS, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                //Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {
                //Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateCheckboxes()
    {
        if(mWeekDays.contains("Dom")) mBtnDom.setChecked(true);
        if(mWeekDays.contains("Seg")) mBtnSeg.setChecked(true);
        if(mWeekDays.contains("Ter")) mBtnTer.setChecked(true);
        if(mWeekDays.contains("Qua")) mBtnQua.setChecked(true);
        if(mWeekDays.contains("Qui")) mBtnQui.setChecked(true);
        if(mWeekDays.contains("Sex")) mBtnSex.setChecked(true);
        if(mWeekDays.contains("Sab")) mBtnSab.setChecked(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getWeekDays()
    {
        ArrayList<String> weekDays = new ArrayList<String>();

        if(mBtnDom.isChecked()) weekDays.add("Dom");
        if(mBtnSeg.isChecked()) weekDays.add("Seg");
        if(mBtnTer.isChecked()) weekDays.add("Ter");
        if(mBtnQua.isChecked()) weekDays.add("Qua");
        if(mBtnQui.isChecked()) weekDays.add("Qui");
        if(mBtnSex.isChecked()) weekDays.add("Sex");
        if(mBtnSab.isChecked()) weekDays.add("Sab");

        String result = TextUtils.join(", ", weekDays);
        if(result.isEmpty()) result = mWeekDays;

        return result;
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateMeals();
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
                //Toast.makeText(mContext, "Pesquisando receita", Toast.LENGTH_SHORT).show();
                MealItem clickedItem = mMealList.get(position);
                searchRecipe(clickedItem);
            }

            @Override
            public void onRemoveClick(int position) {
                removeItem(position);
            }
        });
    }

    public void searchRecipe(MealItem clickedItem)
    {
        /*Intent intent = new Intent(mContext, DetailsActivity.class);

        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_RECIPE_BY_TITLE+"/"+clickedItem.getName(), "recipe", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {
                Toast.makeText(mContext, "Ops, receita indisponpivel no app", Toast.LENGTH_SHORT).show();
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
        });*/

        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("query", clickedItem.getName());
        startActivity(intent);
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
                //Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                Toast.makeText(mContext, "Refeição removida com sucesso", Toast.LENGTH_SHORT).show();
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
            intent.putExtra("idRecipeMenu", mIdRecipeMenu);
            intent.putExtra("category", selectedCategory);
            startActivity(intent);
        }
        else
            Toast.makeText(mContext, "Ops, você chegou ao limite de refeições para essa categoria", Toast.LENGTH_SHORT).show();
    }
}