package com.example.kevin.fridgemanager.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import static com.loopj.android.http.AsyncHttpClient.log;


public class FridgeActivity extends AppCompatActivity {
    private EditText nameView;
    private EditText boughtDateView;
    private EditText expiryDateView;
    private EditText unitView;
    private EditText amountView;

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
        FridgeRestClient.getFridgeData(rv, loading);


    }

    public void refresh(View view) {
        RecyclerView rv = findViewById(R.id.rv);
        View loading = findViewById(R.id.fridgeLoadingPanel);
        rv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        FridgeRestClient.getFridgeData(rv, loading);
    }

    public void showAddIngredientPrompt(View view){
        Context context = view.getContext();
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.add_ingredient_prompt, null);

        nameView = promptsView.findViewById(R.id.edit_ingredient_name);
        boughtDateView = promptsView.findViewById(R.id.edit_ingredient_boughtDate);
        expiryDateView = promptsView.findViewById(R.id.edit_ingredient_expiryDate);
        unitView = promptsView.findViewById(R.id.edit_ingredient_amountUnit);
        amountView = promptsView.findViewById(R.id.edit_ingredient_amount);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void sendIngredientRequest(View view){
        try{
            String name = nameView.getText().toString();
            String boughtDate = boughtDateView.getText().toString();
            String expiryDate = expiryDateView.getText().toString();
            String amountUnit = unitView.getText().toString();
            String amount = amountView.getText().toString();

            Integer amountInt = Integer.parseInt(amount);

            Ingredient ingredient = new Ingredient(name, boughtDate, expiryDate, amountUnit, amountInt);
            FridgeRestClient.putFridgeData(ingredient);
        }
        catch(Exception e){
            log.e("ERROR", e.getMessage());
        }

    }
}