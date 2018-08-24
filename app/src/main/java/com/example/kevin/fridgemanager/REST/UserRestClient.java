package com.example.kevin.fridgemanager.REST;

import android.content.res.Resources;
import android.util.Log;

import com.example.kevin.fridgemanager.CallbackInterface.ILoginCallback;
import com.example.kevin.fridgemanager.CallbackInterface.ISignUpCallback;
import com.example.kevin.fridgemanager.DomainModels.User;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class UserRestClient {

    private static final String TAG = "UserRestClient";
    private static String BASE_URL = GlobalVariables.connectionURL;
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static void get(String url, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void getUserExists(String username, String password, final ILoginCallback loginActivity){
        String urlWithQuery = "/users?user_id=" + username + "&password=" + password;

        get(urlWithQuery, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response.length() == 1){
                    Log.i(TAG, "onSuccess: Successfully received user!");
                    try {
                        JSONObject userObject = response.getJSONObject(0);
                        String username = userObject.getString("user_id");
                        String fridge_id = userObject.getString("fridge_id");
                        User user = new User(username, fridge_id);

                        loginActivity.loginSuccess(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Log.i(TAG, "User does not exist");
                    loginActivity.loginFailure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static void postUserData(final User user, final ISignUpCallback signUpActivity){
        RequestParams params = new RequestParams();
        params.add("user_id", user.getUserId());
        params.add("password", user.getPassword());
        params.add("fridge_id", user.getFridgeId());
        params.add("email", user.getEmail());

        post("/users/", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart(){
                System.out.println("Creating new user...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("Successfully created new user!");
                signUpActivity.loginSuccess(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println(responseString);
            }
        });
    }

}
