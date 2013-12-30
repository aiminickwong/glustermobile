package org.gluster.mobile.rsdlCrawler.parser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.parsers.XmlDeSerializer;
import org.gluster.mobile.rsdlCrawler.RestApiDescriptionEncoder;
import org.gluster.mobile.rsdlCrawler.models.RestApiRsdlModel;
import org.gluster.mobile.rsdlCrawler.models.RestApiRsdlModels;
import org.gluster.mobile.web.ConnectionUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ababu on 12/29/13.
 */
public class RsdlPreProcessor {

    private RestApiRsdlModels restApiRsdlModels;
    private final static HashMap<String , RestApiRsdlModel> restApiHashList = new HashMap<String, RestApiRsdlModel>();

    public RsdlPreProcessor() {
        String url = "http://" + ConnectionUtil.getInstance().getHost() + ConnectionUtil.getInstance().getPortId()+"/ovirt-engine/api?rsdl";
        preProcess(getResult(getRequest(url)));
    }

    private String getRequest(String urlString) {
        int code;
        ConnectionUtil connUtil = ConnectionUtil.getInstance();
        HttpGet request = new org.apache.http.client.methods.HttpGet(urlString);
        try {
            HttpResponse response = connUtil.get(request);
            code = response.getStatusLine().getStatusCode();
            if(!((code >= 200 ) &&(code <= 399))) {
                return response.getStatusLine().getReasonPhrase();
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return (e == null) ? "Could Not Fetch Data .. " : e.toString();
        }
    }

    private RestApiRsdlModels getResult(String xmlData) {
        return (new XmlDeSerializer<RestApiRsdlModels>(xmlData).deSerialize(RestApiRsdlModels.class));
    }

    public void preProcess(RestApiRsdlModels restApiRsdlModels) {
        List<RestApiRsdlModel> restApiRsdlModelList = restApiRsdlModels.getRestApiRsdlModels();
        for(RestApiRsdlModel restApiRsdlModel : restApiRsdlModelList) {
            if(restApiRsdlModel.getDescription() != null) {
                restApiHashList.put(new RestApiDescriptionEncoder(restApiRsdlModel).encodeDescription() , restApiRsdlModel);
            }
        }
    }
}
