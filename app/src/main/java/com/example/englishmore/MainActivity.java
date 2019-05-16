package com.example.englishmore;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private Button start_card;
    private Button start_list;
    private TextView testtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_card = findViewById(R.id.start_card);
        start_list = findViewById(R.id.start_list);
        testtext = findViewById(R.id.testtext);
        final Context context = this;
        start_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                startActivity(intent);

            }
        });
        start_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DeckersListActivity.class);
                startActivity(intent);
            }
        });





    }


}
