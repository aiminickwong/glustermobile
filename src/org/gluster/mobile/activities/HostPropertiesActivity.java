package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.entities.Host;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.GlusterHttpGetApi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HostPropertiesActivity extends GlusterActivity<Host> {
	static String url;
	static TextView hostProperties;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_properties);
		setTitle("Host : " + getIntent().getExtras().getString("title"));
		url = getIntent().getExtras().getString("url");
		hostProperties = (TextView) findViewById(R.id.hostProps);
        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(HostPropertiesActivity.this, url, Host.class);
		asyncTaskParameter.setActivity(HostPropertiesActivity.this);
        new GlusterHttpGetApi<Host>().execute(asyncTaskParameter);
	}

	public void after_get(ArrayList<?> objectList) {
		new SetTextView(hostProperties, objectList, 6).display(6);
	}

	@Override
	public void after_getObject(Host object) {
		ArrayList<Host> objectList = new ArrayList<Host>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(hostProperties, objectList, 6).display(6);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_host_properties, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(HostPropertiesActivity.this).handle();
			break;
		}
		return true;
	}
}
