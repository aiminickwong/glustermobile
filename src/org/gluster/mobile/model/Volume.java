package org.gluster.mobile.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "gluster_volume", strict = false)
public class Volume implements GlusterEntity {
	@Attribute(name = "id", required = false)
	private String id;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "volume_type", required = false)
	private String volume_type;
	@Element(name = "options", required = false)
	private Options o;
	@Path("status")
	@Element(name = "state", required = false)
	private String state;

	public Options getO() {
		return o;
	}

	public void setO(Options o) {
		this.o = o;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVolume_type() {
		return volume_type;
	}

	public void setVolume_type(String volume_type) {
		this.volume_type = volume_type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}