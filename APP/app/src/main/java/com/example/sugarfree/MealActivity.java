package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.FoodAdapter;
import com.example.sugarfree.utils.FoodItem;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.RecipeAdapter;
import com.example.sugarfree.utils.RecipeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MealActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTitle;
    private ImageView mReturnArrow;

    private String mMealName;

    private ArrayList<FoodItem> mFoodList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        mContext = getApplicationContext();
        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);

        getIncomingIntent();
        updateToolbar();

        /*mFoodList.add(new FoodItem("1", "Brócolis", "50", "grama(s)"));
        mFoodList.add(new FoodItem("2", "Cenoura", "10", "quilo(s)"));
        mFoodList.add(new FoodItem("3", "Leite", "1", "litro(s)"));
        mFoodList.add(new FoodItem("4", "Água", "500", "ml"));
        mFoodList.add(new FoodItem("5", "Brócolis", "50", "grama(s)"));
        mFoodList.add(new FoodItem("6", "Cenoura", "10", "quilo(s)"));
        mFoodList.add(new FoodItem("7", "Leite", "1", "litro(s)"));
        mFoodList.add(new FoodItem("8", "Água", "500", "ml"));*/

        mRecyclerView = findViewById(R.id.recyclerViewFood);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        initiateRecyclerView();
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

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_MEAL_BY_ID+"/"+1+"/"+getEnglishMealName(), "meal", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {
                Toast.makeText(mContext, "Hum, algo deu errado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject name = jsonArray.getJSONObject(i);

                    String id = name.getString("idFood");
                    String foodName = name.getString("foodName");
                    String quantity = name.getString("quantity");
                    String unit = name.getString("unit");

                    mFoodList.add(new FoodItem(id, foodName, quantity, unit));
                }
                initiateAdapter();
            }
        });
    }

    public void initiateAdapter()
    {
        mAdapter = new FoodAdapter(mFoodList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onRemoveClick(int position) {
                removeItem(position);
            }
        });
    }

    public void removeItem(int position)
    {
        FoodItem removedItem = mFoodList.get(position);
        mFoodList.remove(position);
        mAdapter.notifyItemRemoved(position);

        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_REMOVE_FOOD+"/"+1+"/"+getEnglishMealName()+"/"+removedItem.getId(), "meal", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {
                Toast.makeText(mContext, "Hum, algo deu errado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject name = jsonArray.getJSONObject(i);

                    //String id = name.getString("_id");
                    String foodName = name.getString("foodName");
                    String quantity = name.getString("quantity");
                    String unit = name.getString("unit");

                    mFoodList.add(new FoodItem("1", foodName, quantity, unit));
                }
                initiateAdapter();
            }
        });
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

    public void onClickAddFood(View v)
    {
        Intent intent = new Intent(mContext, AddFoodActivity.class);
        intent.putExtra("mealName", mTitle.getText().toString());
        startActivity(intent);
        finish();
    }
}