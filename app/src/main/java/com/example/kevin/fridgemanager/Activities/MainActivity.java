/*
Created by Kevin Kwon on July 31 2018
 */
package com.example.kevin.fridgemanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kevin.fridgemanager.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToFridge(View view) {
        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }
}
