package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarfree.utils.FoodAdapter;
import com.example.sugarfree.utils.FoodItem;

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

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);

        getIncomingIntent();
        updateToolbar();

        mFoodList.add(new FoodItem("1", "Brócolis", "50", "grama(s)"));
        mFoodList.add(new FoodItem("2", "Cenoura", "10", "quilo(s)"));
        mFoodList.add(new FoodItem("3", "Leite", "1", "litro(s)"));
        mFoodList.add(new FoodItem("4", "Água", "500", "ml"));
        mFoodList.add(new FoodItem("5", "Brócolis", "50", "grama(s)"));
        mFoodList.add(new FoodItem("6", "Cenoura", "10", "quilo(s)"));
        mFoodList.add(new FoodItem("7", "Leite", "1", "litro(s)"));
        mFoodList.add(new FoodItem("8", "Água", "500", "ml"));

        mRecyclerView = findViewById(R.id.recyclerViewFood);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
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
        mFoodList.remove(position);
        mAdapter.notifyItemRemoved(position);
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
}