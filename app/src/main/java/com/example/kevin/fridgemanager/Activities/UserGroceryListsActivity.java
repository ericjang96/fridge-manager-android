/*
Created by Kevin Kwon on August 17 2018
 */

package com.example.kevin.fridgemanager.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Adapters.GroceryListsViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.Fragments.AddNewUserGroceryListDialogFragment;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;

import java.util.List;

public class UserGroceryListsActivity extends AppCompatActivity {

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;
    private View mLoadingView;

    //vars
    private List<GroceryList> groceryLists;
    private GroceryListsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_grocery_lists);

        mRecyclerView = findViewById(R.id.grocery_lists_recycler_view);
        mLayoutManager = new NpaLinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLoadingView = findViewById(R.id.user_grocery_lists_refresh_progress_bar);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        UserRestClient.getUsersGroceryLists(mRecyclerView, mLoadingView, UserGroceryListsActivity.this);
    }

    public void updateLists(GroceryListsViewAdapter adapter, List<GroceryList> lists){
        this.adapter = adapter;
        this.groceryLists = lists;
    }

    public void showAddListPrompt(View view){
        AddNewUserGroceryListDialogFragment dialog = new AddNewUserGroceryListDialogFragment();
        dialog.show(getSupportFragmentManager(), "Add Ingredient Dialog");
    }

    public void refresh(View view) {
        mLoadingView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        UserRestClient.getUsersGroceryLists(mRecyclerView, mLoadingView,UserGroceryListsActivity.this);
    }

    public void addGroceryList(GroceryList list){
        int position = groceryLists.size();
        adapter.insertAt(list, position);
        adapter.notifyItemInserted(position);
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void removeGroceryList(String name){
        int position = findPositionByName(groceryLists, name);
        adapter.removeAt(position);
    }

    // finds position of grocery list in the list. If not found, return -1
    private int findPositionByName(List<GroceryList> list, String name){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }
}
