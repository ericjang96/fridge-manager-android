package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevin.fridgemanager.R;

import java.util.List;

import DomainModels.Ingredient;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.IngredientViewHolder> {
    List<Ingredient> ingredients;

    public RVAdapter(List<Ingredient> ingredients){
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
        IngredientViewHolder ivh = new IngredientViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int i) {
        Ingredient currentIngred = ingredients.get(i);
        String amountText = currentIngred.getAmount() + " " + currentIngred.getUnit();

        holder.ingredName.setText(currentIngred.getName());
        holder.ingredAmount.setText(amountText);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
