package com.example.kevin.fridgemanager.REST;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.Adapters.FridgeAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.Translators.FridgeToListOfIngredientsTranslator;
import cz.msebera.android.httpclient.Header;

public class FridgeRestClient {
    // Change this every time you restart the AWS server
//    private static final String BASE_URL = "http://ec2-18-236-130-40.us-west-2.compute.amazonaws.com:3000";
    private static final String BASE_URL = "http://10.0.2.2:3000";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void getFridgeData(final RecyclerView rv, final View loading){
        get("/fridges", null, new JsonHttpResponseHandler() {
            @Override
            public void onStart(){
                System.out.println("Getting data...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("Successfully obtained the response!");
                List<Ingredient> ingredients = null;
                try {
                    ingredients = FridgeToListOfIngredientsTranslator.translate(response);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                FridgeAdapter adapter = new FridgeAdapter(ingredients);
                rv.setAdapter(adapter);
                rv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static void insertIngredientData(Ingredient ingredient){
        RequestParams params = new RequestParams();
        params.add("name", ingredient.getName());
        params.add("boughtDate", ingredient.getBoughtDate());
        params.add("expiryDate", ingredient.getExpiryDate());
        params.add("amountUnit", ingredient.getUnit());
        params.add("amount", ingredient.getAmountString());
        params.add("type", "insert");

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
