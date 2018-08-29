package com.example.kevin.fridgemanager.Activities;
/**
 * Created by kevin on July 31, 2018
 **/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.example.kevin.fridgemanager.Singletons.SharedPrefs;

public class MainActivity extends AppCompatActivity {

    //widgets
    private Button mLogoutButton;
    private TextView mWelcomeUser;
    private Intent mPreviousIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreviousIntent = getIntent();
        SharedPrefs.init(MainActivity.this);
        boolean isLoggedIn = SharedPrefs.read("isLoggedIn", false);

        if(!isLoggedIn){
            goToLoginPage();
        }
        else{
            setContentView(R.layout.activity_main);
            mWelcomeUser = findViewById(R.id.welcome_user_text);
            String welcomeText = "Welcome " + SharedPrefs.read("user_id");
            mWelcomeUser.setText(welcomeText);
            mLogoutButton = findViewById(R.id.logout_main_button);
            mLogoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPrefs.write("isLoggedIn", false);
                    goToLoginPage();
                }
            });
        }
    }

    public void goToFridge(View view) {
        Intent intent = new Intent(this, FridgeActivity.class);
        startActivity(intent);
    }

    public void goToGroceries(View view){
        Intent intent = new Intent(this, UserGroceryListsActivity.class);
        startActivity(intent);
    }

    public void goToLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        String previousActivity = mPreviousIntent.getStringExtra(GlobalVariables.loginActivity);
        if(previousActivity == null){
            //do nothing
        }
        else if(previousActivity.equals(GlobalVariables.loginActivity)){
            //do nothing
        }
        else{
            super.onBackPressed();
        }
    }
}
