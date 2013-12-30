package org.gluster.mobile.rsdlCrawler.parser;

import org.gluster.mobile.rsdlCrawler.models.RestApiRsdlModel;

/**
 * Created by ababu on 12/28/13.
 */
public class PrepareRequest {
    RestApiRsdlModel restApiModel;

    public PrepareRequest(RestApiRsdlModel restApiModel) {
        this.restApiModel = restApiModel;
    }

    public String getRequestParameters() {
        String urlPattern = restApiModel.getLink();
        return urlPattern;
    }
}
