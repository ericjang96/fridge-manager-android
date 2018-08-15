/*
Created by Kevin Kwon on August 1 2018
 */
package com.example.kevin.fridgemanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Adapters.IngredientsViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.Fragments.AddNewIngredientDialogFragment;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import java.util.List;

// TODO: Make a sharedPreferences file that is always up-to-date for offline mode. Once we have internet connection, push/pull to server

public class FridgeActivity extends AppCompatActivity {
    private static final String TAG = "FridgeActivity";

    //widgets
    private RecyclerView mRecyclerView;

    // Overridden on create method that initializes the required components in our Fridge Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        // Grabbing the recycler view and setting it as a linear layout
        // making the page look like a normal scrollable list
        mRecyclerView = findViewById(R.id.recycler_view_ingredients);
        LinearLayoutManager llm = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(llm);

        View loading = findViewById(R.id.fridgeLoadingPanel);

        // Get fridge data
        // TODO: We should ping the server to see if we have a connection, and only make this call if we do. Otherwise, we should fall back to offline storage
        FridgeRestClient.getFridgeData(mRecyclerView, loading);
        //TODO: Add an observer/listener so that after data is retrieved we can have global variables of adapter/list of ingredients
    }

    // Refresh the list of ingredients shown in our fridge by sending a new GET request
    public void refresh(View view) {
        RecyclerView rv = findViewById(R.id.recycler_view_ingredients);
        View loading = findViewById(R.id.fridgeLoadingPanel);
        rv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        FridgeRestClient.getFridgeData(rv, loading);
    }

    //
    public void showAddIngredientPrompt(View view){
        AddNewIngredientDialogFragment dialog = new AddNewIngredientDialogFragment();
        dialog.show(getSupportFragmentManager(), "Add Ingredient Dialog");
    }

    // Obtain the recycler view, and the adapter. Get the ingredients and
    // try to find position of ingredients. If found, update the amount. If not, return false
    public boolean tryUpdateItemAmount(String name, String amount){
        IngredientsViewAdapter adapter = (IngredientsViewAdapter) mRecyclerView.getAdapter();
        assert adapter != null;
        List<Ingredient> ingredients = adapter.getIngredients();
        int position = findPositionByName(ingredients, name);

        // Not found
        if(position == -1){
            return false;
        }

        adapter.notifyItemChanged(findPositionByName(ingredients, name), amount);
        return true;
    }

    public void addIngredient(Ingredient ingredient){
        IngredientsViewAdapter adapter = (IngredientsViewAdapter) mRecyclerView.getAdapter();
        assert adapter != null;
        List<Ingredient> ingredients = adapter.getIngredients();
        ingredients.add(ingredient);
    }

    // finds position of ingredient in the list. If not found, return -1
    public int findPositionByName(List<Ingredient> list, String name){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name))
                return i;
        }

        return -1;
    }
}