package org.gluster.mobile.model;

import java.util.List;

public interface GlusterEntities extends  GlusterEntity{
	public List<? extends GlusterEntity> getObjects();

	public void setObjects(List<? extends GlusterEntity> objects);

	public <T extends GlusterEntity> void setObject(T obj);
}
