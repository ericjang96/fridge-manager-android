package com.example.kevin.fridgemanager.Activities;

/**
 * Created by kevin on Aug 17, 2018
 **/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Activities.Helpers.RecyclerViewActivityHelper;
import com.example.kevin.fridgemanager.Adapters.GroceryListsViewAdapter;
import com.example.kevin.fridgemanager.Adapters.RecyclerViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;
import com.example.kevin.fridgemanager.Fragments.AddNewUserGroceryListDialogFragment;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;

import java.util.ArrayList;
import java.util.List;

public class UserGroceryListsActivity extends AppCompatActivity implements RecyclerViewActivity {

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;
    private View mLoadingView;

    //vars
    private List<RecyclerViewItem> groceryLists;
    private GroceryListsViewAdapter adapter;
    private RecyclerViewActivityHelper helper = new RecyclerViewActivityHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_grocery_lists);

        init();
    }

    public void init() {
        mRecyclerView = findViewById(R.id.grocery_lists_recycler_view);
        mLayoutManager = new NpaLinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLoadingView = findViewById(R.id.user_grocery_lists_refresh_progress_bar);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        UserRestClient.getUsersGroceryLists(mRecyclerView, mLoadingView, UserGroceryListsActivity.this);
    }

    public void update(RecyclerViewAdapter adapter, List<GroceryList> items) {
        this.adapter = (GroceryListsViewAdapter) adapter;
        this.groceryLists = toBaseClass(items);
    }

    public void refresh(View view) {
        mLoadingView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        UserRestClient.getUsersGroceryLists(mRecyclerView, mLoadingView,UserGroceryListsActivity.this);
    }

    public void showAddItemPrompt(View view){
        AddNewUserGroceryListDialogFragment dialog = new AddNewUserGroceryListDialogFragment();
        dialog.show(getSupportFragmentManager(), "Add Ingredient Dialog");
    }


    public void addGroceryList(GroceryList list){
        groceryLists = helper.addItem(groceryLists, list, adapter, mRecyclerView);
    }

    public void removeGroceryList(String name){
        int position = helper.findPositionByName(groceryLists, name);
        adapter.removeAt(position);
    }


    public List<RecyclerViewItem> toBaseClass(List<GroceryList> lists){
        return new ArrayList<RecyclerViewItem>(lists);
    }
}
