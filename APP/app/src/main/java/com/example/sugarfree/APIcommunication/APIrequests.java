package com.example.sugarfree.APIcommunication;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class APIrequests {
    private RequestQueue requestQueue;

    public boolean postMethod(Context context, String data, String action)
    {
        final String saveData = data;
        String URL= action;

        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    //Toast.makeText(context,objres.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(context, objres.getString("success"),Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(context,"Server Error",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                JSONObject objres;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    objres = new JSONObject(body);
                    Toast.makeText(context, objres.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(context,"Server Error",Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return saveData == null ? null : saveData.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
        return true;
    }
}
