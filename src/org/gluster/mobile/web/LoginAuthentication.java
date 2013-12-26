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
        ParametersToHttpPageGetter detailsFromUI = params[0];
        final String urlString = detailsFromUI.getUrl(detailsFromUI);
        final String userName = detailsFromUI.getUserName(detailsFromUI);
        final String password = detailsFromUI.getPassword(detailsFromUI);
        String host = detailsFromUI.getHost(detailsFromUI);
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
            result.getEntity();
        } catch (Exception e) {
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
        if (retVal.equals("pass")) {
            activity.afterLoginPageChange(-1);
        } else {
            ConnectionUtil.getInstance().clear();
            activity.setErrorMessage();
            return;
        }
        super.onPostExecute(result);
    }
}
