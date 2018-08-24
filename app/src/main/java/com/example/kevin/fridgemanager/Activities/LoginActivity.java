package com.example.kevin.fridgemanager.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;

public class LoginActivity extends AppCompatActivity implements UserRestClient.callbackTest {
    //widgets
    private EditText mUsername, mPassword;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.username_input);
        mPassword = findViewById(R.id.password_input);
        mLoginButton = findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                UserRestClient.getUserExists(username, password, LoginActivity.this);
            }
        });
    }


    public void goToSignupPage(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    public void goToMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveLoggedInState(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("fridge_manager_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    @Override
    public void callOnSuccess() {
        saveLoggedInState();
        goToMainPage();
    }
}