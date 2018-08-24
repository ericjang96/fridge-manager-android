/*
Created by Kevin Kwon on July 31 2018
 */
package com.example.kevin.fridgemanager.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kevin.fridgemanager.R;

public class MainActivity extends AppCompatActivity {

    //widgets
    private Button mLogoutButton;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("fridge_manager_login", MODE_PRIVATE);
        mEditor = pref.edit();

        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);
        if(!isLoggedIn){
            goToLoginPage();
        }
        else{
            setContentView(R.layout.activity_main);
        }

        mLogoutButton = findViewById(R.id.logout_main_button);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor.putBoolean("isLoggedIn", false);
                mEditor.apply();
                goToLoginPage();
            }
        });
    }

    public void goToFridge(View view) {
        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }

    public void goToGroceries(View view){
        Intent intent = new Intent(this, GroceryActivity.class);
        startActivity(intent);
    }

    public void goToLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
