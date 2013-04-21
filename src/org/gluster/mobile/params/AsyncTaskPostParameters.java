package org.gluster.mobile.params;

import org.gluster.mobile.gactivity.GlusterActivity;

import android.content.Context;

public class AsyncTaskPostParameters {
	private String url;
	private String requestBody;
	private Context context;
	private int choice;
	private GlusterActivity activity;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public GlusterActivity getActivity() {
		return activity;
	}

	public void setActivity(GlusterActivity activity) {
		this.activity = activity;
	}
}
