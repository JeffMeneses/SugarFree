package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.Recipe;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mImgRecipeDetails;
    private TextView mTxtTitleDetails, mTxtIngredients, mTxtInstructions, mTxtTags;

    private TextView mTitle;
    private ImageView mReturnArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.EXTRA_TITLE);
        String ingredients = intent.getStringExtra(Constants.EXTRA_INGREDIENTS);
        //String category = intent.getStringExtra(Constants.EXTRA_CATEGORY);
        String instructions = intent.getStringExtra(Constants.EXTRA_INSTRUCTIONS);
        String tags = intent.getStringExtra(Constants.EXTRA_TAGS);
        //String likes = intent.getStringExtra(Constants.EXTRA_LIKES);
        Bitmap image = ImageHandler.convert(intent.getStringExtra(Constants.EXTRA_IMAGE));

        mImgRecipeDetails = findViewById(R.id.imgRecipeDetails);
        mTxtTitleDetails = findViewById(R.id.txtTitleDetails);
        mTxtIngredients = findViewById(R.id.txtIngredients);
        mTxtInstructions = findViewById(R.id.txtInstructions);
        mTxtTags = findViewById(R.id.txtTags);

        mImgRecipeDetails.setImageBitmap(image);
        mTxtTitleDetails.setText(title);
        mTxtIngredients.setText(ingredients);
        mTxtInstructions.setText(instructions);
        mTxtTags.setText(tags);

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
}