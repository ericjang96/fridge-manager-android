package com.example.kevin.fridgemanager.Activities;
/**
 * Created by kevin on Aug 23, 2018
 **/
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevin.fridgemanager.CallbackInterface.ILoginCallback;
import com.example.kevin.fridgemanager.DomainModels.User;
import com.example.kevin.fridgemanager.Generators.AlertDialogGenerator;
import com.example.kevin.fridgemanager.Managers.KeyboardManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;
import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.example.kevin.fridgemanager.Singletons.SharedPrefs;

public class LoginActivity extends AppCompatActivity implements ILoginCallback {
    //widgets
    private EditText mUsername, mPassword;
    private Button mLoginButton;
    private View mLoading;

    //vars
    private AlertDialogGenerator dialogGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeWidgets();

        KeyboardManager keyboardManager = new KeyboardManager(LoginActivity.this);
        keyboardManager.setupParent(findViewById(R.id.login_activity_layout));

        dialogGenerator = new AlertDialogGenerator();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    private void initializeWidgets(){
        mUsername = findViewById(R.id.username_input);
        mPassword = findViewById(R.id.password_input);
        mLoginButton = findViewById(R.id.login_button);
        mLoading = findViewById(R.id.login_progress_bar);
        mLoading.setVisibility(View.INVISIBLE);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoading.setVisibility(View.VISIBLE);
                String userId = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                UserRestClient.getUserExists(userId, password, LoginActivity.this);
            }
        });
    }

    public void goToSignupPage(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GlobalVariables.fromActivity, GlobalVariables.loginActivity);
        startActivity(intent);
    }

    @Override
    public void saveCurrentUser(String userId, String fridge_id) {
        SharedPrefs.write("user_id", userId);
        SharedPrefs.write("fridge_id", fridge_id);
    }

    @Override
    public void loginSuccess(User user) {
        saveCurrentUser(user.getUserId(), user.getFridgeId());
        SharedPrefs.write("isLoggedIn", true);
        mLoading.setVisibility(View.INVISIBLE);
        goToMainPage();
    }

    @Override
    public void loginFailure(){
        mLoading.setVisibility(View.INVISIBLE);
        AlertDialog dialog = dialogGenerator.generate("Error", "The username or password is wrong", LoginActivity.this);
        dialog.show();
    }
}