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

import com.example.kevin.fridgemanager.R;

public class EditIngredientDialogFragment extends DialogFragment {
    private static final String TAG = "EditIngredientDialogFra";

    // Depending on Insert or Remove button is clicked on the CardView
    public static EditIngredientDialogFragment newInstance(String type) {
        EditIngredientDialogFragment myFragment = new EditIngredientDialogFragment();
        Bundle args = new Bundle();
        args.putString("updateType", type);
        myFragment.setArguments(args);
        return myFragment;
    }

    //widgets
    private TextView mTextInputType;
    private EditText mEditAmountText;
    private Button mUpdateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        String type = args.getString("updateType");
        assert type!=null;

        View view = inflater.inflate(R.layout.dialog_edit_ingredient, container, false);
        mTextInputType = view.findViewById(R.id.insert_or_update_text);
        mEditAmountText = view.findViewById(R.id.edit_amount_text);
        mUpdateButton = view.findViewById(R.id.edit_ingredient_button);


        // TODO: change text to "How many <units> would you like to insert?"
        if(type.equals("insert")){
            mTextInputType.setText("How much would you like to insert?");
        }
        if(type.equals("remove")){
            mTextInputType.setText("How much would you like to remove?");
        }



        return view;
    }
}
