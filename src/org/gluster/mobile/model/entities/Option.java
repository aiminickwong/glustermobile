package org.gluster.mobile.model.entities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "option")
public class Option implements GlusterEntity {
	// @Path("option")
	@Attribute(name = "name")
	private String name;
	// @Path("option")
	@Attribute(name = "value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
