package org.gluster.mobile.activities;

import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetListView;
import org.gluster.mobile.model.Volume;
import org.gluster.mobile.model.Volumes;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class VolumeDisplayActivity extends GlusterActivity<Volume> {
	public static String url;
	public static ListView lists;
	public static String[] names;
	private String name;
	private Button create;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_display);
		lists = (ListView) findViewById(R.id.listView1);
		name = getIntent().getExtras().getString("name");
		create = (Button) findViewById(R.id.button1);
		url = getIntent().getExtras().getString("url") + "/glustervolumes/";
		System.out.println("Context is : " + VolumeDisplayActivity.this
				+ "In volumeDisplayActivity");
		AsyncTaskParameters<Volumes> p = new AsyncTaskParameters<Volumes>();
		p.setClassNames(Volumes.class);
		p.setChoice(2);
		p.setUrl(url);
		p.setActivity(VolumeDisplayActivity.this);
		p.setContext(this);
		p.setHost(ConnectionUtil.getInstance().getHost());
		// p.setContext(this);
		// p.setDisplay(lists);
		// p.setListOrText(true);
		System.out.println("in volume display activity");
		new HttpPageGetter<Volumes, Volume>().execute(p);
		create.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent nextPage = new Intent(VolumeDisplayActivity.this,
						VolumeCreateActivity.class);
				Bundle nPParams = new Bundle();
				nPParams.putString("url", url);
				nPParams.putString("name", name);
				nPParams.putString("clusterHostUrl", getIntent().getExtras()
						.getString("clusterHostUrl"));
				nextPage.putExtras(nPParams);
				startActivity(nextPage);

			}
		});

	}

	public void after_get(List<Volume> objectList) {
		System.out.println(objectList.size());
		new SetListView(VolumeDisplayActivity.this, lists, objectList, url,
				ConnectionUtil.getInstance().getHost(), 2,
				(Button) findViewById(R.id.button1), 1, name).setDisplay();
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
}
/*
 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
 * menu; this adds items to the action ebar if it is // present.
 * getMenuInflater().inflate(R.menu.activity_volume_display, menu); return true;
 * }
 * 
 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
 * String[], ArrayList<String>> { ArrayList<String> t_res; ArrayList<Volume> v;
 * Context con;
 * 
 * @Override protected ArrayList<String> doInBackground(AsyncTaskParameters...
 * arg0) { // TODO Auto-generated method stub con = arg0[0].getContext();
 * getPage(url); return t_res; }
 * 
 * @Override protected void onPostExecute(ArrayList<String> result) { // TODO
 * Auto-generated method stub System.out.println("not a mistake of con\n");
 * setDisplay(); System.out.println("ListView Done!!!!");
 * System.out.println("in on postexecute size is:" + t_res.size()); if
 * (t_res.size() == 0) { String[] noResult = new String[1]; noResult[0] =
 * "No volumes in the selected cluster"; ArrayAdapter<String> stringAdapter =
 * new ArrayAdapter<String>( con, R.layout.list_view_style, noResult);
 * lists.setAdapter(stringAdapter); return; } try { names = new
 * String[t_res.size()]; t_res.toArray(names); ArrayAdapter<String>
 * stringAdapter = new ArrayAdapter<String>( con,
 * android.R.layout.simple_list_item_1, names); lists.setAdapter(stringAdapter);
 * lists.setOnItemClickListener(new OnItemClickListener() {
 * 
 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
 * long arg3) { try { String[] options = new String[v.get((int) arg3)
 * .getO().getOptions().size()];
 * 
 * // TODO Auto-generated method stub for (int i = 0; i < v.get((int)
 * arg3).getO() .getOptions().size(); i++) { options[i] = v.get((int)
 * arg3).getO() .getOptions().get(i).getName(); } Intent nextPage = new
 * Intent(con, BrickOptionActivity.class);
 * nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); Bundle nextPageParams = new
 * Bundle(); nextPageParams.putString("url", url + "/" + v.get((int)
 * arg3).getId()); nextPageParams.putString("currentVolume", v.get((int)
 * arg3).getVolumeName()); nextPageParams.putStringArray("options", options);
 * nextPage.putExtras(nextPageParams); con.startActivity(nextPage);
 * 
 * 
 * } catch (Exception e) { System.out.println(e.toString()); } } });
 * lists.setOnItemLongClickListener(new OnItemLongClickListener() {
 * 
 * @Override public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int
 * arg2, final long arg3) { // TODO Auto-generated method stub PopupMenu p = new
 * PopupMenu(con, lists); p.getMenuInflater().inflate(R.menu.popupmenu,
 * p.getMenu()); p.show(); p.setOnMenuItemClickListener(new
 * OnMenuItemClickListener() {
 * 
 * @Override public boolean onMenuItemClick(MenuItem item) { // TODO
 * Auto-generated method stub switch (item.toString().charAt(0)) { case 'P':
 * Bundle nPParams = new Bundle(); nPParams.putString("url", url + "/" +
 * v.get((int) arg3).getId()); nPParams.putString("title", v.get((int)
 * arg3).getVolumeName()); Intent nextPage = new Intent(con,
 * VolumePropertiesActivity.class); nextPage.addFlags(
 * Intent.FLAG_ACTIVITY_NEW_TASK) .putExtras(nPParams);
 * con.startActivity(nextPage); break; case 'D': System.out.println("Delete");
 * break; } return false; } }); return false; } });
 * System.out.println("no adapter error");
 * 
 * } catch (Exception e) { System.out.println(e.toString()); } // setListView();
 * this.cancel(true); }
 * 
 * private void setDisplay() { // TODO Auto-generated method stub t_res = new
 * ArrayList<String>(); if (v.size() == 0) { return; } for (int i = 0; i <
 * v.size(); i++) { t_res.add(v.get(i).getVolumeName().toString());
 * System.out.println(t_res.get(i).toString()); }
 * System.out.println(t_res.size()); // tv.setText(t_res); }
 * 
 * private void getPage(String urlString) { // TODO Auto-generated method stub
 * ConnectionUtil connUtil = ConnectionUtil.getInstance(); //
 * connUtil.initClient("10.70.1.136", "admin@internal", "redhat");
 * SerializerClass getData = null; HttpGet request = new HttpGet(urlString);
 * System.out.println("get request done!!!!"); HttpResponse response =
 * connUtil.get(request); System.out.println("response      ");
 * System.out.println("connutil no problem!!!!!!!!!!!1"); HttpEntity entity =
 * response.getEntity(); String xmlData = null; try { xmlData =
 * EntityUtils.toString(entity); } catch (ParseException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * System.out.println(xmlData); try { getData = new SerializerClass(xmlData); }
 * catch (Exception e) { System.out.println(e.toString() +
 * "from serializer class"); } try { v = new ArrayList<Volume>(); v =
 * (ArrayList<Volume>) getData.getResults(2);
 * System.out.println("in get page size is : " + v.size()); } catch (Exception
 * e) { System.out.println(e.toString() + "problem!!!!!!!!!!!!!!!!!!!!!!"); } }
 * 
 * }
 * 
 * }
 */