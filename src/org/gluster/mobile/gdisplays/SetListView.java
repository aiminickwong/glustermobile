package org.gluster.mobile.gdisplays;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.activities.BrickOptionActivity;
import org.gluster.mobile.activities.BrickPropertyActivity;
import org.gluster.mobile.activities.ClusterAddActivity;
import org.gluster.mobile.activities.ClusterPropertiesActivity;
import org.gluster.mobile.activities.HostPropertiesActivity;
import org.gluster.mobile.activities.R;
import org.gluster.mobile.activities.VolumeCreateActivity;
import org.gluster.mobile.activities.VolumeDisplayActivity;
import org.gluster.mobile.activities.VolumeHostsActivity;
import org.gluster.mobile.activities.VolumePropertiesActivity;
import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.GlusterEntity;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Option;
import org.gluster.mobile.model.Volume;
import org.gluster.mobile.params.AsyncTaskPostParameters;
import org.gluster.mobile.web.HttpDeleteRequests;
import org.gluster.mobile.web.HttpPostRequests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class SetListView<G extends GlusterEntity> {
	private ListView lists;
	private Context context;
	private List<G> objectList;
	private int choice;
	private String url;
	private String host;
	private Button button;
	private String name;

	public SetListView(Context context, ListView lists, List<G> objectList,
			String url, String host, int choice, Button button, int b,
			String name) {
		this.context = context;
		this.lists = lists;
		this.objectList = objectList;
		this.choice = choice;
		this.url = url;
		this.host = host;
		if (b == 1) {
			this.button = button;
			this.name = name;
		}

	}

	public void after_request(String result, Context context) {
		new VolumeDisplayActivity().setStartStopAlert(result, context);
	}

	public String[] displayParameters(int choice) {
		String[] listViewElements = new String[objectList.size()];
		int i = 0;
		switch (choice) {
		case 1:
			ArrayList<Cluster> clusterList = new ArrayList<Cluster>(
					(ArrayList<Cluster>) objectList);
			System.out.println("In setlistview size of clusterlist is : "
					+ clusterList.size());
			for (i = 0; i < objectList.size(); i++) {
				listViewElements[i] = clusterList.get(i).getName();
			}
			System.out.println("ListView is not null.Its size is : "
					+ listViewElements.length);
			break;
		case 2:
			ArrayList<Volume> volumeList = new ArrayList<Volume>(
					(ArrayList<Volume>) objectList);
			for (i = 0; i < objectList.size(); i++) {
				listViewElements[i] = volumeList.get(i).getName();
			}
			break;
		case 4:
			ArrayList<Host> hostList = new ArrayList<Host>(
					(ArrayList<Host>) objectList);
			for (i = 0; i < objectList.size(); i++) {
				listViewElements[i] = hostList.get(i).getName();
			}
			break;
		case 5:
			ArrayList<Brick> brickList = new ArrayList<Brick>(
					(ArrayList<Brick>) objectList);
			for (i = 0; i < objectList.size(); i++) {
				listViewElements[i] = brickList.get(i).getName();
			}
			break;
		case 6:
			ArrayList<Option> optionList = new ArrayList<Option>(
					(ArrayList<Option>) objectList);
			for (i = 0; i < objectList.size(); i++) {
				listViewElements[i] = optionList.get(i).getName();
			}
			break;
		}
		return listViewElements;
	}

	public void setDisplay() {
		System.out.println("In setListView size of objectlist is : "
				+ objectList.size());
		System.out.println("In setListView choicee is : " + choice);
		String[] listViewElements = displayParameters(choice);
		System.out.println("Context is: " + context);
		if (objectList.size() == 0) {
			listViewElements = new String[1];
			listViewElements[0] = "Nothing To Display";
		}
		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(context,
				R.layout.list_view_style, listViewElements);
		lists.setAdapter(stringAdapter);
		System.out.println("After setting listView");
		afterDisplay(choice);
	}

	public void afterDisplay(int choice) {
		switch (choice) {
		case 1: {
			lists.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, final long positionOfClick) {
					// TODO Auto-generated method stub
					PopupMenu p = new PopupMenu(context, lists);
					p.getMenuInflater().inflate(R.menu.popupmenu, p.getMenu());
					p.show();
					p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							ArrayList<Cluster> clusterList = new ArrayList<Cluster>(
									(ArrayList<Cluster>) objectList);
							switch (item.toString().charAt(0)) {
							case 'P': {
								Bundle nPParams = new Bundle();
								nPParams.putString("url", url
										+ clusterList
												.get((int) positionOfClick)
												.getId());
								nPParams.putString("title", "Cluster : "
										+ clusterList
												.get((int) positionOfClick)
												.getName());
								System.out.println("In setListView url is : "
										+ url
										+ clusterList
												.get((int) positionOfClick)
												.getId());
								Intent nextPage = new Intent(context,
										ClusterPropertiesActivity.class);
								nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
										.putExtras(nPParams);
								context.startActivity(nextPage);
								break;
							}
							case 'D':
								AsyncTaskPostParameters params = new AsyncTaskPostParameters();
								params.setContext(context);
								params.setRequestBody("<async>false</async>");
								params.setUrl(url
										+ clusterList
												.get((int) positionOfClick)
												.getId());
								new HttpDeleteRequests().execute(params);
								System.out.println("Delete");
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
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long positionOfClick) {
					ArrayList<Cluster> clusterList = new ArrayList<Cluster>(
							(ArrayList<Cluster>) objectList);

					// TODO Auto-generated method stub
					Bundle p = new Bundle();
					System.out
							.println("In list click function url is : " + url);
					p.putString("url", url);
					p.putString("host", host);
					System.out
							.println("In list click function clusterName is : "
									+ clusterList.get((int) positionOfClick)
											.getName());
					p.putString("clusterName",
							clusterList.get((int) positionOfClick).getName());
					System.out
							.println("In list click function clusterName is : "
									+ clusterList.get((int) positionOfClick)
											.getId());
					p.putString("id", clusterList.get((int) positionOfClick)
							.getId());
					context.startActivity(new Intent(context,
							VolumeHostsActivity.class).addFlags(
							Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(p));
				}
			});

			if (button.equals(null)) {
				System.out.println("Button is null.Caught the culprit");
			}

			try {
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent nextPage = new Intent(context,
								ClusterAddActivity.class);
						nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(nextPage);
					}
				});
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			break;
		}
		case 2: {
			lists.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, final long positionOfClick) {
					// TODO Auto-generated method stub
					PopupMenu p = new PopupMenu(context, lists);
					p.getMenuInflater().inflate(R.menu.popupmenu, p.getMenu());
					p.show();
					p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							ArrayList<Volume> volumeList = new ArrayList<Volume>(
									(ArrayList<Volume>) objectList);
							System.out
									.println("In option menu you clicked on : "
											+ item.getItemId());

							switch (item.getOrder()) {
							case 0: {
								Bundle nPParams = new Bundle();
								nPParams.putString(
										"url",
										url
												+ volumeList.get(
														(int) positionOfClick)
														.getId());
								nPParams.putString("title", "Volume : "
										+ volumeList.get((int) positionOfClick)
												.getName());
								Intent nextPage = new Intent(context,
										VolumePropertiesActivity.class);
								nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
										.putExtras(nPParams);
								context.startActivity(nextPage);
								break;
							}
							case 1: {
								System.out.println("Delete");
								break;
							}
							case 2: {
								String requestBody = "<action/>";
								AsyncTaskPostParameters params = new AsyncTaskPostParameters();
								params.setContext(context);
								params.setChoice(2);
								params.setRequestBody(requestBody);
								params.setUrl(url
										+ volumeList.get((int) positionOfClick)
												.getId() + "/start");
								new HttpPostRequests().execute(params);
								break;
							}
							case 3: {
								String requestBody = "<action/>";
								AsyncTaskPostParameters params = new AsyncTaskPostParameters();
								params.setContext(context);
								params.setRequestBody(requestBody);
								params.setChoice(2);
								params.setUrl(url
										+ volumeList.get((int) positionOfClick)
												.getId() + "/stop");
								new HttpPostRequests().execute(params);
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
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long positionOfClick) {
					ArrayList<Volume> volumeList = new ArrayList<Volume>(
							(ArrayList<Volume>) objectList);
					Bundle p = new Bundle();
					System.out.println("In list click function url is : " + url
							+ volumeList.get((int) positionOfClick).getId());
					p.putString("url",
							url + volumeList.get((int) positionOfClick).getId());

					System.out
							.println("In list click function volumeName is : "
									+ volumeList.get((int) positionOfClick)
											.getName());
					p.putString("volumeName",
							volumeList.get((int) positionOfClick).getName());
					String[] options = new String[volumeList
							.get((int) positionOfClick).getO().getOptions()
							.size()];

					for (int i = 0; i < volumeList.get((int) positionOfClick)
							.getO().getOptions().size(); i++) {
						options[i] = volumeList.get((int) positionOfClick)
								.getO().getOptions().get(i).getName();
					}
					p.putStringArray("options", options);
					context.startActivity(new Intent(context,
							BrickOptionActivity.class).addFlags(
							Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(p));
				}
			});
			button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle nPParams = new Bundle();
					nPParams.putString("url", url);
					nPParams.putString("name", name);
					Intent nextPage = new Intent(context,
							VolumeCreateActivity.class).putExtras(nPParams);
					nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(nextPage);

				}
			});
			break;
		}
		case 4: {
			lists.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, final long positionOfClick) {
					// TODO Auto-generated method stub
					PopupMenu p = new PopupMenu(context, lists);
					p.getMenuInflater().inflate(R.menu.popupmenu, p.getMenu());
					p.show();
					p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							ArrayList<Host> hostList = new ArrayList<Host>(
									(ArrayList<Host>) objectList);
							switch (item.toString().charAt(0)) {
							case 'P':
								Bundle nPParams = new Bundle();
								System.out
										.println("In setlistview host properties url is : "
												+ url
												+ hostList.get(
														(int) positionOfClick)
														.getId());
								nPParams.putString(
										"url",
										url
												+ hostList.get(
														(int) positionOfClick)
														.getId());
								nPParams.putString("title",
										hostList.get((int) positionOfClick)
												.getName());
								Intent nextPage = new Intent(context,
										HostPropertiesActivity.class);
								nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
										.putExtras(nPParams);
								context.startActivity(nextPage);
								break;
							case 'D':
								System.out.println("Delete");
								break;
							}
							return true;
						}
					});
					return true;
				}
			});
			break;

		}
		case 5: {
			lists.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, final long positionOfClick) {
					// TODO Auto-generated method stub
					PopupMenu p = new PopupMenu(context, lists);
					p.getMenuInflater().inflate(R.menu.popupmenu, p.getMenu());
					p.show();
					p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							ArrayList<Brick> brickList = new ArrayList<Brick>(
									(ArrayList<Brick>) objectList);
							switch (item.toString().charAt(0)) {
							case 'P':
								Bundle nPParams = new Bundle();
								System.out
										.println("In setlistview host properties url is : "
												+ url
												+ "/"
												+ brickList.get(
														(int) positionOfClick)
														.getId());
								nPParams.putString("url", url
										+ "/"
										+ brickList.get((int) positionOfClick)
												.getId());
								nPParams.putString("title",
										brickList.get((int) positionOfClick)
												.getName());
								Intent nextPage = new Intent(context,
										BrickPropertyActivity.class);
								nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
										.putExtras(nPParams);
								context.startActivity(nextPage);
								break;
							case 'D':
								System.out.println("Delete");
								break;
							}
							return false;
						}
					});
					return false;
				}
			});
			break;
		}
		}
	}
}
