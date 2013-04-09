package org.gluster.mobile.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Host implements GlusterEntity {
	@Attribute(name = "id", required = true)
	private String id;

	@Element(name = "name", required = true)
	private String name;
	@Element(name = "address", required = true)
	private String address;
	@Element(name = "cluster", required = false)
	Cluster c = new Cluster();
	@Element(name = "port", required = true)
	private String port;
	@Element(name = "type", required = true)
	private String type;
	@Element(name = "memory", required = true)
	private long memory;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getMemory() {
		return memory;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public String getState() { return state; }
	 * 
	 * public void setState(String state) { this.state = state; }
	 */
	public String getId() {
		return this.id;
	}

	public void setClusterId(String clusterId) {
		this.setId(clusterId);
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public Cluster getC() {
		return c;
	}

	public void setC(Cluster c) {
		this.c = c;
	}
}
