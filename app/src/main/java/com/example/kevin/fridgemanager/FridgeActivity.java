package com.example.kevin.fridgemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapters.RVAdapter;
import DomainModels.Ingredient;

public class FridgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(llm);

        initData();
        RVAdapter adapter = new RVAdapter(ingredients);
        rv.setAdapter(adapter);
    }

    private List<Ingredient> ingredients;

    private void initData(){
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Carrot", 1000, "grams"));
        ingredients.add(new Ingredient("Potato", 1400, "grams"));
        ingredients.add(new Ingredient("Oyster Sauce", 330, "mL"));
        ingredients.add(new Ingredient("Cheddar Cheese", 250, "grams"));
        ingredients.add(new Ingredient("Onions", 500, "grams"));
        ingredients.add(new Ingredient("Tofu", 650, "grams"));
        ingredients.add(new Ingredient("Carrot", 1000, "grams"));
        ingredients.add(new Ingredient("Potato", 1400, "grams"));
        ingredients.add(new Ingredient("Oyster Sauce", 330, "mL"));
        ingredients.add(new Ingredient("Cheddar Cheese", 250, "grams"));
        ingredients.add(new Ingredient("Onions", 500, "grams"));
        ingredients.add(new Ingredient("Tofu", 650, "grams"));
        ingredients.add(new Ingredient("Carrot", 1000, "grams"));
        ingredients.add(new Ingredient("Potato", 1400, "grams"));
        ingredients.add(new Ingredient("Oyster Sauce", 330, "mL"));
        ingredients.add(new Ingredient("Cheddar Cheese", 250, "grams"));
        ingredients.add(new Ingredient("Onions", 500, "grams"));
        ingredients.add(new Ingredient("Tofu", 650, "grams"));
    }
}
