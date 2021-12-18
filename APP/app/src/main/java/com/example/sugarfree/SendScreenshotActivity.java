package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sugarfree.APIcommunication.APIrequests;
import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.utils.ImageHandler;
import com.example.sugarfree.utils.RecipeItem;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public class SendScreenshotActivity extends AppCompatActivity {
    private Context mContext;

    private ImageView mImgFirst, mImgSecond;
    private Button mBtnSend;

    private static final int RC_CROP1 = 100;
    private static final int RC_CROP2 = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_screenshot);
        mContext = getApplicationContext();

        mImgFirst = findViewById(R.id.imgFirst);
        mImgSecond = findViewById(R.id.imgSecond);
        mBtnSend = findViewById(R.id.btnSend);

        mImgFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropActivity(RC_CROP1);
            }
        });

        mImgSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropActivity(RC_CROP2);
            }
        });
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImages();
            }
        });
    }

    public void onClickReturn(View v){
        finish();
    }

    private void startCropActivity(int requestCode){
        Intent cropIntent = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);

        startActivityForResult(cropIntent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            InputStream inputStream;
            if (requestCode == RC_CROP1) {
                try {
                    inputStream = getContentResolver().openInputStream(resultUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    mImgFirst.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == RC_CROP2) {
                try {
                    inputStream = getContentResolver().openInputStream(resultUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    mImgSecond.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void sendImages() {
        APIrequests apiRequests = new APIrequests();

        mImgFirst.setDrawingCacheEnabled(true);
        String imageFirst = ImageHandler.convert(mImgFirst.getDrawingCache());
        imageFirst = imageFirst.replaceAll("\n", "");

        mImgSecond.setDrawingCacheEnabled(true);
        String imageSecond = ImageHandler.convert(mImgSecond.getDrawingCache());
        imageSecond = imageSecond.replaceAll("\n", "");

        String recipeImages = "{"+
                "\"recipeImages\":" + "[{\"image\":\""+ imageFirst +"\"},{\"image\":\""+ imageSecond +"\"}]" +
                "}";

        apiRequests.postMethod(mContext, recipeImages, Constants.POST_EXTRACT_TEXT_FROM_IMG, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) throws JSONException {
                JSONObject json = jsonObject.getJSONObject("result");

                String title = json.getString("title");
                String ingredients = json.getString("ingredients");
                String instructions = json.getString("instructions");

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("ingredients", ingredients);
                intent.putExtra("instructions", instructions);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}