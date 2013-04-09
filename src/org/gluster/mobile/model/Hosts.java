package org.gluster.mobile.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "hosts")
public class Hosts implements GlusterEntities {
	@ElementList(name = "hosts", required = true, inline = true)
	private ArrayList<Host> hosts = new ArrayList<Host>();

	public ArrayList<Host> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<Host> hosts) {
		this.hosts = hosts;
	}

	@Override
	public List<? extends GlusterEntity> getObjects() {
		// TODO Auto-generated method stub
		return getHosts();
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		// TODO Auto-generated method stub
		setObjects((List<Host>) objects);
	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub
		List<Host> temp = new ArrayList<Host>();
		temp.add((Host) obj);
	}

}
