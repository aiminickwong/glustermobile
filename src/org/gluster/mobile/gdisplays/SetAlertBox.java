package org.gluster.mobile.gdisplays;

import org.gluster.mobile.activities.ClusterDisplayActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class SetAlertBox {
	private String message;
	private Context context;
	private int choice;

	public SetAlertBox(String message, Context context, int choice) {
		super();
		this.message = message;
		this.context = context;
		this.choice = choice;
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
									
									/*
									context.startActivity(new Intent(context,
											ClusterDisplayActivity.class)
											.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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

		}
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}
}
