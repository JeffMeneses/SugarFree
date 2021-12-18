package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.CurrentUser;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.sugarfree.utils.CurrentUser.setCurrentUser;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mImgRecipeDetails;
    private TextView mTxtTitleDetails, mTxtIngredients, mTxtInstructions, mTxtTags, mTxtRatingValue, mTxtRatingCount;
    private RatingBar mRbInfo;

    private Context mContext;
    private TextView mTitle;
    private ImageView mReturnArrow;

    private String title, ingredients, instructions, tags, category, avgRating, recipeID, countRating;
    private Bitmap image;
    private RatingBar mRbReview;

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
        mTxtRatingValue = findViewById(R.id.txtRatingValue);
        mTxtRatingCount = findViewById(R.id.txtRatingCount);
        mRbInfo = findViewById(R.id.rbInfo);

        mTitle = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();

        //RatingBar
        mRbReview = findViewById(R.id.rbReview);
        mRbReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(getApplicationContext(), "Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_LONG).show();
                sendRecipeReview(rating);
            }
        });
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
        recipeID = intent.getStringExtra("recipeID");
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
                avgRating = recipeJson.getString("avgRating");
                countRating = recipeJson.getString("countRating");
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
        mTxtRatingValue.setText(avgRating);
        mTxtRatingCount.setText("("+countRating+")");
        mRbInfo.setRating(Float.parseFloat(avgRating));
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

    public void sendRecipeReview(float rating)
    {
        APIrequests apiRequests = new APIrequests();

        String recipeReview = "{"+
                "\"recipeID\":" + "\"" + recipeID + "\","+
                "\"userID\":" + "\"" + CurrentUser.getCurrentUser() + "\","+
                "\"rating\":" + "\"" + rating + "\""+
                "}";
        //Toast.makeText(getApplicationContext(), recipeReview, Toast.LENGTH_LONG).show();
        apiRequests.postMethod(mContext, recipeReview, Constants.POST_RECIPE_REVIEW, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) throws JSONException {
                Toast.makeText(mContext, "Obrigado pela sua avaliação!",Toast.LENGTH_LONG).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }
}