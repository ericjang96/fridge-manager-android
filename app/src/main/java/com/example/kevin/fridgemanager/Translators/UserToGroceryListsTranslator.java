package com.example.kevin.fridgemanager.Translators;

import com.example.kevin.fridgemanager.DomainModels.GroceryList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on Aug 28, 2018
 **/
public class UserToGroceryListsTranslator {
    public static List<GroceryList> translate(JSONObject user) throws JSONException {
        List<GroceryList> result = new ArrayList<>();
        JSONArray listsArray = user.getJSONArray("groceryLists");

        for(int i = 0 ; i < listsArray.length(); i++){
            JSONObject list = listsArray.getJSONObject(i);
            String name = list.getString("name");
            String grocery_list_id = list.getString("grocery_list_id");
            result.add(new GroceryList(name, grocery_list_id));
        }

        return result;
    }
}
