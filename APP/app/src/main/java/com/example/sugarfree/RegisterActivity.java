package com.example.sugarfree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.APIcommunication.APIrequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mEmail, mName, mPassword, mConfirmPassword;
    private  ImageView mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = getApplicationContext();

        mEmail = findViewById(R.id.txtEmail);
        mName = findViewById(R.id.txtName);
        mPassword = findViewById(R.id.txtPassword);
        mConfirmPassword = findViewById(R.id.txtConfirmPassword);

        mPicture = (ImageView)findViewById(R.id.profilePicture);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_picture);
        mPicture.setImageBitmap(image);
    }

    public void onClickConfirm(View v)
    {
        APIrequests apiRequests = new APIrequests();

        String email = mEmail.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if(!checkInputs(email, name, password, confirmPassword)) {
            return;
        }

        //TODO: criar classe parseJson
        String user = "{"+
                "\"name\":" + "\"" + name + "\","+
                "\"email\":" + "\"" + email + "\","+
                "\"password\":" + "\"" + password + "\""+
                "}";

        /*apiRequests.postMethod(mContext, user, Constants.POST_REGISTER, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) throws JSONException {
                Toast.makeText(mContext, jsonObject.getString("success"),Toast.LENGTH_LONG).show();
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });*/
        //startActivity(new Intent(mContext, SelectRecipesActivity.class));
    }

    public boolean checkInputs(String email, String name, String password, String confirmPassword)
    {
        boolean validInput = true;
        StringBuilder errorMessage = new StringBuilder();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            validInput = false;
            errorMessage.append(Constants.MSG_INVALID_EMAIL);
        }
        else if(name.isEmpty() || name.length() < Constants.MIN_NAME_LENGTH)
        {
            validInput = false;
            errorMessage.append(Constants.MSG_INVALID_NAME);
        }
        else if(password.isEmpty() || password.length() < Constants.MIN_PASSWORD_LENGTH)
        {
            validInput = false;
            errorMessage.append(Constants.MSG_INVALID_PASSWORD);
        }
        else if(!password.equals(confirmPassword))
        {
            validInput = false;
            errorMessage.append(Constants.MSG_PASSWORD_MISMATCH);
        }

        if(!validInput)
        {
            Toast.makeText(mContext, errorMessage,Toast.LENGTH_LONG).show();
        }

        return validInput;
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

    public void onClickReturn(View v){
        finish();
    }
}