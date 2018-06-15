package com.example.kevin.moleseeker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class DialogFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.game_over_dialog,null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intentBack = new Intent(getContext(),MainMenuActivity.class);
                        startActivity(intentBack);
                }
            }
        };

        setCancelable(false);
        return new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                .setView(v)
                .setTitle("Game Over")
                .setMessage("Congratulations! You have found all the moles!")
                .setPositiveButton("Return to Main Menu",listener)
                .create();
    }
}
