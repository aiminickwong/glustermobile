package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.entities.Cluster;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.GlusterHttpGetApi;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ClusterPropertiesActivity extends GlusterActivity<Cluster> {
	public static TextView display;
	public static String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cluster_properties);
		setTitle(getIntent().getExtras().getString("title"));
		url = getIntent().getExtras().getString("url");
		display = (TextView) findViewById(R.id.textView1);
        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(ClusterPropertiesActivity.this, url, Cluster.class);
        asyncTaskParameter.setActivity(ClusterPropertiesActivity.this);
        new GlusterHttpGetApi<Cluster>().execute(asyncTaskParameter);
	}

	@Override
	public void after_getObject(Cluster object) {
		ArrayList<Cluster> objectList = new ArrayList<Cluster>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(display, objectList, 3).display(3);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(ClusterPropertiesActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cluster_properties, menu);
		return true;
	}
}
