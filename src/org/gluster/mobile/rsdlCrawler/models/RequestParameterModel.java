package org.gluster.mobile.rsdlCrawler.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ababu on 12/28/13.
 */
@Root(name = "parameter")
public class RequestParameterModel {
    @Attribute(name = "required", required = false)
    String requirement;

    @Attribute(name = "type", required = false)
    String type;

    @Element(name = "name", required = false)
    String parameterName;

    @Element(name = "value", required = false)
    String parameterValue;

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
