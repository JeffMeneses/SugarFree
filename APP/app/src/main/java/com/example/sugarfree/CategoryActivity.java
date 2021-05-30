package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sugarfree.utils.RecipeAdapter;
import com.example.sugarfree.utils.RecipeItem;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<RecipeItem> mRecipeList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //TODO: método GET para pegar receitas

        //PLACE HOLDER
        mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Cenoura", "5 Likes"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Lentilha", "6 Likes"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Feijão", "7 Likes"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Cenoura", "5 Likes"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Lentilha", "6 Likes"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Feijão", "7 Likes"));

        buildRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new RecipeAdapter(mRecipeList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mRecipeList.get(position).changeText1("Clicked");
                mAdapter.notifyItemChanged(position);
            }
        });
    }
}