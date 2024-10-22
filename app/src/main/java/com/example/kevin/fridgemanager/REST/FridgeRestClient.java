package com.example.kevin.fridgemanager.REST;

import android.util.Log;

import com.example.kevin.fridgemanager.DomainModels.User;
import com.example.kevin.fridgemanager.Singletons.SharedPrefs;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by kevin on Aug 3, 2018
 **/
public class FridgeRestClient extends AbstractRestClient{
    private static final String TAG = "FridgeRestClient";

    public static void createNewFridge(User user){
        RequestParams params = new RequestParams();
        params.add("user_id", user.getUserId());
        params.add("fridge_id", user.getFridgeId());

        post("/fridges", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: Successfully created a new Fridge!");
            }
        });
    }

    public static void insertIngredientData(Ingredient ingredient){
        String fridge_id = SharedPrefs.read("fridge_id");
        // use time since epoch
        long boughtDateMilliseconds = ingredient.getBoughtDate().getTime();
        long expireDateMilliseconds = ingredient.getExpiryDate().getTime();

        RequestParams params = new RequestParams();
        params.add("name", ingredient.getName());
        params.add("boughtDate", String.valueOf(boughtDateMilliseconds));
        params.add("expiryDate", String.valueOf(expireDateMilliseconds));
        params.add("amountUnit", ingredient.getUnit());
        params.add("amount", ingredient.getAmountString());
        params.add("type", "insert");
        params.add("fridge_id", fridge_id);

        put("/fridges/ingredients", params, new JsonHttpResponseHandler(){
            @Override
            public void onStart(){
                System.out.println("Sending PUT request...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: Successfully inserted ingredient");
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

    public static void removeIngredientAmount(String name, int amount){
        String fridge_id = SharedPrefs.read("fridge_id");
        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("amount", String.valueOf(amount));
        params.add("type", "removeAmount");
        params.add("fridge_id", fridge_id);

        put("/fridges/ingredients", params, new JsonHttpResponseHandler(){
            @Override
            public void onStart(){
                System.out.println("Sending PUT request...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    System.out.println(response.getString("response"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("Failed to remove: " + responseString);
            }
        });
    }

    public static void deleteWholeIngredient(String name){
        String fridge_id = SharedPrefs.read("fridge_id");
        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("type", "delete");
        params.add("fridge_id", fridge_id);

        put("/fridges/ingredients", params, new JsonHttpResponseHandler(){
            @Override
            public void onStart(){
                System.out.println("Sending PUT request...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("Success");
            }
        });
    }
}
