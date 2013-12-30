package org.gluster.mobile.params;

import android.app.Activity;
import android.content.Context;

import org.gluster.mobile.gactivity.GlusterActivity;


/**
 * Created by ababu on 12/26/13.
 */
public class AsyncTaskParameter<G> {
    private  static AsyncTaskParameter asyncTaskParameter;
    private String url;
    private  Class className;
    private Context context;
    private Class intentActivity;
    private String requestBody = "";
    private GlusterActivity activity;
    private int urlEntityMapKey;

    public static AsyncTaskParameter getAsyncTaskParameter() {
        return asyncTaskParameter;
    }

    public static void setAsyncTaskParameter(Context context, String url, Class className, GlusterActivity currentActivity) {
        asyncTaskParameter = new AsyncTaskParameter(context, url, className.getClass());
        asyncTaskParameter.setActivity(currentActivity);
    }

    public AsyncTaskParameter(Context context, String url, Class className) {
        this.context = context;
        this.url = url;
        this.className = className;
    }

    public int getUrlEntityMapKey() {
        return urlEntityMapKey;
    }

    public void setUrlEntityMapKey(int urlEntityMapKey) {
        this.urlEntityMapKey = urlEntityMapKey;
    }

    public Class getIntentActivity() {
        return intentActivity;
    }

    public void setIntentActivity(Class intentActivity) {
        this.intentActivity = intentActivity;
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

    public GlusterActivity getActivity() {
        return activity;
    }

    public void setActivity(GlusterActivity activity) {
        this.activity = activity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;

    }
}
