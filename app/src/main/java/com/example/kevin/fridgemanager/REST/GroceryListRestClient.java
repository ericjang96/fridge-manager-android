package com.example.kevin.fridgemanager.REST;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Activities.GroceryListActivity;
import com.example.kevin.fridgemanager.Activities.UserGroceryListsActivity;
import com.example.kevin.fridgemanager.Adapters.GroceryItemsViewAdapter;
import com.example.kevin.fridgemanager.Adapters.GroceryListsViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.Translators.GroceryListTranslator;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GroceryListRestClient extends AbstractRestClient {

    public static void getGroceryListWithId(String grocery_list_id, final RecyclerView rv, final GroceryListActivity activity){
        String urlWithQuery = "/groceryLists?grocery_list_id=" + grocery_list_id;

        get(urlWithQuery, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject JSONList = response.getJSONObject(0);
                    GroceryList groceryList = GroceryListTranslator.translate(JSONList);
                    GroceryItemsViewAdapter adapter = new GroceryItemsViewAdapter(groceryList.getGroceryItems(), rv.getContext());
                    rv.setAdapter(adapter);
                    rv.setVisibility(View.VISIBLE);
                    activity.updateList(adapter, groceryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void insertNewGroceryList(GroceryList list){
        RequestParams params = new RequestParams();
        params.add("grocery_list_id", list.getGroceryListId());
        params.add("name", list.getName());

        post("/groceryLists", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
            }
        });
    }

    public static void insertGroceryItem(GroceryItem item){
        RequestParams params = new RequestParams();
        params.add("name", item.getName());
        params.add("amount", String.valueOf(item.getAmount()));
        params.add("comment", item.getComment());
        params.add("type", "insert");

        put("/groceryLists", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("Successfully inserted a new item!");
            }
        });
    }

    public static void deleteGroceryItem(String name){
        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("type", "delete");

        put("/groceryLists", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
            }
        });
    }
}
