package com.example.englishmore;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button startTest;

    private SharedPreferences mPreferences ;
    private String sharedPrefFile = "com.example.englishmore.topicInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTest =(Button)findViewById(R.id.mainTestBtn);
        mPreferences = this.getSharedPreferences("sharedPrefFile", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("topicInfo", "[\n" +
                "{\"topic\": \"TOEFL\",\n" +
                "\"total\": 1000},\n" +
                "{\"topic\": \"GRE\",\n" +
                "\"total\": 2000},\n" +
                "{\"topic\": \"SAT\",\n" +
                "\"total\": 3000}\n" +
                "]");

        preferencesEditor.commit();

        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TopicListActivity.class);
                startActivity(intent);
            }
        });
    }


}
