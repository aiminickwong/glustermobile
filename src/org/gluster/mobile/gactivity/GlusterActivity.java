package org.gluster.mobile.gactivity;

import java.util.List;

import org.gluster.mobile.model.GlusterEntity;

import android.app.Activity;

public class GlusterActivity<G extends GlusterEntity> extends Activity {
	public void after_get(List<G> objectList) {

	}

	public void after_post(String message) {

	}

	public void after_getObject(G object) {

	}

	public void after_delete(String message) {

	}

	public void after_create() {

	}
}
