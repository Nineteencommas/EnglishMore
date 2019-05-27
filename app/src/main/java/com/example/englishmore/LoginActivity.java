package com.example.englishmore;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class LoginActivity extends AppCompatActivity {
    private EditText username_view;
    private EditText password_view;
    private TextView password_wrong;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_view = findViewById(R.id.main_username);
        password_view = findViewById(R.id.main_password);
        password_wrong = (TextView) findViewById(R.id.login_wrongPassword);
        login = findViewById(R.id.main_login);
        username_view.setHint("username");
        password_view.setHint("password");

        username_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    username_view.setHint("username");
                } else {
                     username_view.setHint("");
                }
            }
        });

        password_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    password_view.setHint("password");
                } else {
                    password_view.setHint("");
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                PreferrenceHelper.downLoadInfo(getApplicationContext(),username_view.getText().toString(), password_view.getText().toString());

                downloadProgress(getApplication(),username_view.getText().toString(), password_view.getText().toString());
                //PreferrenceHelper.writeToBasicInfo();
            }
        });
    }

    public void downloadProgress(final Context context, String username,String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://35.178.77.171:5000/get_progress?username="+username+"&password="+password,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("password is wrong"))
                        {

                            password_wrong.setText("password is wrong");
                        }
                        else
                        {
                            if(!response.equals("new user")) {
                                PreferrenceHelper.parseProgress(response, context);
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
}
