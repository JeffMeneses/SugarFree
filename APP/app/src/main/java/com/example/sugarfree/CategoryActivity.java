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
    private RecyclerView mReciclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ArrayList<RecipeItem> recipeList = new ArrayList<>();
        //TODO: método GET para pegar receitas

        //PLACE HOLDER
        recipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Cenoura", "5 Likes"));
        recipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Lentilha", "6 Likes"));
        recipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Feijão", "7 Likes"));
        recipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Cenoura", "5 Likes"));
        recipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Lentilha", "6 Likes"));
        recipeList.add(new RecipeItem(R.drawable.ic_default_image, "Sopa de Feijão", "7 Likes"));

        mReciclerView = findViewById(R.id.reciclerView);
        mReciclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new RecipeAdapter(recipeList);

        mReciclerView.setLayoutManager(mLayoutManager);
        mReciclerView.setAdapter(mAdapter);
    }
}