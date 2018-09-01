package com.example.kevin.fridgemanager.Adapters;
/**
 * Created by kevin on Aug 27, 2018
 **/


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
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.UserRestClient;

import java.util.List;

public class GroceryListsViewAdapter extends RecyclerViewAdapter  {
    public GroceryListsViewAdapter(Context context) {
        super(context);
    }

    public class GroceryListsViewHolder extends GenericViewHolder {

        //widgets
        TextView mGroceryListName;
        Button mDeleteButton;
        CardView mCardView;
        Context context = getContext();

        GroceryListsViewHolder(View view) {
            super(view);
            initialize();

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = mGroceryListName.getText().toString();
                    view.setClickable(false);
                    Intent intent = new Intent(context, GroceryListActivity.class);
                    intent.putExtra("grocery_list_id", getIdByName(name));
                    intent.putExtra("name", mGroceryListName.getText().toString());
                    context.startActivity(intent);
                    view.setClickable(true);
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

        protected void initialize(){
            mCardView = (CardView) initViewAndGetView(R.id.cardview_grocery_list);
            mGroceryListName = (TextView) initViewAndGetView(R.id.grocery_list_name);
            mDeleteButton = (Button) initViewAndGetView(R.id.grocery_list_delete_button);
        }
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_grocery_list, viewGroup, false);
        return view;
    }

    @Override
    protected void bindView(Object item, GenericViewHolder viewHolder) {
        GroceryList list = (GroceryList) item;
        if (item != null) {
            viewHolder.getTextView(R.id.grocery_list_name).setText(list.getName());
        }
    }

    @Override
    protected void bindViewPayloads(Object item, GenericViewHolder viewHolder, @NonNull List payloads) {
        bindView(item, viewHolder);
    }

    @NonNull
    @Override
    public GroceryListsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_grocery_list, viewGroup, false);
        return new GroceryListsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }


    private String getIdByName(String name) {
        List<GroceryList> lists = getList();
        for(int i = 0; i < lists.size(); i++){
            if(lists.get(i).getName().equals(name))
                return lists.get(i).getGroceryListId();
        }
        return "name_not_found";
    }
}
