package org.gluster.mobile.model.entities;

import org.simpleframework.xml.Element;

public class DataCenter {
	@Element(name = "name", required = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
