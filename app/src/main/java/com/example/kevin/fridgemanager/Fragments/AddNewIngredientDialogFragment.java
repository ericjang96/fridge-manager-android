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

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import java.util.Calendar;
import java.util.Date;

public class AddNewIngredientDialogFragment extends DialogFragment {
    private static final String TAG = "AddIngredientDialogFrag";

    //widgets
    private EditText mEditIngredientName, mEditUnit, mEditAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_ingredient, container, false);
        mEditIngredientName = view.findViewById(R.id.edit_ingredient_name);
        mEditUnit = view.findViewById(R.id.edit_ingredient_amountUnit);
        mEditAmount = view.findViewById(R.id.edit_ingredient_amount);

        Button mSendRequestButton = view.findViewById(R.id.send_ingredient_request_button);
        Button mCancelButton = view.findViewById(R.id.cancel_add_ingredient_prompt_button);

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
                    FridgeActivity activity = (FridgeActivity)getActivity();
                    String name = mEditIngredientName.getText().toString();
                    String amountUnit = mEditUnit.getText().toString();
                    String amount = mEditAmount.getText().toString();

                    Date currentDate = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(currentDate);

                    // expiry date is 2 weeks later (for now)
                    c.add(Calendar.DATE, 14);
                    Date expiryDate = c.getTime();

                    Integer amountInt = Integer.parseInt(amount);

                    Ingredient ingredient = new Ingredient(name, currentDate, expiryDate, amountUnit, amountInt);
                    FridgeRestClient.insertIngredientData(ingredient);
                    getDialog().dismiss();

                    // If ingredient does not exist
                    if(!activity.tryUpdateItemAmount(name, amount)){
                        activity.addIngredient(ingredient);
                    }
                }
                catch(Exception e){
                    Log.e(TAG, "sendIngredientRequest: " + e.getMessage());
                }
            }
        });

        return view;
    }

}
