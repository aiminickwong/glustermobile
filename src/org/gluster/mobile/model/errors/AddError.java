package org.gluster.mobile.model.errors;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "fault")
public class AddError implements GlusterErrors {
	@Element(name = "reason")
	private String reason;
	@Element(name = "detail")
	private String detail;

    @Override
	public String getReason() {
		return reason;
	}

    @Override
	public void setReason(String reason) {
		this.reason = reason;
	}

    @Override
	public String getDetail() {
		return detail;
	}

    @Override
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
