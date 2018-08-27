/*
Created by Kevin Kwon on August 13 2018
 */
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
import android.widget.TextView;

import com.example.kevin.fridgemanager.Activities.FridgeActivity;
import com.example.kevin.fridgemanager.DomainModels.Ingredient;
import com.example.kevin.fridgemanager.R;
import com.example.kevin.fridgemanager.REST.FridgeRestClient;

import java.util.List;


public class EditIngredientDialogFragment extends DialogFragment {
    private static final String TAG = "EditIngredientDialogFra";

    // Depending on Insert or Remove button is clicked on the CardView
    // TODO: Make one parameter an ingredient object maybe? Pass that and then update/insert/remove as needed
    public static EditIngredientDialogFragment newInstance(String type, Ingredient ingredient) {
        EditIngredientDialogFragment myFragment = new EditIngredientDialogFragment();
        Bundle args = new Bundle();
        args.putString("updateType", type);
        args.putSerializable("ingredient", ingredient);
        myFragment.setArguments(args);
        return myFragment;
    }

    //widgets
    private TextView mTextInputType;
    private EditText mEditAmountText;
    private Button mUpdateButton;
    private FridgeActivity mActivity;


    // TODO: Make a factory to create an insert or remove dialog
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String type = args.getString("updateType");
        final Ingredient ingredient = (Ingredient) args.getSerializable("ingredient");
        mActivity = (FridgeActivity) getActivity();
        mActivity.updateCurrentIngredientPosition(ingredient);

        View view = inflater.inflate(R.layout.dialog_edit_ingredient, container, false);
        mTextInputType = view.findViewById(R.id.insert_or_update_text);
        mEditAmountText = view.findViewById(R.id.edit_amount_text);
        mUpdateButton = view.findViewById(R.id.edit_ingredient_button);
        String displayMessage;


        if(ingredient.getUnit().equals("number")){
            displayMessage = "How many would you like to " + type + "?";
        }
        else{
            displayMessage = "How many " + ingredient.getUnit() + " would you like to " + type + "?";
        }

        mTextInputType.setText(displayMessage);


        switch(type){
            case "insert":
                mUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String amountText = mEditAmountText.getText().toString();
                        if(amountText.isEmpty()){
                            getDialog().dismiss();
                            return;
                        }
                        mActivity.tryUpdateItemAmount(ingredient, amountText);
                        getDialog().dismiss();
                        int amount = Integer.parseInt(amountText);
                        FridgeRestClient.insertIngredientData(new Ingredient(ingredient.getName(), amount, ingredient.getUnit()));
                    }
                });
                break;
            case "remove":
                mUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String amountText = mEditAmountText.getText().toString();
                        if(amountText.isEmpty()){
                            getDialog().dismiss();
                            return;
                        }
                        int realAmount = Integer.parseInt(amountText);
                        int negativeAmount =  realAmount * -1;
                        mActivity.tryUpdateItemAmount(ingredient, String.valueOf(negativeAmount));
                        getDialog().dismiss();
                        FridgeRestClient.removeIngredientAmount(ingredient.getName(), realAmount);
                    }
                });
                break;
            default:
                break;
        }

        return view;
    }
}
