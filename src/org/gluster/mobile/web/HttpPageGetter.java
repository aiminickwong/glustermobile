package org.gluster.mobile.web;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.Bricks;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.GlusterEntities;
import org.gluster.mobile.model.GlusterEntity;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.xml.EntitiesDeSerializer;

import android.os.AsyncTask;

public class HttpPageGetter<G extends GlusterEntities, E extends GlusterEntity>
		extends AsyncTask<AsyncTaskParameters<G>, String[], Void> {
	private String url;
	private String xmlData = null;
	private GlusterActivity<E> activity;
	private Class<G> classNames;
	private Class<? extends GlusterEntity> className;
	private G object;
	private int choice;
	private E tObj;
    private String error;

	@Override
	protected Void doInBackground(AsyncTaskParameters<G>... arg0) {
		url = arg0[0].getUrl();
        error = "";
		activity = arg0[0].getActivity();
		classNames = arg0[0].getClassNames();
		className = arg0[0].getClassName();
		choice = arg0[0].getChoice();
		getPage(url);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (choice != 1) {
			getResultObjects();
            if (!error.equals("")) {
                new SetAlertBox(error,activity,2,activity).showDialog();
            }
            else {
			    activity.after_get((List<E>) object.getObjects());
            }
		} else {
			getResultObject();
			activity.after_getObject(tObj);
		}
	}

	private void getResultObject() {
		EntitiesDeSerializer<G> get = null;
		try {
			get = new EntitiesDeSerializer<G>(xmlData);
		} catch (Exception error) {
            error.printStackTrace();
        }
		try {
			tObj = get.<E> getResult(className);
		} catch (Exception e) {
          e.printStackTrace();
        }

	}

	private void getPage(String urlString) {
        HttpResponse response;
		ConnectionUtil connUtil = ConnectionUtil.getInstance();
		HttpGet request = new HttpGet(urlString);
		try {
            response = connUtil.get(request);
            HttpEntity entity = response.getEntity();
			xmlData = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
            if(e!=null) {
                error = e.toString();
            } else {
                error = " Could not fetch Data ...";
            }
		}

	}

	private void getResultObjects() {
		EntitiesDeSerializer<G> get = null;
		try {
			get = new EntitiesDeSerializer<G>(xmlData);
		} catch (Exception error) {
			error.printStackTrace();
		}
		try {
			object = get.getResults(classNames);
		} catch (Exception e) {
            e.printStackTrace();
        }

	}
}