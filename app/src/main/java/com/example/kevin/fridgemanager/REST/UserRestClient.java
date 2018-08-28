package com.example.kevin.fridgemanager.REST;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.kevin.fridgemanager.Activities.UserGroceryListsActivity;
import com.example.kevin.fridgemanager.Adapters.GroceryListsViewAdapter;
import com.example.kevin.fridgemanager.Adapters.IngredientsViewAdapter;
import com.example.kevin.fridgemanager.CallbackInterface.IFridgeUpdateIngredients;
import com.example.kevin.fridgemanager.CallbackInterface.ILoginCallback;
import com.example.kevin.fridgemanager.CallbackInterface.ISignUpCallback;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.DomainModels.User;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.example.kevin.fridgemanager.Singletons.SharedPrefs;
import com.example.kevin.fridgemanager.Translators.UserToGroceryListsTranslator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

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

    private static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.put(getAbsoluteUrl(url), params, responseHandler);
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

    public static void getUsersGroceryLists(final RecyclerView rv, final UserGroceryListsActivity activity){
        String urlWithQuery = "/users?user_id=" + SharedPrefs.read(GlobalVariables.user_id);

        get(urlWithQuery, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject user = response.getJSONObject(0);
                    List<GroceryList> groceryLists = UserToGroceryListsTranslator.translate(user);
                    GroceryListsViewAdapter adapter = new GroceryListsViewAdapter(groceryLists, rv.getContext());
                    rv.setAdapter(adapter);
                    rv.setVisibility(View.VISIBLE);
                    activity.updateLists(adapter, groceryLists);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void insertNewGroceryList(GroceryList list){
        RequestParams params = new RequestParams();
        params.add("name", list.getName());
        params.add("grocery_list_id", list.getGroceryListId());
        params.add("user_id", SharedPrefs.read(GlobalVariables.user_id));
        params.add("type", "insert");

        put("/users/groceryLists", params, new JsonHttpResponseHandler(){
            @Override
            public void onStart(){
                System.out.println("Sending PUT request...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: Successfully inserted grocery list");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.i(TAG, "onFailure: " + errorResponse.getString("response"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
