package com.example.kevin.fridgemanager.CallbackInterface;

import com.example.kevin.fridgemanager.DomainModels.User;

/**
 * Created by kevin on Aug 24, 2018
 **/
public interface ISignUpCallback{
    void loginSuccess(User user);

    void goToMainPage();

    void saveCurrentUser(String userId, String fridge_id);

    void addFridge(User user);
}
