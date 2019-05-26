package com.example.englishmore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.welcome_animation);
        imageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/"
                        + "com.example.englishmore.topicAndDeckerInfo" + ".xml");
                if(f.exists()) {
                    PreferrenceHelper.checkIfUpdate(getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), TopicListActivity.class));
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
