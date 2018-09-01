package com.example.kevin.fridgemanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.Fragments.EditIngredientDialogFragment;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import java.util.List;

/**
 * Created by kevin on Aug 30, 2018
 **/
public class IngredientsViewAdapter extends RecyclerViewAdapter{

    public IngredientsViewAdapter(Context context) {
        super(context);
    }

    // View Holder for ingredients
    protected class TestIngredientsViewHolder extends GenericViewHolder{
        //widgets
        TextView ingredientName, ingredientAmount, ingredientUnit;
        Button insertButton, removeButton, deleteButton;
        EditTextListener editTextListener;

        //vars
        Context context = getContext();

        protected TestIngredientsViewHolder(View view) {
            super(view);
            initialize();

            // When clicking the + button, opens up a new EditIngredientDialogFragment
            insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ingredient ingredient = new Ingredient(ingredientName.getText().toString(), Integer.parseInt(ingredientAmount.getText().toString()), ingredientUnit.getText().toString());
                    EditIngredientDialogFragment editDialog = EditIngredientDialogFragment.newInstance("insert", ingredient);
                    FridgeActivity activity = (FridgeActivity) context;
                    editDialog.show(activity.getSupportFragmentManager(), "edit dialog");
                }
            });

            // When clicking the - button, opens up a new EditIngredientDialogFragment
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ingredient ingredient = new Ingredient(ingredientName.getText().toString(), Integer.parseInt(ingredientAmount.getText().toString()), ingredientUnit.getText().toString());
                    EditIngredientDialogFragment editDialog = EditIngredientDialogFragment.newInstance("remove", ingredient);
                    FridgeActivity activity = (FridgeActivity) context;
                    editDialog.show(activity.getSupportFragmentManager(), "edit dialog");
                }
            });

            // Delete button removes whole ingredient
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = ingredientName.getText().toString();
                    FridgeRestClient.deleteWholeIngredient(name);
                    FridgeActivity activity = (FridgeActivity) context;
                    activity.removeWholeIngredient(name);
                }
            });
        }

        protected void initialize(){
            ingredientName = (TextView) initViewAndGetView(R.id.ingredient_name);
            ingredientAmount = (TextView) initViewAndGetView(R.id.ingredient_amount);
            ingredientUnit = (TextView) initViewAndGetView(R.id.ingredient_unit);

            insertButton = (Button) initViewAndGetView(R.id.add_ingredient_button);
            removeButton = (Button) initViewAndGetView(R.id.remove_ingredient_button);
            deleteButton = (Button) initViewAndGetView(R.id.delete_ingredient_button);

            editTextListener = new EditTextListener();
            setTextWatcher(editTextListener);
            ingredientAmount.addTextChangedListener(editTextListener);
        }
    }

    // Creating view holder
    @Override
    public TestIngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.cardview_ingredient, viewGroup, false);
        return new TestIngredientsViewHolder(view);
    }

    // Creating the view
    @Override
    protected View createView(final Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_ingredient, viewGroup, false);
        return view;
    }

    // How to bind view with data
    @Override
    protected void bindView(Object item, GenericViewHolder viewHolder) {
        Ingredient ingredient = (Ingredient) item;
        if (item != null) {
            viewHolder.getTextView(R.id.ingredient_name).setText(ingredient.getName());
            viewHolder.getTextView(R.id.ingredient_amount).setText(String.valueOf(ingredient.getAmount()));
            viewHolder.getTextView(R.id.ingredient_unit).setText(ingredient.getUnit());
        }
    }

    // Bind view when extra arguments are given
    @Override
    protected void bindViewPayloads(Object item, GenericViewHolder viewHolder, @NonNull List payloads) {
        EditTextListener editTextListener = (EditTextListener) viewHolder.getTextWatcher();
        int position = viewHolder.getAdapterPosition();
        editTextListener.updatePosition(position);

        TextView ingredientAmount = viewHolder.getTextView(R.id.ingredient_amount);

        //TODO: right now assuming notifyItemChanged is only called by update Item Number for this adapter
        // TODO: which might be true, but should change the parameters for more robust handling
        if(!payloads.isEmpty()) {
            String amt = payloads.get(0).toString();
            Integer originalValue = Integer.parseInt(ingredientAmount.getText().toString());
            Integer additionalValue = Integer.parseInt(amt);

            Integer total = originalValue + additionalValue;
            if(total >= 0){
                ingredientAmount.setText(String.valueOf(originalValue + additionalValue));
            }
            else{
                ingredientAmount.setText("0");
            }
        }
        else {
            bindView(item, viewHolder);
        }
    }

    // Referencing https://stackoverflow.com/questions/31844373/saving-edittext-content-in-recyclerview
    // A text listener is required to make sure that a changed item in the recycler view will have its position
    // updated correctly to display once recycled (scrolled far down and back up).
    protected class EditTextListener implements TextWatcher {
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
            Ingredient updatedIngredient = (Ingredient) getList().get(position);
            String newAmount = charSequence.toString();
            updatedIngredient.setAmount(Integer.parseInt(newAmount));
            getList().set(position, updatedIngredient);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //no op
        }
    }
}
