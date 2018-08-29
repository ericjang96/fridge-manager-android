package com.example.kevin.fridgemanager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevin.fridgemanager.Activities.UserGroceryListsActivity;
import com.example.kevin.fridgemanager.DomainModels.GroceryList;
import com.example.kevin.fridgemanager.Generators.UniqueIdGenerator;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.GroceryListRestClient;
import com.example.kevin.fridgemanager.REST.UserRestClient;

/**
 * Created by kevin on Aug 28, 2018
 **/
public class AddNewUserGroceryListDialogFragment extends DialogFragment {
    private static final String TAG = "AddNewUserGroceryListDi";

    //widgets
    private EditText mEditTextGroceryListName;
    private Button mAddButton, mCancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_user_grocery_list, container, false);
        mEditTextGroceryListName = view.findViewById(R.id.user_grocery_list_name_input);

        mAddButton = view.findViewById(R.id.add_user_grocery_list_button);
        mCancelButton = view.findViewById(R.id.cancel_add_user_grocery_list_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "dismissDialog: closing dialog");
                getDialog().dismiss();
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserGroceryListsActivity activity = (UserGroceryListsActivity)getActivity();
                String name = mEditTextGroceryListName.getText().toString();
                String grocery_list_id = UniqueIdGenerator.generateID();

                GroceryList list = new GroceryList(name, grocery_list_id);
                UserRestClient.insertNewGroceryList(list);
                GroceryListRestClient.insertNewGroceryList(list);
                getDialog().dismiss();

                assert activity != null;
                activity.addGroceryList(list);
            }
        });

        return view;
    }
}
