package com.example.kevin.fridgemanager.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevin.fridgemanager.R;

import java.util.ArrayList;
import java.util.List;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;


public class FridgeAdapter extends RecyclerView.Adapter<FridgeAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredients;

    public FridgeAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView ingredName;
        TextView ingredAmount;

        IngredientViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            ingredName = itemView.findViewById(R.id.ingredient_name);
            ingredAmount = itemView.findViewById(R.id.ingredient_amount);
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_card_view, viewGroup, false);
        return new IngredientViewHolder(v);
    }

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

        holder.ingredName.setText(currentIngred.getName());
        holder.ingredAmount.setText(amountText);
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
