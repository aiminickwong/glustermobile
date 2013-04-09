package org.gluster.mobile.model;

import org.simpleframework.xml.Element;

public class Access_Protocol {
@Element(name = "access_protocol")
	private String access_protocol;

public String getAccess_protocol() {
	return access_protocol;
}

public void setAccess_protocol(String access_protocol) {
	this.access_protocol = access_protocol;
}
}
