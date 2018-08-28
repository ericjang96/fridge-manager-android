package com.example.kevin.fridgemanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.GroceryListActivity;
import com.example.kevin.fridgemanager.Activities.UserGroceryListsActivity;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;

import java.util.List;

public class GroceryListsViewAdapter extends RecyclerView.Adapter<GroceryListsViewAdapter.GroceryListsViewHolder> {
    //vars
    private List<GroceryList> groceryLists;
    private Context context;

    public GroceryListsViewAdapter(List<GroceryList> list, Context context) {
        this.groceryLists = list;
        this.context = context;
    }

    class GroceryListsViewHolder extends RecyclerView.ViewHolder {

        //widgets
        TextView mGroceryListName;
        Button mDeleteButton;
        CardView mCardView;

        GroceryListsViewHolder(@NonNull View groceryListItemView) {
            super(groceryListItemView);

            mCardView = groceryListItemView.findViewById(R.id.cardview_grocery_list);
            mGroceryListName = groceryListItemView.findViewById(R.id.grocery_list_name);
            mDeleteButton = groceryListItemView.findViewById(R.id.grocery_list_delete_button);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GroceryListActivity.class);
                    context.startActivity(intent);
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = mGroceryListName.getText().toString();
                    UserRestClient.deleteGroceryList(name);
                    UserGroceryListsActivity activity = (UserGroceryListsActivity) context;
                    activity.removeGroceryList(name);
                }
            });
        }
    }

    @NonNull
    @Override
    public GroceryListsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_grocery_list, viewGroup, false);
        return new GroceryListsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListsViewHolder groceryListsViewHolder, int position) {
        final GroceryList list = groceryLists.get(position);
        final String name = list.getName();

        groceryListsViewHolder.mGroceryListName.setText(name);
    }

    @Override
    public int getItemCount() {
        return groceryLists.size();
    }


    public void removeAt(int position){
        groceryLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, groceryLists.size());
    }

    public void insertAt(GroceryList list, int position){
        groceryLists.add(position, list);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, groceryLists.size());
    }
}
