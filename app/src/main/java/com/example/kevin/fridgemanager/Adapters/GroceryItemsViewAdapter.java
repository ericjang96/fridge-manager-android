package com.example.kevin.fridgemanager.Adapters;

/**
 * Created by kevin on Aug 29, 2018
 **/

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.GroceryListActivity;
import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.GroceryListRestClient;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class GroceryItemsViewAdapter extends RecyclerViewAdapter  {

    public GroceryItemsViewAdapter(Context context){
        super(context);
    }

    protected class GroceryItemsViewHolder extends GenericViewHolder implements ExpandableLayout.OnExpansionUpdateListener{
        // widgets
        private CardView mCardView;
        private TextView mItemName, mItemComment, mItemAmount;
        private Button mDeleteButton;
        private ExpandableLayout mExpandableLayout;
        private ImageView mExpandIcon;

        //vars
        Context context = getContext();

        public GroceryItemsViewHolder(@NonNull View groceryItemView) {
            super(groceryItemView);
            initialize();

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

        public void initialize(){

            mExpandableLayout = (ExpandableLayout) initViewAndGetView(R.id.expandable_layout_grocery_item);
            mCardView = (CardView) initViewAndGetView(R.id.cardview_grocery_item);
            mItemName = (TextView) initViewAndGetView(R.id.grocery_item_name);
            mItemComment = (TextView) initViewAndGetView(R.id.grocery_item_comment);
            mItemAmount = (TextView) initViewAndGetView(R.id.grocery_item_amount);
            mDeleteButton = (Button) initViewAndGetView(R.id.delete_grocery_item_button);
            mExpandIcon = (ImageView) initViewAndGetView(R.id.grocery_item_expand_icon);

            mExpandableLayout.setInterpolator(new AccelerateInterpolator());
            mExpandableLayout.setOnExpansionUpdateListener(this);
        }
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_grocery_item, viewGroup, false);
        return view;
    }

    @Override
    protected void bindView(Object item, GenericViewHolder viewHolder) {
        GroceryItem groceryItem = (GroceryItem) item;
        if(item != null){
            String trimmedComment = groceryItem.getComment().trim();
            if(trimmedComment.equals("")){
                viewHolder.getView(R.id.grocery_item_expand_icon).setVisibility(View.GONE);
            }

            viewHolder.getTextView(R.id.grocery_item_name).setText(groceryItem.getName());
            viewHolder.getTextView(R.id.grocery_item_amount).setText(String.valueOf(groceryItem.getAmount()));
            viewHolder.getTextView(R.id.grocery_item_comment).setText(groceryItem.getComment());
        }
    }

    @Override
    protected void bindViewPayloads(Object item, GenericViewHolder viewHolder, @NonNull List payloads) {
        bindView(item, viewHolder);
    }

    @NonNull
    @Override
    public GroceryItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_grocery_item, viewGroup, false);
        return new GroceryItemsViewHolder(v);
    }
}
