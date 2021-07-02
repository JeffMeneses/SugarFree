package com.example.sugarfree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.RecipeItem;
import com.example.sugarfree.utils.RecipeMenuAdapter;
import com.example.sugarfree.utils.RecipeMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sugarfree.utils.CurrentUser.getCurrentUser;

public class RecipeMenusActivity extends AppCompatActivity {
    private Context mContext;

    private ArrayList<RecipeMenuItem> mRecipeMenuList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecipeMenuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mTitle;
    private ImageView mReturnArrow;

    private  String mRecipeMenuText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menus);

        mContext = getApplicationContext();

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();

        buildRecyclerView();
        //initiateRecyclerView();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateRecipeMenuList();
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
        apiRequests.getMethod(mContext, Constants.GET_ALL_RECIPE_MENU_BY_ID+"/"+getCurrentUser(), "recipeMenu", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject recipeMenu = jsonArray.getJSONObject(i);

                    String id = recipeMenu.getString("idRecipeMenu");
                    String name = recipeMenu.getString("name");
                    String idUser = recipeMenu.getString("idUser");
                    String weekDays = recipeMenu.getString("weekDays");

                    mRecipeMenuList.add(new RecipeMenuItem(id, name, idUser, weekDays));
                }
                initiateAdapter();
            }
        });
    }

    public void updateRecipeMenuList()
    {
        mRecipeMenuList.clear();
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_ALL_RECIPE_MENU_BY_ID+"/"+getCurrentUser(), "recipeMenu", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject recipeMenu = jsonArray.getJSONObject(i);

                    String id = recipeMenu.getString("idRecipeMenu");
                    String name = recipeMenu.getString("name");
                    String idUser = recipeMenu.getString("idUser");
                    String weekDays = recipeMenu.getString("weekDays");

                    mRecipeMenuList.add(new RecipeMenuItem(id, name, idUser, weekDays));
                }
                //mAdapter.notifyItemInserted(mRecipeMenuList.size()-1);
                //mAdapter.notifyDataSetChanged();
                initiateAdapter();
            }
        });
    }

    public void initiateAdapter()
    {
        mAdapter = new RecipeMenuAdapter(mRecipeMenuList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecipeMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(mContext, RecipeMenuDetailsActivity.class);
                RecipeMenuItem clickedItem = mRecipeMenuList.get(position);

                intent.putExtra("idRecipeMenu", clickedItem.getId());
                intent.putExtra("recipeMenuName", clickedItem.getName());
                intent.putExtra("weekDays", clickedItem.getWeekDays());
                startActivity(intent);
            }

            @Override
            public void onRemoveClick(int position) {
                removeItem(position);
            }

            @Override
            public void onShareClick(int position) {
                getRecipeMenuText(position);
            }
        });
    }

    public void removeItem(int position)
    {
        RecipeMenuItem removedItem = mRecipeMenuList.get(position);
        mRecipeMenuList.remove(position);
        mAdapter.notifyItemRemoved(position);

        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_REMOVE_RECIPE_MENU+"/"+removedItem.getId(), "recipeMenu", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {
                Toast.makeText(mContext, "Cardápio removido com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                Toast.makeText(mContext, "Cardápio removido com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void shareItem()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mRecipeMenuText);
        startActivity(Intent.createChooser(shareIntent, "Selecione um aplicativo"));
    }

    public void getRecipeMenuText(int position)
    {
        mRecipeMenuText = "";
        RecipeMenuItem sharedItem = mRecipeMenuList.get(position);

        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_RECIPE_MENU_BY_ID+"/"+sharedItem.getId(), "recipeMenu", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject item = jsonArray.getJSONObject(i);

                    String name = item.getString("name");
                    mRecipeMenuText = name+"\n"+
                            "___________________________________\n\n";


                    JSONArray breakfast = item.getJSONArray("breakfast");
                    JSONArray lunch = item.getJSONArray("lunch");
                    JSONArray dinner = item.getJSONArray("dinner");

                    if(breakfast.length() > 0)
                    {
                        mRecipeMenuText = mRecipeMenuText  + "[Café da Manhã]\n\n";
                        for (int j = 0; j < breakfast.length(); j++)
                        {
                            JSONObject breakfastItem = breakfast.getJSONObject(j);
                            mRecipeMenuText = mRecipeMenuText +
                                    breakfastItem.getString("type")+" -> "+
                                    breakfastItem.getString("mealName")+"\n";
                        }
                        mRecipeMenuText = mRecipeMenuText + "___________________________________\n\n";
                    }

                    if(lunch.length() > 0)
                    {
                        mRecipeMenuText = mRecipeMenuText  + "[Almoço]\n\n";
                        for (int j = 0; j < lunch.length(); j++)
                        {
                            JSONObject lunchItem = lunch.getJSONObject(j);
                            mRecipeMenuText = mRecipeMenuText  +
                                    lunchItem.getString("type")+" -> "+
                                    lunchItem.getString("mealName")+"\n";
                        }
                        mRecipeMenuText = mRecipeMenuText + "___________________________________\n\n";
                    }

                    if(dinner.length() > 0)
                    {
                        mRecipeMenuText = mRecipeMenuText  + "[Jantar]\n\n";
                        for (int j = 0; j < dinner.length(); j++)
                        {
                            JSONObject dinnerItem = dinner.getJSONObject(j);
                            mRecipeMenuText = mRecipeMenuText +
                                    dinnerItem.getString("type")+" -> "+
                                    dinnerItem.getString("mealName")+"\n";
                        }
                    }
                }
                shareItem();
            }
        });
    }

    public void addNewRecipeMenu(String recipeMenuName)
    {
        APIrequests apiRequests = new APIrequests();

        //String idUser = mFoodName.getText().toString();

        String recipeMenu = "{"+
                "\"idUser\":" + "\"" + getCurrentUser() + "\","+
                "\"name\":" + "\"" + recipeMenuName + "\""+
                "}";

        apiRequests.postMethod(mContext, recipeMenu, Constants.POST_RECIPE_MENU, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show();

                updateRecipeMenuList();
            }
        });
    }
}