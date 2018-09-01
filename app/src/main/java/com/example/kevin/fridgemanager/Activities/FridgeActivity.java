package com.example.kevin.fridgemanager.Activities;
/**
 * Created by kevin on Aug 1, 2018
 **/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kevin.fridgemanager.Activities.Helpers.RecyclerViewActivityHelper;
import com.example.kevin.fridgemanager.Adapters.RecyclerViewAdapter;
import com.example.kevin.fridgemanager.Adapters.IngredientsViewAdapter;
import com.example.kevin.fridgemanager.CallbackInterface.IKeyboardChange;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;
import com.example.kevin.fridgemanager.Fragments.AddNewIngredientDialogFragment;
import com.example.kevin.fridgemanager.Managers.KeyboardManager;
import com.example.kevin.fridgemanager.Managers.NpaLinearLayoutManager;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.AsyncTasks.Fridge.GetIngredientsTask;

import java.util.ArrayList;
import java.util.List;

// TODO: Make a sharedPreferences file that is always up-to-date for offline mode. Once we have internet connection, push/pull to server

public class FridgeActivity extends AppCompatActivity implements IKeyboardChange, RecyclerViewActivity{
    private static final String TAG = "FridgeActivity";

    //widgets
    private RecyclerView mRecyclerView;
    private NpaLinearLayoutManager mLayoutManager;
    private View mLoadingView;

    //vars
    private IngredientsViewAdapter ingredientsAdapter;
    private List<RecyclerViewItem> ingredients;

    private int currentIngredientPosition;
    private KeyboardManager keyboardManager;
    private RecyclerViewActivityHelper helper = new RecyclerViewActivityHelper();

    // Overridden on create method that initializes the required components in our Fridge Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        init();
    }

    public void init() {
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

    public void update(RecyclerViewAdapter adapter, List<Ingredient> items) {
        this.ingredientsAdapter = (IngredientsViewAdapter) adapter;
        this.ingredients = toBaseClass(items);
    }

    // Refresh the list of ingredients shown in our fridge by sending a new GET request
    public void refresh(View view) {
        mRecyclerView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
        new GetIngredientsTask(FridgeActivity.this).execute();
    }

    public void showAddItemPrompt(View view) {
        AddNewIngredientDialogFragment dialog = new AddNewIngredientDialogFragment();
        dialog.show(getSupportFragmentManager(), "Add Ingredient Dialog");
    }

    // Obtain the recycler view, and the adapter. Get the ingredients and
    // try to find position of ingredients. If found, update the amount. If not, return false
    public boolean tryUpdateItemAmount(Ingredient ingredient, String amount){
        int position = helper.findPositionByName(ingredients, ingredient.getName());
        // Not found
        if(position == -1){
            return false;
        }

        ingredientsAdapter.notifyItemChanged(position, amount);
        return true;
    }

    public void addIngredient(Ingredient ingredient){
        ingredients = helper.addItemAlphabetically(ingredients, ingredient, ingredientsAdapter, mRecyclerView);
    }

    // Calls the adapter to remove ingredient at position i
    public void removeWholeIngredient(String name){
        int position = helper.findPositionByName(ingredients, name);
        ingredientsAdapter.removeAt(position);
    }


    public void updateCurrentIngredientPosition(Ingredient ingredient){
        currentIngredientPosition = helper.findPositionByName(ingredients, ingredient.getName());
    }

    @Override
    public void doWhenKeyboardOpens() {
        mRecyclerView.smoothScrollToPosition(currentIngredientPosition);
    }

    @Override
    public void doWhenKeyboardCloses() {
        //do Nothing
    }

    public List<RecyclerViewItem> toBaseClass(List<Ingredient> ingredients){
        return new ArrayList<RecyclerViewItem>(ingredients);
    }
}