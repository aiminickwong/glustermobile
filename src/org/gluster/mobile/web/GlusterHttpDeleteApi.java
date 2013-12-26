package org.gluster.mobile.web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.params.AsyncTaskParameter;

import android.content.Context;
import android.os.AsyncTask;

public class GlusterHttpDeleteApi extends AsyncTask<AsyncTaskParameter, Void, String> {
	private String url;
	private String requestBody;
	private Context context;
	private int choice;
	private GlusterActivity activity;

	@Override
	protected String doInBackground(AsyncTaskParameter... params) {
		url = params[0].getUrl();
		requestBody = params[0].getRequestBody();
		context = params[0].getContext();
		activity = params[0].getActivity();
		return request(url);
	}

	private String request(String url) {
		ConnectionUtil connUtil = ConnectionUtil.getInstance();
		HttpDelete request = new HttpDelete(url);
		HttpResponse response = connUtil.delete(requestBody, request);
		HttpEntity entity = response.getEntity();
		try {
			String outcome = EntityUtils.toString(entity);
			System.out.println("Outcome of Delete request : " + outcome);
			return outcome;
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		activity.after_delete(result);
	}

}
