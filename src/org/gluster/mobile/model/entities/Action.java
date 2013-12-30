package org.gluster.mobile.model.entities;

import org.simpleframework.xml.Element;

public class Action implements GlusterEntity {
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
}
