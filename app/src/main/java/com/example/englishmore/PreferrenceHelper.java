package com.example.englishmore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;

public class PreferrenceHelper {
    public static void downLoadInfo(final Context context, final String username, final String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/getTopicAndDeckerInfo",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseInfoResponse(response, context, username, password);
                        Log.d("JSON", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(context).addToRequestQueue(stringRequest);

    }
    public static void parseInfoResponse(String response, Context context,String username, String password)
    {
        Log.d("JSON",response);
        SharedPreferences mPreferences = context.getSharedPreferences("com.example.englishmore.topicAndDeckerInfo", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        try{
            JSONObject jsonObject = new JSONObject(response);
            Iterator<String> keys = jsonObject.keys();
            String topicList = "[\"TOEFL\"]";
            while (keys.hasNext()) {

                String key = keys.next();
                if (key.equals("topicList"))
                {

                    topicList = jsonObject.getJSONArray("topicList").toString();
                    Log.d("test",topicList);
                }
                else{
                    preferencesEditor.putInt(key, jsonObject.getInt(key));
                }}
            preferencesEditor.commit();
            writeToBasicInfo(topicList,context ,username, password);
        }
        catch (Exception e)
        {
            Log.d("JOSN", "something wrong with parseInfoResponse");
        }
    }

    public static void writeToBasicInfo(String topicList,Context context, String username, String password)
    {
        SharedPreferences mPreferences = context.getSharedPreferences("com.example.englishmore.basicInfo", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("username",username);
        preferencesEditor.putString("password",password);
        preferencesEditor.putString("topicList",topicList);
        Log.d("test2",topicList);
        preferencesEditor.commit();
    }

    public static void downloadProgress(final Context context, final String username, final String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/get_progress?username="+username+"&password="+password,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseInfoResponse(response, context, username, password);
                        Log.d("JSON", response);

                        if(response.equals("password is wrong"))
                        {
                          //  LoginActivity.getInstance().wrongPassWord();
                        }
                        else
                        {
                            if(!response.equals("new user")) {
                                parseProgress(response, context);
                            }

                            Intent intent = new Intent(context,TopicListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(context).addToRequestQueue(stringRequest);

    }

    public static void parseProgress(String response, Context context) {
        Log.d("Progress", response);
        SharedPreferences mPreferences = context.getSharedPreferences("com.example.englishmore.userProgress", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                preferencesEditor.putString(key, jsonObject.getString(key));
                if(jsonObject.get(key) instanceof String)
                {
                    preferencesEditor.putString(key,jsonObject.getString(key));
                }
                else
                {
                    preferencesEditor.putInt(key,jsonObject.getInt(key));
                }
            }
            preferencesEditor.commit();
        }
        catch (Exception e)
        {
            Log.d("Progress","something went wrong with parseProgress"+ e.toString());
        }
    }

    public static void checkIfUpdate(final Context context)// not sure is nested string request will cause some bug, hope not
    {
        final SharedPreferences mPreferences = context.getSharedPreferences("com.example.englishmore.basicInfo", MODE_PRIVATE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/ifUpdate",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true"))
                        {
                            downLoadInfo(context,mPreferences.getString("username","lala"),mPreferences.getString("password","llala") );
                        }
//                        Intent intent = new Intent(context,TopicListActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                        context.startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(context).addToRequestQueue(stringRequest);
    }
}
