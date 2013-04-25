package org.gluster.mobile.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.activities.LoginMainActivity;
import org.gluster.mobile.params.ParametersToHttpPageGetter;

import android.content.Context;
import android.os.AsyncTask;

public class LoginAuthentication extends
		AsyncTask<ParametersToHttpPageGetter, Void, Void> {
	String retVal = null;
	LoginMainActivity activity;
	Context context;

	public LoginAuthentication(LoginMainActivity activity) {
		this.activity = activity;
	}

	private void authenticate(ParametersToHttpPageGetter... params) {
		// TODO Auto-generated method stub
		ParametersToHttpPageGetter detailsFromUI = params[0];
		final String urlString = detailsFromUI.getUrl(detailsFromUI);
		System.out.println("url is \t" + urlString);
		final String userName = detailsFromUI.getUserName(detailsFromUI);
		System.out.println("userName is" + userName);
		final String password = detailsFromUI.getPassword(detailsFromUI);
		System.out.println("password is:\t" + password);
		String host = detailsFromUI.getHost(detailsFromUI);
		// int choice = detailsFromUI.getChoice(detailsFromUI);
		String hostCred = host;
		ConnectionUtil connUtil = ConnectionUtil.getInstance();
		connUtil.clear();
		String[] hostID = hostCred.split(":");
		if (hostID[1].equals("") || hostID[1].equals(null)) {
			hostID[1] = "80";
		}
		connUtil.initClient(hostID[0], userName, password,
				Integer.parseInt(hostID[1]));

		HttpGet request = new HttpGet(urlString);
		HttpResponse result;
		try {
			result = connUtil.get(request);
			System.out.println(EntityUtils.toString(result.getEntity()));
		} catch (Exception e) {
			// TODO: handle exception
			retVal = "fail";
			return;
		}
		int code = result.getStatusLine().getStatusCode();
		if (!((code >= 200) && (code <= 399))) {
			retVal = "fail";
			connUtil.clear();
			return;
		} else {
			retVal = "pass";
		}
	}

	@Override
	protected Void doInBackground(ParametersToHttpPageGetter... params) {
		authenticate(params);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		if (retVal.equals("pass")) {
			System.out.println("Iam cool!!!!!!!!!!!");
			activity.afterLoginPageChange(-1);
		} else {
			ConnectionUtil.getInstance().clear();
			activity.setErrorMessage();
			System.out.println("Bang emulator!!!");
			return;
		}
		super.onPostExecute(result);
	}
}
