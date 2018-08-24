package com.example.kevin.fridgemanager.REST;

import com.example.kevin.fridgemanager.Activities.LoginActivity;
import com.example.kevin.fridgemanager.DomainModels.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class UserRestClient {

    public interface callbackTest{
        void callOnSuccess();
    }

    private static final String TAG = "UserRestClient";

//    private static final String BASE_URL = "http://ec2-18-236-130-40.us-west-2.compute.amazonaws.com:3000";
    private static final String BASE_URL = "http://10.0.2.2:3000";
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

    public static void getUserExists(String username, String password, final callbackTest loginActivity){
        String urlWithQuery = "/users?user_id=" + username + "&password=" + password;

        get(urlWithQuery, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response.length() > 0){
                    System.out.println("Successfully received user!");
                    loginActivity.callOnSuccess();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static void postUserData(User user){
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println(responseString);
            }
        });
    }

}
