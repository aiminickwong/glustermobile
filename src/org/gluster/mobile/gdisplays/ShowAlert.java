package org.gluster.mobile.gdisplays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.gluster.mobile.gactivity.GlusterActivity;

/**
 * Created by Anmol Babu on 12/26/13.
 */
public class ShowAlert {
    private String message;
    private Context context;
    private Class intentActivity;

    public ShowAlert(String message, Context context, Class intentActivity) {
        this.message = message;
        this.context = context;
        this.intentActivity = intentActivity;
    }

    public void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
            .setTitle("Post Request Status")
            .setMessage(message);
        if(intentActivity == null) {
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        } else {
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.startActivity(new Intent(context, intentActivity).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
