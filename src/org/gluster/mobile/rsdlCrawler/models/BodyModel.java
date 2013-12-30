package org.gluster.mobile.rsdlCrawler.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Created by ababu on 12/28/13.
 */
public class BodyModel {
    @Element(name = "type", required = false)
    private String type;

    @ElementList(name = "parameters_set", required = false, inline = true)
    private RequestParameterModel parameterList;

    public RequestParameterModel getParameterList() {
        return parameterList;
    }

    public void setParameterList(RequestParameterModel parameterList) {
        this.parameterList = parameterList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
