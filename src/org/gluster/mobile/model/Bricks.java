package org.gluster.mobile.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "bricks")
public class Bricks implements GlusterEntities {
	@ElementList(name = "brick", required = false, inline = true)
	List<Brick> bricks = new ArrayList<Brick>();

	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	@Override
	public List<? extends GlusterEntity> getObjects() {
		// TODO Auto-generated method stub
		return getBricks();
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		// TODO Auto-generated method stub
		setBricks((List<Brick>) objects);
	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub
		List<Brick> temp = new ArrayList<Brick>();
		temp.add((Brick) obj);
	}

}
