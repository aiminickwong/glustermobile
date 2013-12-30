package org.gluster.mobile.rsdlCrawler.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by ababu on 12/28/13.
 */
@Root(name = "request", strict = false)
public class RequestModel {
    @Element(name = "http_method", required = false)
    private String http_method;

    @Element(name = "url", required = false)
    private UrlModel url;

    @ElementList(name = "body", required = false)
    private BodyModel body;

    public String getHttp_method() {
        return http_method;
    }

    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }

    public UrlModel getUrl() {
        return url;
    }

    public void setUrl(UrlModel url) {
        this.url = url;
    }

    public BodyModel getBody() {
        return body;
    }

    public void setBody(BodyModel body) {
        this.body = body;
    }
}
