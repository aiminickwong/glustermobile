package org.gluster.mobile.model.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "gluster_volume")
public class VolumeCreate implements GlusterEntity {
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "replica_count", required = false)
	private int replica_count;
	@Element(name = "stripe_count", required = false)
	private int stripe_count;
	@Element(name = "volume_type", required = false)
	private String volume_type;
	@Element(name = "bricks", required = false)
	Bricks bricks = new Bricks();
	@Element(name = "options", required = false)
	Options optionList = new Options();

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

	public Bricks getBricks() {
		return bricks;
	}

	public void setBricks(Bricks bricks) {
		this.bricks = bricks;
	}

	public int getReplica_count() {
		return replica_count;
	}

	public void setReplica_count(int replica_count) {
		this.replica_count = replica_count;
	}

	public int getStripe_count() {
		return stripe_count;
	}

	public void setStripe_count(int stripe_count) {
		this.stripe_count = stripe_count;
	}

	public Options getOptionList() {
		return optionList;
	}

	public void setOptionList(Options optionList) {
		this.optionList = optionList;
	}
}
