package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddRecipeWrittenActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTitle, mIngredients, mInstructions, mCategory, mTags;
    private ImageView mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_written);
        mContext = getApplicationContext();

        mTitle = findViewById(R.id.txtTitle);
        mIngredients = findViewById(R.id.txtIngredients);
        mInstructions = findViewById(R.id.txtInstructions);
        mCategory = findViewById(R.id.txtCategory);
        mTags = findViewById(R.id.txtTags);

        mPicture = (ImageView)findViewById(R.id.imgRecipe);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_picture);
        mPicture.setImageBitmap(image);
    }

    public void onClickReturn(View v){
        finish();
    }

    public void onClickAddPicture(View v)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, Constants.IMAGE_GALLERY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    mPicture.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void onClickConfirm(View v)
    {
        APIrequests apiRequests = new APIrequests();

        String title = mTitle.getText().toString();
        String ingredients = mIngredients.getText().toString();
        String instructions = mInstructions.getText().toString();
        String category = mCategory.getText().toString();
        String tags = mTags.getText().toString();

        String recipe = "{"+
                "\"title\":" + "\"" + title + "\","+
                "\"ingredients\":" + "\"" + ingredients + "\","+
                "\"instructions\":" + "\"" + instructions + "\","+
                "\"category\":" + "\"" + category + "\","+
                "\"tags\":" + "\"" + tags + "\""+
                "}";

        apiRequests.postMethod(mContext, recipe, Constants.POST_RECIPES, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }
}