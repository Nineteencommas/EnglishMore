package com.example.englishmore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    TextView Info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Info = findViewById(R.id.card_info_text);
        String info = "<p><b>About us</b></p>"+ "<p>This is a course project for Software Development, built by Michael and Xinyi</p>";
        Info.setText(Html.fromHtml(info));
    }
}
