package org.gluster.mobile.model;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "gluster_volume")
public class VolumeCreate implements GlusterEntity {
	@Element(name = "name")
	private String name;
	@Element(name = "replica_count")
	private int replica_count;
	@Element(name = "stripe_count")
	private int stripe_count;
	@Element(name = "volume_type")
	private String volume_type;
	@Path("bricks")
	@ElementList(name = "bricks", required = true, inline = true)
	ArrayList<Brick> bricks = new ArrayList<Brick>();
	@ElementList(name = "access_protocols", required = false, inline = true)
	ArrayList<Access_Protocol> access_protocols = new ArrayList<Access_Protocol>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVolume_type() {
		return volume_type;
	}

	public void setVolume_type(String volume_type) {
		this.volume_type = volume_type;
	}

	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(ArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	public int getReplica_count() {
		return replica_count;
	}

	public void setReplica_count(int replica_count) {
		this.replica_count = replica_count;
	}

	public ArrayList<Access_Protocol> getAccess_protocols() {
		return access_protocols;
	}

	public void setAccess_protocols(ArrayList<Access_Protocol> access_protocols) {
		this.access_protocols = access_protocols;
	}

	public int getStripe_count() {
		return stripe_count;
	}

	public void setStripe_count(int stripe_count) {
		this.stripe_count = stripe_count;
	}

}
