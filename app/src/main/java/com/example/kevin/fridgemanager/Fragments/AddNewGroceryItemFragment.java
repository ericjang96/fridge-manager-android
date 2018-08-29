package com.example.kevin.fridgemanager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevin.fridgemanager.Activities.GroceryListActivity;
import com.example.kevin.fridgemanager.DomainModels.GroceryItem;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.GroceryListRestClient;

/**
 * Created by kevin on Aug 29, 2018
 **/
public class AddNewGroceryItemFragment extends DialogFragment {

    //widgets
    private EditText mItemName, mItemAmount, mItemComment;
    private Button mCancelButton, mAddButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_grocery_item, container, false);
        mItemName = view.findViewById(R.id.grocery_item_name_input);
        mItemAmount = view.findViewById(R.id.grocery_item_amount_input);
        mItemComment = view.findViewById(R.id.grocery_item_comment_input);

        mAddButton = view.findViewById(R.id.send_add_grocery_item_request_button);
        mCancelButton = view.findViewById(R.id.cancel_add_grocery_item_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryListActivity activity = (GroceryListActivity)getActivity();
                String name = mItemName.getText().toString();
                int amount = Integer.parseInt(mItemAmount.getText().toString());
                String comment = mItemComment.getText().toString();

                GroceryItem item = new GroceryItem(name, amount, comment);

                GroceryListRestClient.insertGroceryItem(item);
                getDialog().dismiss();

                assert activity != null;
                activity.addGroceryItem(item);
            }
        });

        return view;
    }
}
