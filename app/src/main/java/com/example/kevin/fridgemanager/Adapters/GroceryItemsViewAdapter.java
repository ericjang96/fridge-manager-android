package com.example.kevin.fridgemanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.GroceryListActivity;
import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.GroceryListRestClient;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class GroceryItemsViewAdapter extends RecyclerView.Adapter<GroceryItemsViewAdapter.GroceryItemsViewHolder> {

    //vars
    private List<GroceryItem> groceryItems;
    private Context context;

    public GroceryItemsViewAdapter(List<GroceryItem> items, Context context){
        this.groceryItems = items;
        this.context = context;
    }

    protected class GroceryItemsViewHolder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener{
        // widgets
        private CardView mCardView;
        private TextView mItemName, mItemComment, mItemAmount;
        private Button mDeleteButton;
        private ExpandableLayout mExpandableLayout;
        private ImageView mExpandIcon;

        public GroceryItemsViewHolder(@NonNull View groceryItemView) {
            super(groceryItemView);

            mExpandableLayout = groceryItemView.findViewById(R.id.expandable_layout_grocery_item);
            mCardView = groceryItemView.findViewById(R.id.cardview_grocery_item);
            mItemName = groceryItemView.findViewById(R.id.grocery_item_name);
            mItemComment = groceryItemView.findViewById(R.id.grocery_item_comment);
            mItemAmount = groceryItemView.findViewById(R.id.grocery_item_amount);
            mDeleteButton = groceryItemView.findViewById(R.id.delete_grocery_item_button);
            mExpandIcon = groceryItemView.findViewById(R.id.grocery_item_expand_icon);

            mExpandableLayout.setInterpolator(new LinearInterpolator());
            mExpandableLayout.setOnExpansionUpdateListener(this);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mItemComment.getText().toString().equals("")){
                        mExpandableLayout.toggle();
                    }
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = mItemName.getText().toString();
                    GroceryListRestClient.deleteGroceryItem(name);
                    GroceryListActivity activity = (GroceryListActivity) context;
                    activity.removeGroceryItem(name);
                }
            });
        }



        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            if (state == ExpandableLayout.State.EXPANDED) {
                mExpandIcon.setImageResource(R.drawable.ic_expand_less_black_24dp);
            }
            else if(state == ExpandableLayout.State.COLLAPSED){
                mExpandIcon.setImageResource(R.drawable.ic_expand_more_black_24dp);
            }
        }
    }

    @NonNull
    @Override
    public GroceryItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_grocery_item, viewGroup, false);
        return new GroceryItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryItemsViewHolder groceryItemsViewHolder, int position) {
        final GroceryItem item = groceryItems.get(position);
        final String name = item.getName();
        final String comment = item.getComment();
        final String amount = String.valueOf(item.getAmount());

        if(comment.equals("")){
            groceryItemsViewHolder.mExpandIcon.setVisibility(View.GONE);
        }

        groceryItemsViewHolder.mItemName.setText(name);
        groceryItemsViewHolder.mItemComment.setText(comment);
        groceryItemsViewHolder.mItemAmount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }


    public void insertAt(GroceryItem item, int position){
        groceryItems.add(position, item);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, groceryItems.size());
    }

    public void removeAt(int position){
        groceryItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, groceryItems.size());
    }

}
