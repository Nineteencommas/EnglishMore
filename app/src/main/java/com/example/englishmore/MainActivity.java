package com.example.englishmore;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    private Button startTest;
    private TextView text_test;
    private TextView username_view;
    private TextView password_view;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTest = findViewById(R.id.mainTestBtn);
        text_test = findViewById(R.id.main_text_test);
        username_view = findViewById(R.id.main_username);
        password_view = findViewById(R.id.main_password);
        login = findViewById(R.id.main_login);
        username_view.setVisibility(View.INVISIBLE);
        password_view.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);

        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/"
                        + "com.example.englishmore.topicAndDeckerInfo" + ".xml");
                if(f.exists())
                {
                    checkIfUpdate(); // after check will start a new activity   or can be put into downloadInfo?
                }
                else
                {
                    username_view.setVisibility(View.VISIBLE);
                    password_view.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                downLoadInfo();
                writeToBasicInfo();
                downloadProgress();
            }
        });
    }


    public void checkIfUpdate()// not sure is nested string request will cause some bug, hope not
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/ifUpdate",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true"))
                        {
                            downLoadInfo();
                        }
                        Intent intent = new Intent(MainActivity.this,TopicListActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void downLoadInfo()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/getTopicAndDeckerInfo",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseInfoResponse(response);
                        Log.d("JSON", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void downloadProgress()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/get_progress?username="+username_view.getText().toString()+"&password="+password_view.getText().toString(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseInfoResponse(response);
                        Log.d("JSON", response);

                        if(response.equals("password is wrong"))
                        {
                            text_test.setText("password is wrong");
                        }
                        else
                        {
                            if(!response.equals("new user")) {
                                parseProgress(response);
                            }

                            Intent intent = new Intent(MainActivity.this,TopicListActivity.class);
                            startActivity(intent);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void writeToBasicInfo()
    {
        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("com.example.englishmore.basicInfo", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("username",username_view.getText().toString());
        preferencesEditor.putString("password",password_view.getText().toString());
        preferencesEditor.putString("topicList","[\"TOEFL\",\"GRE\"]");
        preferencesEditor.commit();
    }
    // another is left for write to userProgress

    public void parseInfoResponse(String response)
    {
        Log.d("JSON",response);
        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("com.example.englishmore.topicAndDeckerInfo", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        try{
        JSONObject jsonObject = new JSONObject(response);
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            preferencesEditor.putInt(key, jsonObject.getInt(key));
        }
        preferencesEditor.commit();
        writeToBasicInfo();
    }
    catch (Exception e)
    {
        Log.d("JOSN", "something wrong with parseInfoResponse");
    }
    }

    public void parseProgress(String response) {
        Log.d("Progress", response);
        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("com.example.englishmore.userProgress", MODE_PRIVATE);
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
}
