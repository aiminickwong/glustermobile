package org.gluster.mobile.rsdlCrawler.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**

 * Created by ababu on 12/28/13.
 */
@Root(name = "link" , strict = false)
public class RestApiRsdlModel {
    @Attribute(name = "href", required = false)
    private String link;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "request", required = false)
    private RequestModel request;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestModel getRequest() {
        return request;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }
}
