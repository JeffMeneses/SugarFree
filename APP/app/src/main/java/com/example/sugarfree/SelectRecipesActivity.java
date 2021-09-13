package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.MultiAdapter;
import com.example.sugarfree.utils.RecipeAdapter;
import com.example.sugarfree.utils.RecipeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectRecipesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<RecipeItem> selectedRecipes = new ArrayList<>();
    private MultiAdapter mAdapter;
    Button btn;
    private Context mContext;

    private TextView mTxtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipes);
        mContext = getApplicationContext();

        recyclerView = findViewById(R.id.recyclerView);
        btn = findViewById(R.id.btn_multi_selection);
        mTxtName = findViewById(R.id.txtTitle);
        mTxtName.setText(getIntent().getStringExtra("name")+", escolha 3 favoritas.");

        initiateRecyclerView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                if(mAdapter.getSelected().size() > 0){
                    stringBuilder.append("[");
                    for(int i=0; i < mAdapter.getSelected().size(); i++){
                        stringBuilder.append("\"");
                        stringBuilder.append(mAdapter.getSelected().get(i).getId());
                        if((mAdapter.getSelected().size() - i) > 1)
                            stringBuilder.append("\",");
                        else
                            stringBuilder.append("\"");
                    }
                    stringBuilder.append("]");
                }
                Intent intent = new Intent();
                intent.putExtra("selectedRecipes", (Serializable) stringBuilder);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void initiateRecyclerView()
    {
        APIrequests apiRequests = new APIrequests();

        apiRequests.getMethod(mContext, Constants.GET_N_RANDOM_RECIPES, "recipes", new APIrequests.VolleyGETResponseListener() {
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
                    String avgRating = name.getString("avgRating");
                    String countRating = name.getString("countRating");
                    String image = name.getString("image");

                    Bitmap imageBitmap = ImageHandler.convert(image);

                    //mRecipeList.add(new RecipeItem(R.drawable.ic_default_image, title, likes));
                    selectedRecipes.add(new RecipeItem(id, imageBitmap, title, avgRating, countRating));
                }
                initiateAdapter();
            }
        });
    }

    public void initiateAdapter()
    {
        mAdapter = new MultiAdapter(this, selectedRecipes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);
    }
}