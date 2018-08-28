/*
Created by Kevin Kwon on August 17 2018
 */

package com.example.kevin.fridgemanager.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;

import java.util.List;

public class UserGroceryListsActivity extends AppCompatActivity {

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;

    //vars
    private List<GroceryList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_grocery_lists);

        mRecyclerView = findViewById(R.id.grocery_lists_recycler_view);
        mLayoutManager = new NpaLinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        UserRestClient.getUsersGroceryLists(mRecyclerView, UserGroceryListsActivity.this);
    }
}
