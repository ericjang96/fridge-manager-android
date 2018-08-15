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


    // TODO: Make a factory to create an insert or remove dialog
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        String type = args.getString("updateType");
        assert type != null;
        final Ingredient ingredient = (Ingredient) args.getSerializable("ingredient");
        assert ingredient != null;

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
                        ((FridgeActivity)getActivity()).tryUpdateItemAmount(ingredient.getName(), String.valueOf(mEditAmountText.getText()));
                        getDialog().dismiss();
                    }
                });
                break;
            case "remove":
                mUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            default:
                break;
        }

        return view;
    }
}
