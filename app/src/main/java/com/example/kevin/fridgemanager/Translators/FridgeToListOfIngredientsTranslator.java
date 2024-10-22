package com.example.kevin.fridgemanager.Translators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;

/**
 * Created by kevin on Aug 4, 2018
 **/
public class FridgeToListOfIngredientsTranslator {
    public static List<Ingredient> translate(JSONArray fridgeResponse) throws JSONException {
        List<Ingredient> result = new ArrayList<>();
        for(int i = 0 ; i < fridgeResponse.length(); i++){
            JSONObject ing = fridgeResponse.getJSONObject(i);
            result.add(new Ingredient(ing.getString("_id"), ing.getInt("amount"), ing.getString("unit")));
        }

        return result;
    }
}
