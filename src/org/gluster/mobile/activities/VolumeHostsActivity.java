package org.gluster.mobile.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class VolumeHostsActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String title = getIntent().getExtras().getString("clusterName");
		setTitle("Cluster : " + title);
		String url = getIntent().getExtras().getString("url");
		String clusterid = getIntent().getExtras().getString("id");
		TabHost tabHost = getTabHost();
		if (tabHost.equals(null)) {
			System.out.println("Null!!");
		}
		TabSpec volumes = tabHost.newTabSpec("Volumes");
		volumes.setIndicator("Volumes");
		Bundle clusterVolumeUrl = new Bundle();
		String clusterUrl = url + clusterid;
		System.out.println("In volumehosts activity url is  : " + clusterUrl);
		clusterVolumeUrl.putString("url", clusterUrl);
		clusterVolumeUrl.putString("name", title);
		Intent volumesIntent = new Intent(VolumeHostsActivity.this,
				VolumeDisplayActivity.class);
		volumesIntent.putExtras(clusterVolumeUrl);
		volumes.setContent(volumesIntent);

		TabSpec hosts = tabHost.newTabSpec("Hosts");
		hosts.setIndicator("Hosts");
		Bundle hostClusterId = new Bundle();
		hostClusterId.putString("clusterid", clusterid);
		hostClusterId.putString("url", getIntent().getExtras()
				.getString("host") + "/api/hosts/");
		Intent hostsIntent = new Intent(VolumeHostsActivity.this,
				HostsDisplayActivity.class);
		hostsIntent.putExtras(hostClusterId);
		hosts.setContent(hostsIntent);

		tabHost.addTab(volumes);
		tabHost.addTab(hosts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_volume_hosts, menu);
		return true;
	}

}
