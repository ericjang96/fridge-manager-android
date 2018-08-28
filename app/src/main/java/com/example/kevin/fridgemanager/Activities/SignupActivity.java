package com.example.kevin.fridgemanager.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevin.fridgemanager.CallbackInterface.ISignUpCallback;
import com.example.kevin.fridgemanager.DomainModels.User;
import com.example.kevin.fridgemanager.Generators.AlertDialogGenerator;
import com.example.kevin.fridgemanager.Generators.FridgeIdGenerator;
import com.example.kevin.fridgemanager.Managers.KeyboardManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;
import com.example.kevin.fridgemanager.REST.UserRestClient;
import com.example.kevin.fridgemanager.Singletons.SharedPrefs;
import com.example.kevin.fridgemanager.AsyncTasks.User.GetListOfUsersTask;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity implements ISignUpCallback{
    private static final String TAG = "SignupActivity";
    //widgets
    private EditText mUserIdEditText, mPasswordEditText, mConfirmPasswordEditText, mEmailEditText;
    private Button mDoneButton;
    private View mLoading;

    //vars
    private AlertDialogGenerator dialogGenerator;
    private List<String> usernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);
        initializeWidgets();

        new GetListOfUsersTask(SignupActivity.this).execute();

        KeyboardManager keyboardManager = new KeyboardManager(SignupActivity.this);
        keyboardManager.setupParent(findViewById(R.id.signup_user_layout));

        dialogGenerator = new AlertDialogGenerator();
    }

    private void initializeWidgets(){
        mUserIdEditText = findViewById(R.id.username_signup_input);
        mPasswordEditText = findViewById(R.id.password_signup_input);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_signup_input);
        mEmailEditText = findViewById(R.id.email_signup_input);

        mDoneButton = findViewById(R.id.done_signup_button);
        mLoading = findViewById(R.id.signup_progress_bar);
        mLoading.setVisibility(View.INVISIBLE);

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoading.setVisibility(View.VISIBLE);
                String userId = mUserIdEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String fridgeId = FridgeIdGenerator.generateID();
                String email = mEmailEditText.getText().toString();

                if(doesPasswordMatch() && isUsernameAvailable(userId)){
                    User user = new User(userId, password, fridgeId, email);
                    UserRestClient.postUserData(user, SignupActivity.this);
                }
            }
        });
    }

    private boolean doesPasswordMatch(){
        String pwd = mPasswordEditText.getText().toString();
        String confirmPwd = mConfirmPasswordEditText.getText().toString();

        if(pwd.equals(confirmPwd)){
            return true;
        }
        else{
            mLoading.setVisibility(View.INVISIBLE);
            AlertDialog dialog = dialogGenerator.generate("Error", "The password and confirmation do not match", SignupActivity.this);
            dialog.show();
            return false;
        }
    }

    private boolean isUsernameAvailable(String username){
        if(usernames.contains(username)){
            mLoading.setVisibility(View.INVISIBLE);
            AlertDialog dialog = dialogGenerator.generate("Error", "This username already exists, please choose another", SignupActivity.this);
            dialog.show();
            return false;
        }

        return true;
    }

    public void setUsernames(List<String> usernames){
        this.usernames = usernames;
    }

    @Override
    public void loginSuccess(User user) {
        addFridge(user);
        saveCurrentUser(user.getUserId(), user.getFridgeId());
        SharedPrefs.write("isLoggedIn", true);
        mLoading.setVisibility(View.INVISIBLE);
        goToMainPage();
    }

    @Override
    public void goToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void saveCurrentUser(String userId, String fridge_id) {
        SharedPrefs.write("user_id", userId);
        SharedPrefs.write("fridge_id", fridge_id);
    }

    @Override
    public void addFridge(User user) {
        FridgeRestClient.createNewFridge(user);
    }
}
