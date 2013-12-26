package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.Volume;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.GlusterHttpGetApi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class VolumePropertiesActivity extends GlusterActivity<Volume> {
	static String url;
	static TextView props;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_properties);
		setTitle(getIntent().getExtras().getString("title"));
		props = (TextView) findViewById(R.id.properties);
		url = getIntent().getExtras().getString("url");

        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(VolumePropertiesActivity.this, url, Volume.class);
		asyncTaskParameter.setActivity(VolumePropertiesActivity.this);
        new GlusterHttpGetApi<Volume>().execute(asyncTaskParameter);
	}

	@Override
	public void after_getObject(Volume object) {
		ArrayList<Volume> objectList = new ArrayList<Volume>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(props, objectList, 7).display(7);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(VolumePropertiesActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_volume_properties, menu);
		return true;
	}
}
