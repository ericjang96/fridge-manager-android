package com.example.kevin.fridgemanager.Translators;

import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on Aug 29, 2018
 **/
public class GroceryListTranslator {
    public static GroceryList translate(JSONObject groceryList) throws JSONException {
        String grocery_list_id = groceryList.getString("grocery_list_id");
        String grocery_list_name = groceryList.getString("name");
        JSONArray JSONitems = groceryList.getJSONArray("grocery_items");

        List<GroceryItem> items = new ArrayList<>();
        for(int i = 0 ; i < JSONitems.length(); i++){
            JSONObject JSONItem = JSONitems.getJSONObject(i);
            String name = JSONItem.getString("name");
            int amount = JSONItem.getInt("amount");
            String comment = JSONItem.getString("comment");

            GroceryItem item = new GroceryItem(name, amount, comment);
            items.add(item);
        }

        return new GroceryList(grocery_list_name, grocery_list_id, items);
    }
}
