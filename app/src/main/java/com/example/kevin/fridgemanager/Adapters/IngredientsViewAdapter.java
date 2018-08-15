package com.example.kevin.fridgemanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.Fragments.EditIngredientDialogFragment;
import com.example.kevin.fridgemanager.R;

import java.lang.reflect.Field;
import java.util.List;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;

// Adapter used for the recycler view that displays all ingredients in a fridge
public class IngredientsViewAdapter extends RecyclerView.Adapter<IngredientsViewAdapter.IngredientViewHolder> {
    //vars
    private List<Ingredient> ingredients;
    private Context context;

    public IngredientsViewAdapter(List<Ingredient> ingredients, Context context){
        this.ingredients = ingredients;
        this.context = context;
    }


    // Ingredient view holder that references the card view defined to hold one ingredient item
    // class defined within scope of the package
    protected static class IngredientViewHolder extends RecyclerView.ViewHolder {
        //widgets
        public CardView mCardView;
        public TextView mIngredientName, mIngredientAmount, mIngredientUnit;
        public Button mInsertButton, mRemoveButton;
        public EditTextListener editTextListener;

        IngredientViewHolder(View itemView, EditTextListener editTextListener) {
            super(itemView);

            this.editTextListener = editTextListener;
            mCardView = itemView.findViewById(R.id.ingredient_card_view);
            mIngredientName = itemView.findViewById(R.id.ingredient_name);
            mIngredientAmount = itemView.findViewById(R.id.ingredient_amount);
            mIngredientUnit = itemView.findViewById(R.id.ingredient_unit);
            mInsertButton = itemView.findViewById(R.id.add_ingredient_button);
            mRemoveButton = itemView.findViewById(R.id.remove_ingredient_button);
            mIngredientAmount.addTextChangedListener(editTextListener);
        }
    }



    // Return the view holder once created
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_card_view, parent, false);
        return new IngredientViewHolder(v, new EditTextListener());
    }


    // Update a part of view holder you are interested in
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position, @NonNull List<Object> payloads) {
        holder.editTextListener.updatePosition(holder.getAdapterPosition());
        if(!payloads.isEmpty()) {
            String amt = payloads.get(0).toString();
            Integer originalValue = Integer.parseInt(holder.mIngredientAmount.getText().toString());
            Integer additionalValue = Integer.parseInt(amt);
            holder.mIngredientAmount.setText(String.valueOf(originalValue + additionalValue));
        }
        else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }


    // This method defines the way each ingredient data from the list should bind with the view holder
    // Used to update the whole recycler view
    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder holder, int position) {
        final Ingredient currentIngred = ingredients.get(position);
        final String name = currentIngred.getName();
        int amount = currentIngred.getAmount();
        String unit = currentIngred.getUnit();

        // Don't display "number"
        if(unit.equals("number")){
            unit = "";
        }

        holder.mIngredientName.setText(name);
        holder.mIngredientAmount.setText(String.valueOf(amount));
        holder.mIngredientUnit.setText(unit);

        holder.mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditIngredientDialogFragment editDialog = EditIngredientDialogFragment.newInstance("insert", currentIngred);
                FridgeActivity activity = (FridgeActivity) context;
                editDialog.show(activity.getSupportFragmentManager(), "edit dialog");
            }
        });

        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditIngredientDialogFragment editDialog = EditIngredientDialogFragment.newInstance("remove", currentIngred);
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

    public List<Ingredient> getIngredients(){
        return ingredients;
    }


    // Referencing https://stackoverflow.com/questions/31844373/saving-edittext-content-in-recyclerview
    // A text listener is required to make sure that a changed item in the recycler view will have its position
    // updated correctly to display once recycled (scrolled far down and back up).
    private class EditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Ingredient updatedIngredient = ingredients.get(position);
            String newAmount = charSequence.toString();
            updatedIngredient.setAmount(Integer.parseInt(newAmount));
            ingredients.set(position, updatedIngredient);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //no op
        }
    }
}
