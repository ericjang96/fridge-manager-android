package com.example.kevin.fridgemanager.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Adapters.GroceryItemsViewAdapter;
import com.example.kevin.fridgemanager.Adapters.GroceryListsViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.Fragments.AddNewGroceryItemFragment;
import com.example.kevin.fridgemanager.Fragments.AddNewUserGroceryListDialogFragment;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.GroceryListRestClient;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity{

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;
    private TextView mListName;

    //vars
    private GroceryItemsViewAdapter adapter;
    private GroceryList groceryList;
    private String grocery_list_id;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        grocery_list_id = getIntent().getStringExtra("grocery_list_id");
        name = getIntent().getStringExtra("name");

        mListName = findViewById(R.id.grocery_list_name);
        mListName.setText(name);

        mRecyclerView = findViewById(R.id.grocery_items_recycler_view);
        mLayoutManager = new NpaLinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        GroceryListRestClient.setGroceryListId(grocery_list_id);
        GroceryListRestClient.getGroceryListWithId(grocery_list_id, mRecyclerView, GroceryListActivity.this);
    }

    public void updateList(GroceryItemsViewAdapter adapter, GroceryList list){
        this.adapter = adapter;
        this.groceryList = list;
    }

    public void Refresh(View view){
        GroceryListRestClient.getGroceryListWithId(grocery_list_id, mRecyclerView, GroceryListActivity.this);
    }

    public void showAddGroceryItem(View view){
        AddNewGroceryItemFragment dialog = new AddNewGroceryItemFragment();
        dialog.show(getSupportFragmentManager(), "Add Item Dialog");
    }

    public void addGroceryItem(GroceryItem item){
        int position = groceryList.getGroceryItems().size();
        adapter.insertAt(item, position);
        adapter.notifyItemInserted(position);
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void removeGroceryItem(String name){
        int position = findPositionByName(groceryList.getGroceryItems(), name);
        adapter.removeAt(position);
    }

    private int findPositionByName(List<GroceryItem> list, String name){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }
}
