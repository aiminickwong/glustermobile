package org.gluster.mobile.params;

import org.gluster.mobile.activities.SettingsActivity;
import org.gluster.mobile.gactivity.GlusterActivity;

import android.content.Intent;

public class SettingsHandler {
	private GlusterActivity activity;

	public SettingsHandler(GlusterActivity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public void handle() {
		// TODO Auto-generated method stub
		Intent settingsPage = new Intent(activity, SettingsActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(settingsPage);
		activity.finishActivity(0);
	}

}
