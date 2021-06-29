package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mImgRecipeDetails;
    private TextView mTxtTitleDetails, mTxtIngredients, mTxtInstructions, mTxtTags;

    private Context mContext;
    private TextView mTitle;
    private ImageView mReturnArrow;

    private String title, ingredients, instructions, tags, category, likes;
    private Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mContext = getApplicationContext();

        getDetails();
        mImgRecipeDetails = findViewById(R.id.imgRecipeDetails);
        mTxtTitleDetails = findViewById(R.id.txtTitleDetails);
        mTxtIngredients = findViewById(R.id.txtIngredients);
        mTxtInstructions = findViewById(R.id.txtInstructions);
        mTxtTags = findViewById(R.id.txtTags);

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();
    }

    public void updateToolbar()
    {
        mTitle.setText("Detalhes");

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void getDetails()
    {
        Intent intent = getIntent();
        String recipeID = intent.getStringExtra("recipeID");
        APIrequests apiRequests = new APIrequests();
        apiRequests.getMethod(mContext, Constants.GET_RECIPE_BY_ID+"/"+recipeID, "recipes", new APIrequests.VolleyGETResponseListener() {
            @Override
            public void onError(String message)  {

            }

            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                JSONObject recipeJson = jsonArray.getJSONObject(0);

                title = recipeJson.getString("title");
                ingredients = recipeJson.getString("ingredients");
                category = recipeJson.getString("category");
                instructions = recipeJson.getString("instructions");
                tags = recipeJson.getString("tags");
                likes = recipeJson.getString("likes");
                image = ImageHandler.convert(recipeJson.getString("image"));

                assignContent();
            }
        });
    }

    public void assignContent()
    {
        mImgRecipeDetails.setImageBitmap(image);
        mTxtTitleDetails.setText(title);
        mTxtIngredients.setText(ingredients);
        mTxtInstructions.setText(instructions);
        mTxtTags.setText(tags);
    }

    public String getRecipeText()
    {
        String recipeText = mTxtTitleDetails.getText().toString()+"\n"+
                "___________________________________\n\n" +
                mTxtIngredients.getText().toString()+"\n" +
                "___________________________________\n\n" +
                mTxtInstructions.getText().toString();

        return recipeText;
    }

    public void onClickShare(View v)
    {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getRecipeText());
        startActivity(Intent.createChooser(shareIntent, "Selecione um aplicativo"));
    }

    public String convertTags(String tags)
    {
        tags = tags.replaceAll("\\[", "");
        tags = tags.replaceAll("\\]", "");
        tags = tags.replaceAll(",", "    ");

        return tags;
    }
}