package org.gluster.mobile.web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.activities.ClusterDisplayActivity;
import org.gluster.mobile.activities.VolumeDisplayActivity;
import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.AddError;
import org.gluster.mobile.model.StartStopError;
import org.gluster.mobile.params.AsyncTaskPostParameters;
import org.gluster.mobile.xml.EntitiesDeSerializer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class HttpPostRequests extends
		AsyncTask<AsyncTaskPostParameters, Void, String> {
	private String url;
	private String requestBody;
	private Context context;
	private int choice;
	private GlusterActivity activity;

	@Override
	protected String doInBackground(AsyncTaskPostParameters... params) {
		// TODO Auto-generated method stub
		url = params[0].getUrl();
		System.out.println("In post request : " + url);
		requestBody = params[0].getRequestBody();
		context = params[0].getContext();
		choice = params[0].getChoice();
		activity = params[0].getActivity();
		return request(url);
	}

	private String request(String url) {
		ConnectionUtil connUtil = ConnectionUtil.getInstance();
		HttpPost request = new HttpPost(url);
		HttpResponse response = connUtil.post(requestBody, request);
		HttpEntity entity = response.getEntity();
		try {
			String outcome = EntityUtils.toString(entity);
			System.out.println("Outcome of post request : " + outcome);
			return outcome;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.toString();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		switch (choice) {
		case 1: {
			String error = "";
			if (result.contains("fault")) {
				AddError ae = new EntitiesDeSerializer<AddError>(result)
						.getResults(AddError.class);
				error = ae.getDetail();
				System.out.println(error + "Httppost");
				new SetAlertBox(error, context, 1, null).showDialog();
			} else {
				context.startActivity(new Intent(context,
						ClusterDisplayActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}

			break;
		}
		case 2: {
			String error = "";
			if (result.contains("fault")) {
				StartStopError se = new EntitiesDeSerializer<StartStopError>(
						result).getResults(StartStopError.class);
				error = se.getDetail();
				new SetAlertBox(error, context, 2, null).showDialog();
			} else {
				activity.after_post("Done");
			}

			break;

		}
		/*
		 * case 2: { if (result.contains("fault")) { ArrayList<AddError> a = new
		 * ArrayList<AddError>( (ArrayList<AddError>) new
		 * SerializerClass(result) .getResults(9));
		 * activity.after_post(a.get(0).getDetail()); } else { // toDisplay =
		 * "Request Executed Successfully"; context.startActivity(new
		 * Intent(context, ClusterDisplayActivity.class)
		 * .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); break; } }
		 */
		case 3: {
			activity.after_post(result);
		}

		}
	}
}
