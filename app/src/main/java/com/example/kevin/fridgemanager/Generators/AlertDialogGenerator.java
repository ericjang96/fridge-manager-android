package com.example.kevin.fridgemanager.Generators;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by kevin on Aug 26, 2018
 **/
public class AlertDialogGenerator {

    public AlertDialogGenerator(){
    }

    public AlertDialog generate(String title, String message, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return alertDialog;
    }


}
