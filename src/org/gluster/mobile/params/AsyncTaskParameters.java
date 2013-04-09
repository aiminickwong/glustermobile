package org.gluster.mobile.params;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.model.GlusterEntity;

import android.content.Context;

public class AsyncTaskParameters<G> {
	private Class<G> classNames;
	private String url;
	private int choice;
	private Class<? extends GlusterEntity> className;

	public Class<? extends GlusterEntity> getClassName() {
		return className;
	}

	public void setClassName(Class<? extends GlusterEntity> className) {
		this.className = className;
	}

	private String host;
	private GlusterActivity activity;
	private Context context;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public GlusterActivity getActivity() {
		return activity;
	}

	public void setActivity(GlusterActivity activity) {
		this.activity = activity;
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

	public Class<G> getClassNames() {
		return classNames;
	}

	public void setClassNames(Class<G> classNames) {
		this.classNames = classNames;
	}

}
