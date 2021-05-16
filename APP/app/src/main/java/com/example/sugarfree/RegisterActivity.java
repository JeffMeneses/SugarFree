package com.example.sugarfree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarfree.utils.Constants;

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
        String email = mEmail.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if(!checkInputs(email, name, password, confirmPassword)) {
            return;
        }

        //TODO: fazer requisição de cadastro
        if(true)
        {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setTitle("Aviso");
            dlgAlert.setMessage("Conta criada com sucesso!");
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
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
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setTitle(Constants.ALERT_INVALID_INPUT);
            dlgAlert.setMessage(errorMessage);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        return validInput;
    }

    public void onClickAddPicture(View v)
    {
        //TODO: abrir galeria para escolher foto
    }

    public void onClickReturn(View v){
        finish();
    }
}