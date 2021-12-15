package com.example.fantasticshop;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {
    // We create a listener variable here (https://www.youtube.com/watch?v=r_87U6oHLFc)
        private DialogBoxListener dialogBoxListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ATTENTION!");
        builder.setMessage("Are you sure you want to definitely delete this item ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialogBoxListener.onYesBtnClick();
                dismiss();
            }
        });

        return builder.create();

    }

    public interface DialogBoxListener{
        void onYesBtnClick();

    }

    // in order to set the listener to the ItemDetailsActivity,we overrride the onAttach method
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //we set the listener to the context

        try {
            dialogBoxListener = (DialogBoxListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + "Must implements the DialogoBoxListener");
        }

    }
}
