package com.example.kevin.fridgemanager.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.Fragments.EditIngredientDialogFragment;
import com.example.kevin.fridgemanager.R;

import java.util.ArrayList;
import java.util.List;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;

// Adapter used for the recycler view that displays all ingredients in a fridge
public class FridgeAdapter extends RecyclerView.Adapter<FridgeAdapter.IngredientViewHolder> {
    //vars
    private List<Ingredient> ingredients;
    private Context context;

    public FridgeAdapter(List<Ingredient> ingredients, Context context){
        this.ingredients = ingredients;
        this.context = context;
    }

    // Ingredient view holder that references the card view defined to hold one ingredient item
    // class defined within scope of the package
    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        //widgets
        CardView mCardView;
        TextView mIngredientName, mIngredientAmount;
        Button mInsertButton, mRemoveButton;

        IngredientViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cv);
            mIngredientName = itemView.findViewById(R.id.ingredient_name);
            mIngredientAmount = itemView.findViewById(R.id.ingredient_amount);
            mInsertButton = itemView.findViewById(R.id.add_ingredient_button);
            mRemoveButton = itemView.findViewById(R.id.remove_ingredient_button);
        }
    }

    // Return the view holder once created
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_card_view, viewGroup, false);
        return new IngredientViewHolder(v);
    }

    // This method defines the way each ingredient data from the list should bind with the view holder
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int i) {
        Ingredient currentIngred = ingredients.get(i);
        String amountText;
        if(currentIngred.getUnit().equals("number")){
            amountText = String.valueOf(currentIngred.getAmount());
        }
        else{
            amountText = currentIngred.getAmount() + " " + currentIngred.getUnit();
        }

        holder.mIngredientName.setText(currentIngred.getName());
        holder.mIngredientAmount.setText(amountText);

        holder.mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditIngredientDialogFragment editDialog = EditIngredientDialogFragment.newInstance("insert");
                FridgeActivity activity = (FridgeActivity) context;
                editDialog.show(activity.getSupportFragmentManager(), "edit dialog");
            }
        });

        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditIngredientDialogFragment editDialog = EditIngredientDialogFragment.newInstance("remove");
                FridgeActivity activity = (FridgeActivity) context;
                editDialog.show(activity.getSupportFragmentManager(), "edit dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
