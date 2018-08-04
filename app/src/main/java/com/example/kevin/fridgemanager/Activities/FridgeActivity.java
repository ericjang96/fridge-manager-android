package com.example.kevin.fridgemanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import java.util.ArrayList;
import java.util.List;

public class FridgeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        // Grabbing the recycler view and setting it as a linear layout
        // making the page look like a normal scrollable list
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(llm);

        // Get fridge data
        FridgeRestClient.getFridgeData(rv);
    }

    public void refresh(View view){
        RecyclerView rv = findViewById(R.id.rv);
        FridgeRestClient.getFridgeData(rv);
    }
}