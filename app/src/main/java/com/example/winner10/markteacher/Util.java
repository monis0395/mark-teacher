package com.example.winner10.markteacher;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by monis.q on 08-10-2017.
 */

public class Util {

    static void alertDialog(String msg, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }
}


