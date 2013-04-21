package org.gluster.mobile.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "action")
public class StartStopError implements GlusterEntities {
	@Path("fault")
	@Element(name = "reason")
	private String reason;
	@Path("fault")
	@Element(name = "detail")
	private String detail;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public List<? extends GlusterEntity> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub

	}

}
