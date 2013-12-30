package org.gluster.mobile.model.entities;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "xml")
public class Volumes implements GlusterEntities {
	@ElementList(name = "gluster_volume", required = false, inline = true)
	private List<Volume> gluster_volumes = new ArrayList<Volume>();

	public List<Volume> getGluster_volumes() {
		return gluster_volumes;
	}

	public void setGluster_volumes(List<Volume> gluster_volumes) {
		this.gluster_volumes = gluster_volumes;
	}

	@Override
	public List<? extends GlusterEntity> getObjects() {
		// TODO Auto-generated method stub
		return getGluster_volumes();
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		// TODO Auto-generated method stub
		setGluster_volumes((List<Volume>) objects);
	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub
		List<Volume> temp = new ArrayList<Volume>();
		temp.add((Volume) obj);
	}

}