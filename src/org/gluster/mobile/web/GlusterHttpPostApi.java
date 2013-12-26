package org.gluster.mobile.web;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ShowAlert;
import org.gluster.mobile.model.GlusterErrors;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.xml.XmlDeSerializer;

/**
 * Created by Anmol babu on 12/26/13.
 */
public class GlusterHttpPostApi<G> extends AsyncTask<AsyncTaskParameter, Void, String>{
    private Context context;
    private Class intentActivity;
    private String resultString;
    private Class<G> errorClass;
    private GlusterActivity activity;

    @Override
    protected String doInBackground(AsyncTaskParameter... asyncTaskParameters) {
        errorClass = asyncTaskParameters[0].getClassName();
        context = asyncTaskParameters[0].getContext();
        activity = asyncTaskParameters[0].getActivity();
        intentActivity = asyncTaskParameters[0].getIntentActivity();
        resultString = request(asyncTaskParameters[0].getUrl(), asyncTaskParameters[0].getRequestBody());
        return null;
    }

    private String request(String url, String requestBody) {
        ConnectionUtil connUtil = ConnectionUtil.getInstance();
        HttpPost request = new HttpPost(url);
        HttpResponse response = connUtil.post(requestBody, request);
        HttpEntity entity = response.getEntity();
        try {
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        getObjectFromXml(resultString);
        activity.after_post("Done");
    }

    private void getObjectFromXml(String result) {
        if(result.contains("fault")) {
            new ShowAlert(((GlusterErrors) errorClass.cast(new XmlDeSerializer<G>(result).deSerialize(errorClass))).getDetail(), context, intentActivity).showAlert();
        } else if(result.contains("Exception")) {
            new ShowAlert(result, context, intentActivity).showAlert();
        } else {
            new ShowAlert("Request Succeded", context, intentActivity).showAlert();
        }
    }
}
