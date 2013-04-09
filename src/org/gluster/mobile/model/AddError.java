package org.gluster.mobile.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "fault")
public class AddError implements GlusterEntities {
	@Element(name = "reason")
	private String reason;
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
		List<AddError> temp = new ArrayList<AddError>();
		temp.add(this);
		return (List<? extends GlusterEntity>) temp;
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub
		List<AddError> temp = new ArrayList<AddError>();
		temp.add((AddError) obj);
	}

}
