package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.gdisplays.ShowAlert;
import org.gluster.mobile.model.StartStopError;
import org.gluster.mobile.model.Volume;
import org.gluster.mobile.model.Volumes;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.GlusterHttpGetApi;
import org.gluster.mobile.web.GlusterHttpPostApi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class VolumeDisplayActivity extends GlusterActivity<Volume> {
	public static String url;
	public static ListView lists;
	public static String[] names;
	private String name;
	private List<Volume> volumeList;
	private PopupMenu p;
	private List<HashMap<String, String>> listViewParams;
	private int[] volume_ids = { R.id.glusterElement, R.id.elementProperty1, R.id.elementProperty2 };
	private String[] column_tags = { "Name", "Type", "State" };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.activity_volume_hosts, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_display);
		lists = (ListView) findViewById(R.id.listView1);
		name = getIntent().getExtras().getString("name");
		url = getIntent().getExtras().getString("url") + "/glustervolumes/";
		System.out.println("Context is : " + VolumeDisplayActivity.this
				+ "In volumeDisplayActivity");
		AsyncTaskParameter<Volumes> params = new AsyncTaskParameter<Volumes>(VolumeDisplayActivity.this, url, Volumes.class);
		params.setActivity(VolumeDisplayActivity.this);
        new GlusterHttpGetApi<Volumes>().execute(params);

		lists.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, final long positionOfClick) {
				setPopUp((int) positionOfClick);
				p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getOrder()) {
						case 0: {
							onPropertiesSelected((int) positionOfClick);
							break;
						}
						case 1: {
							onStartVolumeSelected((int) positionOfClick);
							break;
						}
						case 2: {
							onStopVolumeSelected((int) positionOfClick);
							break;
						}

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

	public void goToNextPage(int positionOfClick) {
		Bundle p = new Bundle();
		p.putString("url", url + volumeList.get((int) positionOfClick).getId());
		p.putString("volumeName", volumeList.get((int) positionOfClick).getName() + "of Cluster : " + name);
		String[] options = new String[volumeList.get((int) positionOfClick).getO().getOptions().size()];
		for (int i = 0; i < volumeList.get((int) positionOfClick).getO().getOptions().size(); i++) {
			options[i] = volumeList.get((int) positionOfClick).getO().getOptions().get(i).getName() + " = " + volumeList.get((int) positionOfClick).getO().getOptions().get(i).getValue();
		}
		p.putStringArray("options", options);
		getApplicationContext().startActivity(new Intent(getApplicationContext(), BrickOptionActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(p));
	}

	public void onStopVolumeSelected(int positionOfClick) {
		String requestBody = "<action/>";
        AsyncTaskParameter<StartStopError> asyncTaskParameter = new AsyncTaskParameter<StartStopError>(VolumeDisplayActivity.this, url + volumeList.get((int) positionOfClick).getId() + "/stop", StartStopError.class);
		asyncTaskParameter.setActivity(VolumeDisplayActivity.this);
		asyncTaskParameter.setRequestBody(requestBody);
        new GlusterHttpPostApi<StartStopError>().execute(asyncTaskParameter);
	}

	public void onStartVolumeSelected(int positionOfClick) {
		String requestBody = "<action/>";
        AsyncTaskParameter<StartStopError> asyncTaskParameter = new AsyncTaskParameter<StartStopError>(VolumeDisplayActivity.this, url + volumeList.get((int) positionOfClick).getId() + "/start", StartStopError.class);
		asyncTaskParameter.setActivity(VolumeDisplayActivity.this);
        asyncTaskParameter.setRequestBody(requestBody);
        new GlusterHttpPostApi<StartStopError>().execute(asyncTaskParameter);
	}

	public void onPropertiesSelected(int positionOfClick) {
		Bundle nPParams = new Bundle();
		nPParams.putString("url", url + volumeList.get((int) positionOfClick).getId());
		nPParams.putString("title", "Volume : " + volumeList.get((int) positionOfClick).getName());
		Intent nextPage = new Intent(getApplicationContext(), VolumePropertiesActivity.class);
		nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(nPParams);
		getApplicationContext().startActivity(nextPage);
	}

	public void setPopUp(int positionOfClick) {
		System.out.println("position is : " + positionOfClick);
		p = new PopupMenu(getApplicationContext(), lists);
		p.getMenu().add(0, 0, 0, "Properties");
		if (!volumeList.get(positionOfClick).getState().equalsIgnoreCase("up")) {
			p.getMenu().add(0, 1, 1, "Start");
		} else {
			p.getMenu().add(0, 2, 2, "Stop");
		}
		p.show();
	}

	public void after_get(List<Volume> objectList) {
		volumeList = objectList;
		new ListDisplay(lists, this, setListViewParams(), volume_ids, column_tags).display();
	}

	private List<HashMap<String, String>> setListViewParams() {
		listViewParams = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < volumeList.size(); i++) {
			HashMap<String, String> volume = new HashMap<String, String>();
			volume.put(column_tags[0], volumeList.get(i).getName());
			volume.put(column_tags[1], volumeList.get(i).getVolume_type());
			volume.put(column_tags[2], volumeList.get(i).getState());
			listViewParams.add(volume);
		}
		return listViewParams;
	}

	public void setStartStopAlert(String result, Context context) {
		String toDisplay;
		if (result.contains("complete")) {
			toDisplay = "Request executed Successfully";
		} else {
			toDisplay = "Request Failed";
		}
        new ShowAlert(toDisplay, VolumeDisplayActivity.this,VolumeDisplayActivity.class).showAlert();
	}

	public void createVolume() {
		Intent nextPage = new Intent(VolumeDisplayActivity.this, VolumeCreateActivity.class);
		Bundle nPParams = new Bundle();
		nPParams.putString("url", url);
		nPParams.putString("name", name);
		nPParams.putString("clusterHostUrl", getIntent().getExtras().getString("clusterHostUrl"));
		nextPage.putExtras(nPParams);
		startActivity(nextPage);
	}

	@Override
	public void after_post(String message) {
		if (message.equalsIgnoreCase("Done")) {
            AsyncTaskParameter<Volumes> asyncTaskParameter = new AsyncTaskParameter<Volumes>(VolumeDisplayActivity.this, url, Volumes.class);
			asyncTaskParameter.setActivity(VolumeDisplayActivity.this);
			new GlusterHttpGetApi<Volumes>().execute(asyncTaskParameter);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(VolumeDisplayActivity.this).handle();
			break;
		case R.id.CreateVolume:
			createVolume();
			break;
		default:
            break;
		}
		return true;

	}
}
