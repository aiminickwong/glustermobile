package org.gluster.mobile.model.errors;

/**
 * Created by ababu on 12/26/13.
 */
public interface GlusterErrors {
    String detail = "";
    String reason = "";

    public String getDetail();
    public void setDetail(String detail);
    public String  getReason();
    public void setReason(String reason);
}
