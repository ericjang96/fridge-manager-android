package com.example.kevin.fridgemanager.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

public class AddIngredientDialogFragment extends DialogFragment {
    private static final String TAG = "AddIngredientDialogFrag";

    //widgets
    private EditText mEditIngredientName, mEditBoughtDate, mEditExpiryDate, mEditUnit, mEditAmount;
    private Button mSendRequestButton, mCancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_ingredient_prompt, container, false);
        mEditIngredientName = view.findViewById(R.id.edit_ingredient_name);
        mEditBoughtDate = view.findViewById(R.id.edit_ingredient_boughtDate);
        mEditExpiryDate = view.findViewById(R.id.edit_ingredient_expiryDate);
        mEditUnit = view.findViewById(R.id.edit_ingredient_amountUnit);
        mEditAmount = view.findViewById(R.id.edit_ingredient_amount);

        mSendRequestButton = view.findViewById(R.id.send_ingredient_request_button);
        mCancelButton = view.findViewById(R.id.cancel_add_ingredient_prompt_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "dismissDialog: closing dialog");
                getDialog().dismiss();
            }
        });

        mSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String name = mEditIngredientName.getText().toString();
                    String boughtDate = mEditBoughtDate.getText().toString();
                    String expiryDate = mEditExpiryDate.getText().toString();
                    String amountUnit = mEditUnit.getText().toString();
                    String amount = mEditAmount.getText().toString();

                    Integer amountInt = Integer.parseInt(amount);

                    Ingredient ingredient = new Ingredient(name, boughtDate, expiryDate, amountUnit, amountInt);
                    FridgeRestClient.insertIngredientData(ingredient);
                    getDialog().dismiss();

                    LayoutInflater li = LayoutInflater.from(view.getContext());
                    refreshWithoutLoading(li.inflate(R.layout.activity_fridge, null));
                }
                catch(Exception e){
                    Log.e(TAG, "sendIngredientRequest: " + e.getMessage());
                }
            }
        });

        return view;
    }

    public void refreshWithoutLoading(View view){
        RecyclerView rv = view.findViewById(R.id.rv);
        View loading = view.findViewById(R.id.fridgeLoadingPanel);
        FridgeRestClient.getFridgeData(rv, loading);
    }

}