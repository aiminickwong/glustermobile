package org.gluster.mobile.activities;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Hosts;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.GlusterHttpGetApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class HostsDisplayActivity extends GlusterActivity<Host> {
	public static String url;
	public static String[] names;
	public static ListView lists;
	public static String clusterId;
	private List<Host> hostList;
	private List<HashMap<String, String>> listViewParams;
	private int[] host_ids = { R.id.glusterElement, R.id.elementProperty1 };
	private String[] column_tags = { "Name", "State" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hosts_display);
		init();
		lists.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, final long positionOfClick) {
				PopupMenu p = new PopupMenu(getApplicationContext(), lists);
				p.getMenu().add(0, 1, 0, "Properties");
				p.show();
				p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case 1:
							onPropertiesSelected((int) positionOfClick);
							break;
						}
						return true;
					}
				});
				return true;
			}
		});
	}

	public void init() {
		String searchCluster = "cluster=" + getIntent().getExtras().getString("clusterName");
		try {
			url = "http://" + ConnectionUtil.getInstance().getHost() + "/api/hosts/?search=" + URLEncoder.encode(searchCluster, "utf-8");
		} catch (Exception error) {
			error.printStackTrace();
		}
		clusterId = getIntent().getExtras().getString("clusterid");
		lists = (ListView) findViewById(R.id.listView1);
        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(HostsDisplayActivity.this, url, Hosts.class);
		asyncTaskParameter.setActivity(HostsDisplayActivity.this);
        new GlusterHttpGetApi<Hosts>().execute(asyncTaskParameter);
	}

	public void onPropertiesSelected(int positionOfClick) {
		Bundle nPParams = new Bundle();
		nPParams.putString("url", "http://" + ConnectionUtil.getInstance().getHost() + "/api/hosts/" + hostList.get((int) positionOfClick).getId());
		nPParams.putString("title", hostList.get((int) positionOfClick).getName());
		Intent nextPage = new Intent(getApplicationContext(), HostPropertiesActivity.class);
		nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(nPParams);
		getApplicationContext().startActivity(nextPage);

	}

	@Override
	public void after_get(List<Host> objectList) {
		hostList = objectList;
		new ListDisplay(lists, this, setListViewParams(), host_ids, column_tags).display();
	}

	private List<HashMap<String, String>> setListViewParams() {
		listViewParams = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < hostList.size(); i++) {
			HashMap<String, String> host = new HashMap<String, String>();
			host.put(column_tags[0], hostList.get(i).getName());
			host.put(column_tags[1], hostList.get(i).getState());
			listViewParams.add(host);
		}
		return listViewParams;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(HostsDisplayActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_hosts_display, menu);
		return true;
	}
}
