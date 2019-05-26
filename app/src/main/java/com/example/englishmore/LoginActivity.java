package com.example.englishmore;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    private TextView username_view;
    private TextView password_view;
    private TextView password_wrong;
    private Button login;
    private static LoginActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_view = findViewById(R.id.main_username);
        password_view = findViewById(R.id.main_password);
        password_wrong = findViewById(R.id.login_wrongPassword);
        login = findViewById(R.id.main_login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                PreferrenceHelper.downLoadInfo(getApplicationContext(),username_view.getText().toString(), password_view.getText().toString());
                PreferrenceHelper.downloadProgress(getApplicationContext(),username_view.getText().toString(), password_view.getText().toString());

            }
        });
    }


    public void wrongPassWord()
    {
        password_wrong.setText("wrong password");
    }

    public static LoginActivity getInstance() {
        return instance;
    }

}
