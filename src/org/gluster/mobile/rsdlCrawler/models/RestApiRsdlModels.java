package org.gluster.mobile.rsdlCrawler.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ababu on 12/29/13.
 */
@Root(name = "rsdl")
public class RestApiRsdlModels {
    @ElementList(name = "links", required = false, inline = true)
    List<RestApiRsdlModel> restApiRsdlModels = new ArrayList<RestApiRsdlModel>();

    public List<RestApiRsdlModel> getRestApiRsdlModels() {
        return restApiRsdlModels;
    }

    public void setRestApiRsdlModels(List<RestApiRsdlModel> restApiRsdlModels) {
        this.restApiRsdlModels = restApiRsdlModels;
    }
}
