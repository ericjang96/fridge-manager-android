package com.example.kevin.fridgemanager.Translators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;

public class FridgeToListOfIngredientsTranslator {
    public static List<Ingredient> translate(JSONArray fridgeResponse) throws JSONException {
        JSONObject fridge = fridgeResponse.getJSONObject(0);
        JSONArray ingredientsArray = fridge.getJSONArray("ingredients");
        List<Ingredient> result = new ArrayList<>();

        for(int i = 0; i < ingredientsArray.length(); i++){
            JSONObject val = ingredientsArray.getJSONObject(i);
            String name = val.getString("name");
            Integer amount = val.getInt("amount");
            String unit = val.getString("amountUnit");

            Ingredient ingredient = new Ingredient(name, amount, unit);
            result.add(ingredient);
        }

        return result;
    }
}
