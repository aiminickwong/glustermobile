package org.gluster.mobile.model.entities;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "cluster", strict = false)
public class Cluster implements GlusterEntity {
	@Attribute(name = "id", required = false)
	private String id;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "data_center", required = false)
	private DataCenter data_center;
	@Element(name = "virt_service", required = false)
	private Boolean virt_service;

	@Element(name = "gluster_service", required = false)
	private Boolean gluster_service;

	public Boolean getVirt_service() {
		return virt_service;
	}

	public void setVirt_service(Boolean virt_service) {
		this.virt_service = virt_service;
	}

	public Boolean getGluster_service() {
		return gluster_service;
	}

	public void setGluster_service(Boolean gluster_service) {
		this.gluster_service = gluster_service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DataCenter getData_center() {
		return data_center;
	}

	public void setData_center(DataCenter data_center) {
		this.data_center = data_center;
	}

}
