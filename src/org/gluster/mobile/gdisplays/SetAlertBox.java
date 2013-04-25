package org.gluster.mobile.gdisplays;

import org.gluster.mobile.activities.ClusterDisplayActivity;
import org.gluster.mobile.gactivity.GlusterActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class SetAlertBox {
	private String message;
	private Context context;
	private int choice;
	private GlusterActivity activity;

	public SetAlertBox(String message, Context context, int choice,
			GlusterActivity activity) {
		super();
		this.message = message;
		this.context = context;
		this.choice = choice;
		this.activity = activity;
	}

	public void showDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		switch (choice) {
		case 1: {
			alertDialogBuilder
					.setTitle("Post Request Status")
					.setMessage(message)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
									activity.finish();
									/*
									 * context.startActivity(new Intent(context,
									 * ClusterDisplayActivity.class)
									 * .addFlags(Intent
									 * .FLAG_ACTIVITY_NEW_TASK));
									 */
								}
							});
			break;
		}
		case 2: {
			alertDialogBuilder
					.setTitle("Post Request Status")
					.setMessage(message)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
			break;

		}
		case 3: {
			alertDialogBuilder
					.setTitle("Post Request Status")
					.setMessage(message)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									context.startActivity(new Intent(context,
											ClusterDisplayActivity.class)
											.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

								}
							});
			break;
		}
		}
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}
}
