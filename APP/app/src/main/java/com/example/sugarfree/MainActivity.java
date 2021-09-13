package com.example.sugarfree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.RecipeAdapter;
import com.example.sugarfree.utils.RecipeItem;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sugarfree.utils.CurrentUser.getCurrentUserSelectedRecipes;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<RecipeItem> mRecipeListCB = new ArrayList<>();
    private ArrayList<RecipeItem> mRecipeListCF = new ArrayList<>();
    private RecyclerView mRecyclerViewCB, mRecyclerViewCF;
    private RecipeAdapter mAdapterCB, mAdapterCF;
    private RecyclerView.LayoutManager mLayoutManagerCB, mLayoutManagerCF;

    private Context mContext;
    private DrawerLayout drawer;

    private TextView mTxtCard1, mTxtCard2, mTxtCard3, mTxtCard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mTxtCard1 = findViewById(R.id.txtCard1);
        mTxtCard2 = findViewById(R.id.txtCard2);
        mTxtCard3 = findViewById(R.id.txtCard3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null)
            navigationView.setCheckedItem(R.id.nav_home);

        buildRecyclerView();
        initiateRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerViewCB = findViewById(R.id.recyclerViewCB);
        mRecyclerViewCB.setHasFixedSize(true);
        //mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManagerCB = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewCF = findViewById(R.id.recyclerViewCF);
        mRecyclerViewCF.setHasFixedSize(true);
        //mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManagerCF = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    }

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();

        String userLikedRecipes = "{"+
                "\"userLikedRecipes\":" + getCurrentUserSelectedRecipes() +
                "}";

        String userRatingRecipes = "{"+
                "\"userRatingRecipes\":" + "[{\"_id\":\"47e2b73cfd224c5fa80cfd2743302b1b\", \"rating\":4},{\"_id\":\"a561f2f9891a426f978f543bd0c57d3f\", \"rating\":4}]" +
                "}";

        apiRequests.postMethod(mContext, userLikedRecipes, Constants.POST_CB_RECOMMENDATION, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) throws JSONException {
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject name = jsonArray.getJSONObject(i);

                    String id = name.getString("_id");
                    String title = name.getString("title");
                    String avgRating = name.getString("avgRating");
                    String countRating = name.getString("countRating");
                    String image = name.getString("image");

                    Bitmap imageBitmap = ImageHandler.convert(image);

                    //mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, title, likes));
                    mRecipeListCB.add(new RecipeItem(id, imageBitmap, title, avgRating, countRating));
                }
                initiateAdapterCB();
            }
        });

        apiRequests.postMethod(mContext, userRatingRecipes, Constants.POST_CF_RECOMMENDATION, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) throws JSONException {
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject name = jsonArray.getJSONObject(i);

                    String id = name.getString("_id");
                    String title = name.getString("title");
                    String avgRating = name.getString("avgRating");
                    String countRating = name.getString("countRating");
                    String image = name.getString("image");

                    Bitmap imageBitmap = ImageHandler.convert(image);

                    //mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, title, likes));
                    mRecipeListCF.add(new RecipeItem(id, imageBitmap, title, avgRating, countRating));
                }
                initiateAdapterCF();
            }
        });
    }

    public void initiateAdapterCB()
    {
        mAdapterCB = new RecipeAdapter(mRecipeListCB);

        mRecyclerViewCB.setLayoutManager(mLayoutManagerCB);
        mRecyclerViewCB.setAdapter(mAdapterCB);

        mAdapterCB.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                RecipeItem clickedItem = mRecipeListCB.get(position);

                intent.putExtra("recipeID", clickedItem.getId());
                startActivity(intent);
            }
        });
    }

    public void initiateAdapterCF()
    {
        mAdapterCF = new RecipeAdapter(mRecipeListCF);

        mRecyclerViewCF.setLayoutManager(mLayoutManagerCF);
        mRecyclerViewCF.setAdapter(mAdapterCF);

        mAdapterCF.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                RecipeItem clickedItem = mRecipeListCF.get(position);

                intent.putExtra("recipeID", clickedItem.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar receita");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipe(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void searchRecipe(String query)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }

    public void onClickBreakfast(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard1.getText().toString());
        startActivity(intent);
    }

    public void onClickLunchDinner(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard2.getText().toString());
        startActivity(intent);
    }

    public void onClickSnacks(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard3.getText().toString());
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.nav_refeicoes:
                startActivity(new Intent(mContext, RecipeMenusActivity.class));
                break;
            case R.id.nav_logoff:
                Toast.makeText(mContext, "Sessão encerrada", Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickRecipeMenu(View v)
    {
        startActivity(new Intent(mContext, RecipeMenusActivity.class));
    }
}