package org.gluster.mobile.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "options")
public class Options implements GlusterEntities {
	@ElementList(name = "options", required = false, inline = true)
	private List<Option> options = new ArrayList<Option>();

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@Override
	public List<? extends GlusterEntity> getObjects() {
		// TODO Auto-generated method stub
		return getOptions();
	}

	@Override
	public void setObjects(List<? extends GlusterEntity> objects) {
		// TODO Auto-generated method stub
		setOptions((List<Option>) objects);
	}

	@Override
	public <T extends GlusterEntity> void setObject(T obj) {
		// TODO Auto-generated method stub
		List<Option> temp = new ArrayList<Option>();
		temp.add((Option) obj);
	}
}
