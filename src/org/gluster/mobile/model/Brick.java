package org.gluster.mobile.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "brick")
public class Brick implements GlusterEntity {
	@Attribute(name = "id", required = false)
	private String id;
	@Element(name = "server_id")
	public String server_id;
	@Element(name = "brick_dir")
	public String name;
	@Element(name = "state", required = false)
	public String state;

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
