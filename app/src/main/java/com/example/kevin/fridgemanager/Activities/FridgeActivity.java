/*
Created by Kevin Kwon on August 1 2018
 */
package com.example.kevin.fridgemanager.Activities;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.Fragments.AddIngredientDialogFragment;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import static com.loopj.android.http.AsyncHttpClient.log;

// TODO: Make a sharedPreferences file that is always up-to-date for offline mode. Once we have internet connection, push/pull to server

public class FridgeActivity extends AppCompatActivity {
    // Overridden on create method that initializes the required components in our Fridge Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        // Grabbing the recycler view and setting it as a linear layout
        // making the page look like a normal scrollable list
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(llm);

        View loading = findViewById(R.id.fridgeLoadingPanel);
        // Get fridge data
        // TODO: We should ping the server to see if we have a connection, and only make this call if we do. Otherwise, we should fall back to offline storage
        FridgeRestClient.getFridgeData(rv, loading);
    }

    // Refresh the list of ingredients shown in our fridge by sending a new GET request
    public void refresh(View view) {
        RecyclerView rv = findViewById(R.id.rv);
        View loading = findViewById(R.id.fridgeLoadingPanel);
        rv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        FridgeRestClient.getFridgeData(rv, loading);
    }

    //
    public void showAddIngredientPrompt(View view){
        AddIngredientDialogFragment dialog = new AddIngredientDialogFragment();
        dialog.show(getSupportFragmentManager(), "My Dialog");
    }

}