package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.Recipe;
import com.example.sugarfree.utils.RecipeAdapter;
import com.example.sugarfree.utils.RecipeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<RecipeItem> mRecipeList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String mCategoryName;
    private Context mContext;
    private TextView mTitle;
    private ImageView mReturnArrow;

    private TextView mTxtNoResults;
    private ImageView mImgNoResults;

    private String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mContext = getApplicationContext();
        getIncomingIntent();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();

        mTxtNoResults = findViewById(R.id.txtNoResults);
        mImgNoResults = findViewById(R.id.imgNoResults);

        //TODO: método GET para pegar receitas
        buildRecyclerView();
        initiateRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
    }

    public void getIncomingIntent()
    {
        if(getIntent().hasExtra("categoryName"))
        {
            mCategoryName = getIntent().getStringExtra("categoryName");
            mCategoryName = mCategoryName.replaceAll("\n", "");
        }
        if(getIntent().hasExtra("query"))
        {
            mQuery = getIntent().getStringExtra("query");
        }
    }

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();

        if(mQuery.isEmpty() && !mCategoryName.isEmpty())
        {
            apiRequests.getMethod(mContext, Constants.GET_RECIPES_CATEGORY+"/"+mCategoryName, "recipes", new APIrequests.VolleyGETResponseListener() {
                @Override
                public void onError(String message)  {

                }

                @Override
                public void onResponse(JSONArray jsonArray) throws JSONException {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject name = jsonArray.getJSONObject(i);

                        String id = name.getString("_id");
                        String title = name.getString("title");
                        String likes = name.getString("likes");
                        String image = name.getString("image");

                        Bitmap imageBitmap = ImageHandler.convert(image);

                        //mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, title, likes));
                        mRecipeList.add(new RecipeItem(id, imageBitmap, title, likes));
                    }
                    initiateAdapter();
                }
            });
        }
        else
        {
            apiRequests.getMethod(mContext, Constants.GET_SEARCH_RECIPE_BY_PARTIAL_TITLE+"/"+mQuery, "partialSearch", new APIrequests.VolleyGETResponseListener() {
                @Override
                public void onError(String message)  {
                    if(mRecipeList.size() == 0)
                    {
                        mTxtNoResults.setVisibility(View.VISIBLE);
                        mImgNoResults.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onResponse(JSONArray jsonArray) throws JSONException {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject name = jsonArray.getJSONObject(i);

                        String id = name.getString("_id");
                        String title = name.getString("title");
                        String likes = name.getString("likes");
                        String image = name.getString("image");

                        Bitmap imageBitmap = ImageHandler.convert(image);

                        //mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, title, likes));
                        mRecipeList.add(new RecipeItem(id, imageBitmap, title, likes));
                    }
                    initiateAdapter();
                }
            });
        }
    }

    public void initiateAdapter()
    {
        mAdapter = new RecipeAdapter(mRecipeList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                RecipeItem clickedItem = mRecipeList.get(position);

                intent.putExtra("recipeID", clickedItem.getId());
                startActivity(intent);
            }
        });
    }

    public void updateToolbar()
    {
        if(mQuery.isEmpty() && !mCategoryName.isEmpty())
            mTitle.setText(mCategoryName);
        else
            mTitle.setText("Busca: "+mQuery);

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void onClickAddRecipe(View v)
    {
        Intent intent = new Intent(mContext, AddRecipeWrittenActivity.class);
        intent.putExtra("category", mCategoryName);
        startActivity(intent);
        finish();
    }
}