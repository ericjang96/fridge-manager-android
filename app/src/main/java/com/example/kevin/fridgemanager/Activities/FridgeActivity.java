package com.example.kevin.fridgemanager.Activities;
/**
 * Created by kevin on Aug 1, 2018
 **/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Adapters.IngredientsViewAdapter;
import com.example.kevin.fridgemanager.CallbackInterface.IKeyboardChange;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.Fragments.AddNewIngredientDialogFragment;
import com.example.kevin.fridgemanager.Generators.AlertDialogGenerator;
import com.example.kevin.fridgemanager.Managers.KeyboardManager;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.AsyncTasks.Fridge.GetIngredientsTask;

import java.util.List;

// TODO: Make a sharedPreferences file that is always up-to-date for offline mode. Once we have internet connection, push/pull to server

public class FridgeActivity extends AppCompatActivity implements IKeyboardChange{
    private static final String TAG = "FridgeActivity";

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;
    private View mLoadingView;

    //vars
    private IngredientsViewAdapter ingredientsAdapter;
    private List<Ingredient> ingredients;
    private int currentIngredientPosition;
    private KeyboardManager keyboardManager;

    // Overridden on create method that initializes the required components in our Fridge Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        // Grabbing the recycler view and setting it as a linear layout
        // making the page look like a normal scrollable list
        mRecyclerView = findViewById(R.id.recycler_view_ingredients);
        mLayoutManager = new NpaLinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLoadingView = findViewById(R.id.fridgeLoadingPanel);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        keyboardManager = new KeyboardManager(FridgeActivity.this);
        keyboardManager.setUpKeyboardChanges(findViewById(R.id.fridge_activity_layout), FridgeActivity.this);

        // Get fridge data
        // TODO: We should ping the server to see if we have a connection, and only make this call if we do. Otherwise, we should fall back to offline storage
        // TODO: Probably should use SQLite for offline storage.
        new GetIngredientsTask(FridgeActivity.this).execute();

    }


    public void updateIngredients(IngredientsViewAdapter adapter, List<Ingredient> ingredients) {
        this.ingredientsAdapter = adapter;
        this.ingredients = ingredients;
    }

    // Refresh the list of ingredients shown in our fridge by sending a new GET request
    public void refresh(View view) {
        mRecyclerView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
        new GetIngredientsTask(FridgeActivity.this).execute();
    }


    public void showAddIngredientPrompt(View view){
        AddNewIngredientDialogFragment dialog = new AddNewIngredientDialogFragment();
        dialog.show(getSupportFragmentManager(), "Add Ingredient Dialog");
    }

    // Obtain the recycler view, and the adapter. Get the ingredients and
    // try to find position of ingredients. If found, update the amount. If not, return false
    public boolean tryUpdateItemAmount(Ingredient ingredient, String amount){
        int position = findPositionByName(ingredients, ingredient.getName());
        // Not found
        if(position == -1){
            return false;
        }

        ingredientsAdapter.notifyItemChanged(position, amount);
        return true;
    }

    public void addIngredient(Ingredient ingredient){
        int position = positionToInsert(ingredients, ingredient);
        ingredientsAdapter.insertAt(ingredient, position);
        ingredientsAdapter.notifyItemInserted(position);
        mRecyclerView.smoothScrollToPosition(position);
    }

    // Calls the adapter to remove ingredient at position i
    public void removeWholeIngredient(String name){
        int position = findPositionByName(ingredients, name);
        ingredientsAdapter.removeAt(position);
    }


    public int positionToInsert(List<Ingredient> list, Ingredient ing){
        if(list.size() == 0)
            return 0;
        String name = ing.getName();
        int comparison = name.compareTo(list.get(0).getName());
        if(comparison < 1){
            return 0;
        }

        for(int i = 1; i < list.size(); i++){
            comparison = name.compareTo(list.get(i).getName());
            // if comparison is less than 1, new ingredient name is less than or equal
            // (i.e. earlier in alphabet)
            if(comparison < 1){
                return i;
            }
        }

        // If you can't find it, return the size of list
        return list.size();
    }


    // finds position of ingredient in the list. If not found, return -1
    private int findPositionByName(List<Ingredient> list, String name){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }


    public void updateCurrentIngredientPosition(Ingredient ingredient){
        currentIngredientPosition = findPositionByName(ingredients, ingredient.getName());
    }

    @Override
    public void doWhenKeyboardOpens() {
        mRecyclerView.smoothScrollToPosition(currentIngredientPosition);
    }

    @Override
    public void doWhenKeyboardCloses() {
        //do Nothing
    }
}