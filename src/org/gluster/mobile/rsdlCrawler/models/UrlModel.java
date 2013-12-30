package org.gluster.mobile.rsdlCrawler.models;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ababu on 12/28/13.
 */
public class UrlModel {
    @ElementList(name = "parameters_set", required = false, inline = true)
    private List<RequestParameterModel> parameterList = new ArrayList<RequestParameterModel>();

    public List getParameterList() {
        return parameterList;
    }

    public void setParameterList(List parameterList) {
        this.parameterList = parameterList;
    }
}
