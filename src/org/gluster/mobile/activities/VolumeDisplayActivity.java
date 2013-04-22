package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.model.Volume;
import org.gluster.mobile.model.Volumes;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.params.AsyncTaskPostParameters;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;
import org.gluster.mobile.web.HttpPostRequests;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
	private int[] volume_ids = { R.id.glusterElement, R.id.elementProperty1,
			R.id.elementProperty2 };
	private String[] column_tags = { "Name", "Type", "State" };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
		AsyncTaskParameters<Volumes> params = new AsyncTaskParameters<Volumes>();
		params.setClassNames(Volumes.class);
		params.setChoice(2);
		params.setUrl(url);
		params.setActivity(VolumeDisplayActivity.this);
		params.setContext(this);
		params.setHost(ConnectionUtil.getInstance().getHost());
		// p.setContext(this);
		// p.setDisplay(lists);
		// p.setListOrText(true);
		System.out.println("in volume display activity");
		new HttpPageGetter<Volumes, Volume>().execute(params);
		lists.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, final long positionOfClick) {
				// TODO Auto-generated method stub
				setPopUp((int) positionOfClick);
				p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// TODO Auto-generated method stub

						System.out.println("In option menu you clicked on : "
								+ item.getItemId());

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
		System.out.println("In list click function url is : " + url
				+ volumeList.get((int) positionOfClick).getId());
		p.putString("url", url + volumeList.get((int) positionOfClick).getId());
		System.out.println("In list click function volumeName is : "
				+ volumeList.get((int) positionOfClick).getName());
		p.putString("volumeName", volumeList.get((int) positionOfClick)
				.getName() + "of Cluster : " + name);
		String[] options = new String[volumeList.get((int) positionOfClick)
				.getO().getOptions().size()];
		for (int i = 0; i < volumeList.get((int) positionOfClick).getO()
				.getOptions().size(); i++) {
			options[i] = volumeList.get((int) positionOfClick).getO()
					.getOptions().get(i).getName()
					+ " = "
					+ volumeList.get((int) positionOfClick).getO().getOptions()
							.get(i).getValue();
		}
		p.putStringArray("options", options);
		getApplicationContext().startActivity(
				new Intent(getApplicationContext(), BrickOptionActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(p));
	}

	public void onStopVolumeSelected(int positionOfClick) {
		String requestBody = "<action/>";
		AsyncTaskPostParameters params = new AsyncTaskPostParameters();
		params.setContext(VolumeDisplayActivity.this);
		params.setRequestBody(requestBody);
		params.setChoice(2);
		params.setUrl(url + volumeList.get((int) positionOfClick).getId()
				+ "/stop");
		new HttpPostRequests().execute(params);
	}

	public void onStartVolumeSelected(int positionOfClick) {
		String requestBody = "<action/>";
		AsyncTaskPostParameters params = new AsyncTaskPostParameters();
		params.setContext(VolumeDisplayActivity.this);
		params.setChoice(2);
		params.setRequestBody(requestBody);
		params.setUrl(url + volumeList.get((int) positionOfClick).getId()
				+ "/start");
		new HttpPostRequests().execute(params);

	}

	public void onPropertiesSelected(int positionOfClick) {
		Bundle nPParams = new Bundle();
		nPParams.putString("url", url
				+ volumeList.get((int) positionOfClick).getId());
		nPParams.putString("title",
				"Volume : " + volumeList.get((int) positionOfClick).getName());
		Intent nextPage = new Intent(getApplicationContext(),
				VolumePropertiesActivity.class);
		nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(nPParams);
		getApplicationContext().startActivity(nextPage);
	}

	public void setPopUp(int positionOfClick) {
		p = new PopupMenu(getApplicationContext(), lists);
		p.getMenu().add(0, 0, 0, "Properties");
		if (!volumeList.get(positionOfClick).getState().equalsIgnoreCase("up")) {
			p.getMenu().add(0, 1, 1, "Start");
		} else {
			p.getMenu().add(0, 2, 1, "Stop");
		}
		p.show();
	}

	public void after_get(List<Volume> objectList) {
		System.out.println(objectList.size());
		volumeList = objectList;
		new ListDisplay(lists, this, setListViewParams(), volume_ids,
				column_tags).display();
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
		System.out.println("In listView after requesting ");
		System.out.println("Context is : " + context);
		String toDisplay;
		System.out.println(result + "In setlistview");
		if (result.contains("complete")) {
			toDisplay = "Request executed Successfully";
		} else {
			toDisplay = "Request Failed";
		}
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("Post Request Status")
				.setMessage(toDisplay)
				.setPositiveButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public void createVolume() {
		// TODO Auto-generated method stub
		Intent nextPage = new Intent(VolumeDisplayActivity.this,
				VolumeCreateActivity.class);
		Bundle nPParams = new Bundle();
		nPParams.putString("url", url);
		nPParams.putString("name", name);
		nPParams.putString("clusterHostUrl",
				getIntent().getExtras().getString("clusterHostUrl"));
		nextPage.putExtras(nPParams);
		startActivity(nextPage);
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
			System.out.println("CLicked!!!!!!");
		}
		return true;

	}
}
