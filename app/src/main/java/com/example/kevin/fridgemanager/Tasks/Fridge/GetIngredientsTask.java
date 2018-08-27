package com.example.kevin.fridgemanager.Tasks.Fridge;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.Adapters.IngredientsViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.example.kevin.fridgemanager.Singletons.SharedPrefs;
import com.example.kevin.fridgemanager.Translators.FridgeToListOfIngredientsTranslator;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;


public class GetIngredientsTask extends AsyncTask<Void, Void, List<Ingredient>> {
    private WeakReference<Activity> activity;

    public GetIngredientsTask(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<Ingredient> doInBackground(Void... voids) {
        String fridge_id = SharedPrefs.read("fridge_id");
        String url = GlobalVariables.connectionURL + GlobalVariables.fridgeViewURL + "?fridge_id=" + fridge_id;

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                JSONArray jsonArray = new JSONArray(data);
                List<Ingredient> ingredients = FridgeToListOfIngredientsTranslator.translate(jsonArray);
                return ingredients;
            }

            // TODO: alert dialog with error message?
            else{
                return new ArrayList<>();
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Ingredient> ingredients) {
        FridgeActivity fridgeActivity = (FridgeActivity) activity.get();
        RecyclerView rv = fridgeActivity.findViewById(R.id.recycler_view_ingredients);
        IngredientsViewAdapter adapter = new IngredientsViewAdapter(ingredients, rv.getContext());
        rv.setAdapter(adapter);
        rv.setVisibility(View.VISIBLE);
        View loading = fridgeActivity.findViewById(R.id.fridgeLoadingPanel);
        loading.setVisibility(View.INVISIBLE);

        fridgeActivity.updateIngredients(adapter, ingredients);
    }

}
