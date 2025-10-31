package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

public final class RuntimeErrorAlert {
    private static final String TAG = "RuntimeErrorAlert";

    public static void alert(Object context, String message, String title, String buttonText) {
        alert(context, false, message, title, buttonText);
    }

    public static void alert(final Object context, boolean toast, String message, String title, String buttonText) {
        Log.e(TAG, "alert(" + message + ", " + title + ", " + buttonText);
        if (message == null) {
            message = title + " <No error message>";
        }
        if (toast) {
            Toast.makeText((Context) context, message, 1).show();
            RetValManager.sendError(message);
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder((Context) context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(-1, buttonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });
        alertDialog.show();
    }
}
