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
import com.example.sugarfree.utils.ImageHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddRecipeWrittenActivity extends AppCompatActivity {
    private Context mContext;

    private static final int SEND_SCREENSHOT_ACTIVITY = 002;
    private TextView mTitle, mIngredients, mInstructions, mTags, mTitleToolBar;
    private ImageView mPicture, mReturnArrow;
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_written);
        mContext = getApplicationContext();

        Intent intent = getIntent();
        mCategory = intent.getStringExtra("category");

        mTitle = findViewById(R.id.txtTitle);
        mIngredients = findViewById(R.id.txtIngredients);
        mInstructions = findViewById(R.id.txtInstructions);
        mTags = findViewById(R.id.txtTags);

        mTitleToolBar = findViewById(R.id.txtToolbarTitle);
        mReturnArrow = findViewById(R.id.imgToolbarArrow);
        updateToolbar();

        mPicture = (ImageView) findViewById(R.id.imgRecipe);
        //Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_picture);
        //mPicture.setImageBitmap(image);
    }

    public void onClickReturn(View v) {
        finish();
    }

    public void onClickAddPicture(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, Constants.IMAGE_GALLERY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_SCREENSHOT_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra("title");
                String ingredients = data.getStringExtra("ingredients");
                String instructions = data.getStringExtra("instructions");

                mTitle.setText(title);
                mIngredients.setText(ingredients);
                mInstructions.setText(instructions);
            }
        } else if (resultCode == RESULT_OK) {
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

    public void onClickConfirm(View v) {
        APIrequests apiRequests = new APIrequests();

        String title = mTitle.getText().toString();
        String ingredients = mIngredients.getText().toString();
        String instructions = mInstructions.getText().toString();
        String category = mCategory;
        String tags = mTags.getText().toString();
        tags.replaceAll(" ", "     ");
        tags.replaceAll(", ", "    ");
        tags.replaceAll(",", "     ");
        //ArrayList<String> tags = new ArrayList<>();
        //tags.add("tag 1");
        //tags.add("tag 2");
        //tags.add("tag 3");

        ingredients = ingredients.replaceAll("\n", "\\\\n");
        instructions = instructions.replaceAll("\n", "\\\\n");
        //tags = tags.replaceAll("\n", "\\\\n");

        mPicture.setDrawingCacheEnabled(true);
        String image = ImageHandler.convert(mPicture.getDrawingCache());
        image = image.replaceAll("\n", "");

        String recipe = "{" +
                "\"title\":" + "\"" + title + "\"," +
                "\"ingredients\":" + "\"" + ingredients + "\"," +
                "\"instructions\":" + "\"" + instructions + "\"," +
                "\"category\":" + "\"" + category + "\"," +
                "\"tags\":" + "\"" + tags + "\"," +
                "\"image\":" + "\"" + image + "\"" +
                "}";

        apiRequests.postMethod(mContext, recipe, Constants.POST_RECIPES, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) throws JSONException {
                Toast.makeText(mContext, jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, CategoryActivity.class);
                intent.putExtra("categoryName", mCategory);
                startActivity(intent);
                finish();
            }

        });
    }

    public void updateToolbar() {
        mTitleToolBar.setText("Adicionar");

        mReturnArrow.setVisibility(View.VISIBLE);
        mTitleToolBar.setVisibility(View.VISIBLE);
    }

    public void onClickSendScreenshot(View v) {
        //Toast.makeText(mContext, "Essa função ainda não é suportada.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, SendScreenshotActivity.class);
        startActivityForResult(intent, SEND_SCREENSHOT_ACTIVITY);
    }
}