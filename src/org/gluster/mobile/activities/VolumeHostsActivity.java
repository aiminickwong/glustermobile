package org.gluster.mobile.activities;

import java.net.URLEncoder;

import org.gluster.mobile.web.ConnectionUtil;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class VolumeHostsActivity extends TabActivity {
	private String title;
	private String clusterUrl;
	private String searchCluster;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		title = getIntent().getExtras().getString("clusterName");
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
		clusterUrl = url + clusterid;
		System.out.println("In volumehosts activity url is  : " + clusterUrl);
		clusterVolumeUrl.putString("url", clusterUrl);
		clusterVolumeUrl.putString("name", title);
		searchCluster = "cluster=" + title;
		try {
			clusterVolumeUrl.putString(
					"clusterHostUrl",
					"http://" + ConnectionUtil.getInstance().getHost()
							+ "/api/hosts/?search="
							+ URLEncoder.encode(searchCluster, "utf-8"));
		} catch (Exception error) {
			System.out.println(error.toString());
		}

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
		hostClusterId.putString("clusterName", title);
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

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.CreateVolume:
			createVolume();
			break;
		default:
			System.out.println("CLicked!!!!!!");

		}
		return true;

	}

	private void createVolume() {
		// TODO Auto-generated method stub
		try {
			Intent nextPage = new Intent(VolumeHostsActivity.this,
					VolumeCreateActivity.class);
			Bundle nPParams = new Bundle();
			nPParams.putString("url", clusterUrl + "/glustervolumes/");
			nPParams.putString("name", title);
			nPParams.putString(
					"clusterHostUrl",
					"http://" + ConnectionUtil.getInstance().getHost()
							+ "/api/hosts/?search="
							+ URLEncoder.encode(searchCluster, "utf-8"));
			nextPage.putExtras(nPParams);
			startActivity(nextPage);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

}
