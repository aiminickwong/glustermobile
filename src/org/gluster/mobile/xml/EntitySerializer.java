package org.gluster.mobile.xml;

import java.io.StringWriter;
import java.io.Writer;

import org.gluster.mobile.model.GlusterEntity;
import org.simpleframework.xml.core.Persister;

public class EntitySerializer {
	public String deSerialize(GlusterEntity object, String objectClass) {
		Persister persist = new Persister();
		Writer out = new StringWriter();
		try {
			persist.write(object, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toString();
	}

}
