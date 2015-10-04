package com.example.cesar.clima.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.cesar.clima.R;

/**
 * Created by cesar on 9/26/2015.
 */
public class VentanaAlerta extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.titulo_ventana)
                .setMessage(R.string.mensaje_ventana)
                .setPositiveButton(R.string.boton_ventana, null);
        AlertDialog dialog = builder.create();
        return dialog;

    }
}
