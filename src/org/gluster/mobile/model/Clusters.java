package org.gluster.mobile.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "xml")
public class Clusters implements GlusterEntities {
	@ElementList(name = "cluster", required = true, inline = true)
	List<Cluster> clusters = new ArrayList<Cluster>();

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public String[] getClusterNames(int size, ArrayList<Cluster> c) {
		String[] names = new String[size];
		for (int i = 0; i < size; i++) {
			names[i] = c.get(i).getName();
		}
		return names;
	}

	@Override
	public List<? extends GlusterEntity> getObjects() {
		return getClusters();
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		setClusters((List<Cluster>) objects);
	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub
		List<Cluster> temp = new ArrayList<Cluster>();
		temp.add((Cluster) obj);

	}
}
