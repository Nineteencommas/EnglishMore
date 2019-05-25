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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTest = findViewById(R.id.mainTestBtn);
        text_test = findViewById(R.id.main_text_test);
        getTopicAndDeckerInfo();

        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TopicListActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getTopicAndDeckerInfo()
    {
        File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/"
                + "com.example.englishmore.topicAndDeckerInfo" + ".xml");
        if(! f.exists())
        {
            text_test.setText("file not exist");
            downLoadInfo();
        }
        else
        {
            text_test.setText("file exist");
            checkIfUpdate();
        }

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
    public void writeToBasicInfo()
    {
        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("com.example.englishmore.basicInfo", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("username","Xinyi");
        preferencesEditor.putString("password","123456");
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
}
