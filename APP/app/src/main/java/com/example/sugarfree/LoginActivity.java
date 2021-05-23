package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.APIcommunication.APIrequests;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();

        mEmail = findViewById(R.id.txtEmail);
        mPassword = findViewById(R.id.txtPassword);
    }

    public void onClickLogin(View v)
    {
        APIrequests apiRequests = new APIrequests();

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //TODO: validar credenciais do login
        String user = "{"+
                "\"email\":" + "\"" + email + "\","+
                "\"password\":" + "\"" + password + "\""+
                "}";

        apiRequests.postMethod(mContext, user, Constants.POST_LOGIN);
    }

    public void onClickReturn(View v){
        finish();
    }

}