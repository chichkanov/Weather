package dvinc.yamblzhomeproject.fragments;
/*
 * Created by DV on Space 5 
 * 13.07.2017
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import dvinc.yamblzhomeproject.R;

public class AboutFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.about_dialog_head);
        builder.setMessage(R.string.about_dialog_decs);
        //AlertDialog alertDialog = builder.create();
        return builder.create();
    }
}
