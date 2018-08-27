package com.example.kevin.fridgemanager.Translators;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserJSONArrayToListOfUsernames {
    public static List<String> translate(JSONArray userResponse) throws JSONException {
        List<String> result = new ArrayList<>();
        for(int i = 0 ; i < userResponse.length(); i++){
            JSONObject user = userResponse.getJSONObject(i);
            result.add(user.getString("user_id"));
        }

        return result;
    }
}
