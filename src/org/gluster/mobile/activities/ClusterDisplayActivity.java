package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.gdisplays.ShowAlert;
import org.gluster.mobile.model.AddError;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.Clusters;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.GlusterHttpDeleteApi;
import org.gluster.mobile.web.GlusterHttpGetApi;
import org.gluster.mobile.xml.XmlDeSerializer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class ClusterDisplayActivity extends GlusterActivity<Cluster> {
	public static String[] names;
	static ListView lists;
	public GlusterActivity<Cluster> activity;
	private List<Cluster> clusterList;
	private String url;
	private PopupMenu p;
	private List<HashMap<String, String>> listViewParams;
	private int[] column_ids = { R.id.glusterElement, R.id.elementProperty1, R.id.elementProperty2 };
	private String[] column_tags = { "Cluster Name", "Gluster Service", "Virt Service" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cluster_display);
		init();
		lists.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, final long positionOfClick) {
				setPopUp();
				p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case 0: {
							onPropertiesSelected((int) positionOfClick);
							break;
						}
						case 1:
							onDeleteSelected((int) positionOfClick);
							break;

						}
						return true;
					}
				});
				return true;
			}
		});
		lists.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long positionOfClick) {
				goToNextPage((int) positionOfClick);
			}
		});
	}

	public void init() {
		lists = (ListView) findViewById(R.id.listView1);
		url = ConnectionUtil.getInstance().getHost();
        requestData();
	}

    private void requestData() {
        AsyncTaskParameter<Clusters> atp = new AsyncTaskParameter<Clusters>(ClusterDisplayActivity.this, "http://" + url + "/api/clusters/", Clusters.class);
        atp.setIntentActivity(ClusterDisplayActivity.class);
        atp.setActivity(this);
        activity = this;
        new GlusterHttpGetApi<Clusters>().execute(atp);
    }

    private void requestDataTemp() {
        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(ClusterDisplayActivity.this, "http://" + url + "/api/clusters/", Clusters.class);
        asyncTaskParameter.setActivity(ClusterDisplayActivity.this);
        activity = this;
        new GlusterHttpGetApi<Clusters>().execute(asyncTaskParameter);
    }
    public void goToNextPage(int positionOfClick) {
		Bundle p = new Bundle();
		p.putString("url", "http://" + url + "/api/clusters/");
		p.putString("host", ConnectionUtil.getInstance().getHost());
		p.putString("clusterName", clusterList.get((int) positionOfClick).getName());
		p.putString("id", clusterList.get((int) positionOfClick).getId());
		getApplicationContext().startActivity(new Intent(getApplicationContext(), VolumeHostsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(p));
	}

	public void createCluster() {
		Intent nextPage = new Intent(ClusterDisplayActivity.this, ClusterAddActivity.class);
		startActivity(nextPage);
	}

	public void setPopUp() {
		p = new PopupMenu(getApplicationContext(), lists);
		p.getMenu().add(0, 0, 0, "Properties");
		p.getMenu().add(0, 1, 1, "Delete");
		p.show();
	}

	public void onDeleteSelected(int positionOfClick) {
		AsyncTaskParameter params = new AsyncTaskParameter(ClusterDisplayActivity.this, "http://" + url + "/api/clusters/" + clusterList.get((int) positionOfClick).getId(), null);
		params.setRequestBody("<async>false</async>");
		params.setActivity(ClusterDisplayActivity.this);
		new GlusterHttpDeleteApi().execute(params);
	}

	public void onPropertiesSelected(int positionOfClick) {
		Bundle nPParams = new Bundle();
		nPParams.putString("url", "http://" + url + "/api/clusters/" + clusterList.get(positionOfClick).getId());
		nPParams.putString("title", "Cluster : " + clusterList.get((int) positionOfClick).getName());
		Intent nextPage = new Intent(ClusterDisplayActivity.this, ClusterPropertiesActivity.class);
		nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(nPParams);
		getApplicationContext().startActivity(nextPage);
	}

	@Override
	public void after_get(List<Cluster> objectList) {
		clusterList = objectList;
		new ListDisplay(lists, this, setListViewParams(), column_ids, column_tags).display();
	}

	private List<HashMap<String, String>> setListViewParams() {
		listViewParams = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < clusterList.size(); i++) {
			HashMap<String, String> cluster = new HashMap<String, String>();
			cluster.put(column_tags[0], clusterList.get(i).getName());
			if (clusterList.get(i).getGluster_service()) {
				cluster.put(column_tags[1], "Gluster-Enabled");
			} else {
				cluster.put(column_tags[1], "Gluster-Disabled");
			}
			if (clusterList.get(i).getVirt_service()) {
				cluster.put(column_tags[2], "Virt-Enabled");
			} else {
				cluster.put(column_tags[2], "Virt-Disabled");
			}
			listViewParams.add(cluster);
		}
		return listViewParams;
	}

	@Override
	public void after_delete(String message) {
		String error;
		if (message.contains("fault")) {
			AddError ae = new XmlDeSerializer<AddError>(message).deSerialize(AddError.class);
			error = ae.getDetail();
			System.out.println(error);
		} else {
			error = "Successful";
		}
        new ShowAlert(error, ClusterDisplayActivity.this,ClusterDisplayActivity.class).showAlert();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create:
			createCluster();
			break;
		case R.id.Settings:
			new SettingsHandler(ClusterDisplayActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cluster_display, menu);
		return true;
	}
}
