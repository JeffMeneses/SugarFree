package com.example.sugarfree.APIcommunication;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class APIrequests {
    private RequestQueue mRequestQueue;

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String message);
    }

    public interface VolleyGETResponseListener {
        void onError(String message);

        void onResponse(JSONArray jsonArray) throws JSONException;
    }

    public void postMethod(Context context, String data, String action, VolleyResponseListener volleyResponseListener)
    {
        final String saveData = data;
        String URL= action;

        mRequestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    volleyResponseListener.onResponse(objres.getString("success"));

                } catch (JSONException e) {
                    volleyResponseListener.onError("Server Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                    volleyResponseListener.onError(objres.getString("error"));
                } catch (UnsupportedEncodingException | JSONException e) {
                    Toast.makeText(context,"Server Error",Toast.LENGTH_LONG).show();
                    volleyResponseListener.onError("Server Error");
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
        mRequestQueue.add(stringRequest);
    }

    public void getMethod(Context context, String action, String name, VolleyGETResponseListener volleyGETResponseListener)
    {
        String URL= action;

        mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, action, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            volleyGETResponseListener.onResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(request);
    }


}
