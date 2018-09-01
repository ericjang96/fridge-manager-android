package com.example.kevin.fridgemanager.Activities;
/**
 * Created by kevin on Aug 28, 2018
 **/
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.Helpers.RecyclerViewActivityHelper;
import com.example.kevin.fridgemanager.Adapters.GroceryItemsViewAdapter;
import com.example.kevin.fridgemanager.Adapters.GroceryListsViewAdapter;
import com.example.kevin.fridgemanager.Adapters.RecyclerViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;
import com.example.kevin.fridgemanager.Fragments.AddNewGroceryItemFragment;
import com.example.kevin.fridgemanager.Fragments.AddNewUserGroceryListDialogFragment;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.GroceryListRestClient;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity implements RecyclerViewActivity{

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;
    private TextView mListName;
    private View mLoadingView;

    //vars
    private GroceryItemsViewAdapter adapter;
    private List<RecyclerViewItem> groceryItems;
    private String grocery_list_id;
    private String name;
    private RecyclerViewActivityHelper helper = new RecyclerViewActivityHelper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        init();
    }

    public void init() {
        grocery_list_id = getIntent().getStringExtra("grocery_list_id");
        name = getIntent().getStringExtra("name");

        mListName = findViewById(R.id.grocery_list_name);
        mListName.setText(name);

        mLoadingView = findViewById(R.id.grocery_list_refresh_progress_bar);
        mRecyclerView = findViewById(R.id.grocery_items_recycler_view);
        mLayoutManager = new NpaLinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        GroceryListRestClient.setGroceryListId(grocery_list_id);
        GroceryListRestClient.getGroceryListWithId(mRecyclerView, mLoadingView, GroceryListActivity.this);
    }

    public void update(RecyclerViewAdapter adapter, List<GroceryItem> items) {
        this.adapter = (GroceryItemsViewAdapter) adapter;
        this.groceryItems = toBaseClass(items);
    }

    public void refresh(View view){
        mRecyclerView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
        GroceryListRestClient.getGroceryListWithId(mRecyclerView, mLoadingView, GroceryListActivity.this);
    }

    public void showAddItemPrompt(View view){
        AddNewGroceryItemFragment dialog = new AddNewGroceryItemFragment();
        dialog.show(getSupportFragmentManager(), "Add Item Dialog");
    }

    public void addGroceryItem(GroceryItem item){
        groceryItems = helper.addItem(groceryItems, item, adapter, mRecyclerView);
    }

    public void removeGroceryItem(String name){
        int position = helper.findPositionByName(groceryItems, name);
        adapter.removeAt(position);
    }

    public List<RecyclerViewItem> toBaseClass(List<GroceryItem> items){
        return new ArrayList<RecyclerViewItem>(items);
    }
}
