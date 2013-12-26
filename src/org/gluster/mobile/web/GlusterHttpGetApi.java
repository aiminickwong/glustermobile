package org.gluster.mobile.web;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ShowAlert;
import org.gluster.mobile.model.GlusterEntities;
import org.gluster.mobile.model.GlusterEntity;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.xml.XmlDeSerializer;
import org.apache.http.client.methods.HttpGet;

import java.util.List;

/**
 * Created by Anmol babu on 12/26/13.
 */
public class GlusterHttpGetApi<G> extends AsyncTask<AsyncTaskParameter<G>, String[], String> {

    private String url;
    private Class<G> className;
    private String message = "";
    private Context context;
    private GlusterActivity activity;
    private String resultString;
    private Class intentClass;

    @Override
    protected String doInBackground(AsyncTaskParameter<G>... asyncTaskParameters) {
        url = asyncTaskParameters[0].getUrl();
        className = asyncTaskParameters[0].getClassName();
        context = asyncTaskParameters[0].getContext();
        activity = asyncTaskParameters[0].getActivity();
        intentClass = asyncTaskParameters[0].getIntentActivity();
        resultString = getRequest(url);
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(resultString.contains("Exception") || resultString.contains("Error")) {
            new ShowAlert(resultString, context,intentClass).showAlert();
        } else {
            G resultObject = getResult(resultString);
            if (resultObject instanceof GlusterEntities) {
                List<? extends GlusterEntity> entities = ((GlusterEntities)((className.cast(resultObject)))).getObjects();
                activity.after_get(((GlusterEntities)(className.cast(resultObject))).getObjects());
            } else {
                activity.after_getObject(((GlusterEntity)className.cast(resultObject)));
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public GlusterActivity getActivity() {
        return activity;
    }

    private String getRequest(String urlString) {
        int code;
        ConnectionUtil connUtil = ConnectionUtil.getInstance();
        HttpGet request = new org.apache.http.client.methods.HttpGet(urlString);
        try {
            HttpResponse response = connUtil.get(request);
            code = response.getStatusLine().getStatusCode();
            if(!((code >= 200 ) &&(code <= 399))) {
                return "Error : Request to Server Failed";
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return (e == null) ? "Could Not Fetch Data .. " : e.toString();
        }

    }

    private G getResult(String xmlData) {
        return (new XmlDeSerializer<G>(xmlData).deSerialize(className));
    }
}
