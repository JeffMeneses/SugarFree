package com.example.sugarfree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.RecipeAdapter;
import com.example.sugarfree.utils.RecipeItem;
import com.example.sugarfree.utils.RecipeMenuAdapter;
import com.example.sugarfree.utils.RecipeMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeMenusActivity extends AppCompatActivity {
    private Context mContext;

    private ArrayList<RecipeMenuItem> mRecipeMenuList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecipeMenuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mTitle;
    private ImageView mReturnArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menus);

        mContext = getApplicationContext();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();

        buildRecyclerView();
        initiateRecyclerView();
    }

    public void onClickAddRecipeMenu(View v)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeMenusActivity.this);
        alertDialog.setTitle("Criar Novo Cardápio");
        alertDialog.setMessage("Se organize com seus próprios cardápios personalizados");

        final EditText input = new EditText(RecipeMenusActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint("Nome do Cardápio");
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                addNewRecipeMenu(name);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alertDialog.show();
    }

    public void updateToolbar()
    {
        mTitle.setText("Cardápios");

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
    }

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_RECIPE_MENU_BY_ID+"/"+1, "recipeMenu", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject recipeMenu = jsonArray.getJSONObject(i);

                    String id = recipeMenu.getString("_id");
                    String name = recipeMenu.getString("name");
                    String idUser = recipeMenu.getString("idUser");
                    String weekDays = recipeMenu.getString("weekDays");

                    mRecipeMenuList.add(new RecipeMenuItem(id, name, idUser, weekDays));
                }
                initiateAdapter();
            }
        });
        //mRecipeMenuList.add(new RecipeMenuItem("1", "Cardápio 1", "1", "Terça-feira"));
        //mRecipeMenuList.add(new RecipeMenuItem("1", "Cardápio 2", "1", "Terça-feira"));
        //initiateAdapter();
    }

    public void initiateAdapter()
    {
        mAdapter = new RecipeMenuAdapter(mRecipeMenuList);

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

    public void addNewRecipeMenu(String recipeMenuName)
    {
        APIrequests apiRequests = new APIrequests();

        //String idUser = mFoodName.getText().toString();

        String recipeMenu = "{"+
                "\"idUser\":" + "\"" + 1 + "\","+
                "\"name\":" + "\"" + recipeMenuName + "\""+
                "}";

        apiRequests.postMethod(mContext, recipeMenu, Constants.POST_RECIPE_MENU, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();

                //Intent intent = new Intent(mContext, MealActivity.class);
                //intent.putExtra("mealName", mMealName);
                //startActivity(intent);
                //finish();
            }
        });
    }
}