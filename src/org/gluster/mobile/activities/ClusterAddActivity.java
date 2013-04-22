package org.gluster.mobile.activities;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.DataCenter;
import org.gluster.mobile.params.AsyncTaskPostParameters;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPostRequests;
import org.gluster.mobile.xml.EntitySerializer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ClusterAddActivity extends GlusterActivity<Cluster> {
	private Spinner dataCenters;
	private Spinner cpuIds;
	private EditText name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cluster_add);
		init();
	}

	private void createCluster() {
		Cluster c = new Cluster();
		c.setName(name.getText().toString());
		c.setGluster_service(true);
		c.setVirt_service(false);
		DataCenter dataCenter = new DataCenter();
		dataCenter.setName(dataCenters.getSelectedItem().toString());
		c.setData_center(dataCenter);
		String xml = new EntitySerializer().deSerialize(c, "Cluster.class");
		System.out.println("In Activity : " + xml);
		AsyncTaskPostParameters params = new AsyncTaskPostParameters();
		params.setRequestBody(xml);
		params.setChoice(1);
		params.setContext(ClusterAddActivity.this);
		params.setActivity(ClusterAddActivity.this);
		params.setUrl("http://" + ConnectionUtil.getInstance().getHost()
				+ "/api/clusters");
		new HttpPostRequests().execute(params);
	}

	private void cancel() {
		Intent nextPage = new Intent(ClusterAddActivity.this,
				ClusterDisplayActivity.class);
		startActivity(nextPage);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(ClusterAddActivity.this).handle();
			break;
		case R.id.Done:
			createCluster();
			break;
		case R.id.Cancel:
			cancel();
			break;
		}
		return true;
	}

	public void after_post(String message) {
		new SetAlertBox(message, ClusterAddActivity.this, 1).showDialog();
		finish();
	}

	private void init() {
		dataCenters = (Spinner) findViewById(R.id.spinner1);
		name = (EditText) findViewById(R.id.editText1);
		String[] dataCenter = { "Default" };
		String[] cpuId = { "Intel Conroe Family", "Intel Penryn Family",
				"Intel Nehalem Family", "Intel Westmere Family",
				"Intel SandyBridge Family", "AMD Opteron G1", "AMD Opteron G2",
				"AMD Opteron G3", "AMD Opteron G4" };
		cpuIds = (Spinner) findViewById(R.id.spinner2);
		name = (EditText) findViewById(R.id.editText1);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, dataCenter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataCenters.setAdapter(adapter);
		adapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item, cpuId);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cpuIds.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cluster_add, menu);
		return true;
	}

}
