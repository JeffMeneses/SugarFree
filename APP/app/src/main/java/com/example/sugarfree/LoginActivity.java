package com.example.sugarfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarfree.utils.Constants;
import com.example.sugarfree.APIcommunication.APIrequests;

import static com.example.sugarfree.utils.CurrentUser.setCurrentUser;

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

        apiRequests.postMethod(mContext, user, Constants.POST_LOGIN, new APIrequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String message) {
                setCurrentUser(message);
                Toast.makeText(mContext, "Login concluído com sucesso.",Toast.LENGTH_LONG).show();
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }

    public void onClickReturn(View v){
        finish();
    }

}