package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mContext = getApplicationContext();
        getIncomingIntent();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();

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
        }
    }

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_RECIPES_CATEGORY+"/"+mCategoryName, "recipes", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject name = jsonArray.getJSONObject(i);

                    String title = name.getString("title");
                    String likes = name.getString("likes");
                    String image = name.getString("image");

                    Bitmap imageBitmap = ImageHandler.convert(image);

                    //mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, title, likes));
                    mRecipeList.add(new RecipeItem(imageBitmap, title, likes));
                }
                initiateAdapter();
            }
        });
    }

    public void initiateAdapter()
    {
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

    public void updateToolbar()
    {
        mTitle.setText(mCategoryName);

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }
}