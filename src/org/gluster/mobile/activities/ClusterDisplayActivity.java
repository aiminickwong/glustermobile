package org.gluster.mobile.activities;

import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetListView;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.Clusters;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class ClusterDisplayActivity extends GlusterActivity<Cluster> {
	public static String[] names;
	public static String uuu = "11111111111111111111111111";
	static ListView lists;

	// private ArrayList<?> objectList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cluster_display);
		lists = (ListView) findViewById(R.id.listView1);
		uuu = ConnectionUtil.getInstance().getHost();
		AsyncTaskParameters<Clusters> atp = new AsyncTaskParameters<Clusters>();
		atp.setClassNames(Clusters.class);
		atp.setChoice(2);
		atp.setUrl("http://" + uuu + "/api/clusters/");
		atp.setActivity(ClusterDisplayActivity.this);
		atp.setHost(ConnectionUtil.getInstance().getHost());
		atp.setContext(ClusterDisplayActivity.this);
		new HttpPageGetter<Clusters, Cluster>().execute(atp);
	}

	@Override
	public void after_get(List<Cluster> objectList) {
		System.out.println(objectList.size());
		System.out.println("Host : " + ConnectionUtil.getInstance().getHost());
		new SetListView(getApplicationContext(), lists, objectList, "http://"
				+ uuu + "/api/clusters/", uuu, 1,
				(Button) findViewById(R.id.button1), 1, null).setDisplay();

	}

	/*
	 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
	 * String[], List<String>> { ArrayList<Cluster> c; List<String> t_res = new
	 * ArrayList<String>(); Context con; List<String> t_links = new
	 * ArrayList<String>(); String xmlData = null;
	 * 
	 * @Override protected List<String> doInBackground(AsyncTaskParameters...
	 * arg0) { // TODO Auto-generated method stub con = arg0[0].getContext();
	 * getPage("http://" + uuu + "/api/clusters"); return t_res; }
	 * 
	 * @Override protected void onPostExecute(List<String> result) { // TODO
	 * Auto-generated method stub setDisplay();
	 * System.out.println("ListView Done!!!!");
	 * System.out.println("in on postexecute size is:" + t_res.size()); try {
	 * names = new String[t_res.size()]; t_res.toArray(names);
	 * ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>( con,
	 * R.layout.list_view_style, names); lists.setAdapter(stringAdapter);
	 * lists.setOnItemSelectedListener(new OnItemSelectedListener() {
	 * 
	 * @Override public void onItemSelected(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { // TODO Auto-generated method stub
	 * System.out.println(arg3 + "clicked"); Bundle p = new Bundle();
	 * 
	 * }
	 * 
	 * @Override public void onNothingSelected(AdapterView<?> arg0) { // TODO
	 * Auto-generated method stub System.out.println("clicked"); try {
	 * con.startActivity(new Intent(con, VolumeDisplayActivity.class)); } catch
	 * (Exception e) { System.out.println(e.toString()); }
	 * 
	 * }
	 * 
	 * }); lists.setOnItemClickListener(new OnItemClickListener() { public void
	 * onItemClick(AdapterView<?> parent, View view, int position, long i) {
	 * System.out.println(i + "clicked"); Bundle p = new Bundle(); System.out
	 * .println("list t_list length in item click listener is:" +
	 * t_links.size()); p.putString("url", "http://" + uuu); // +
	 * "/api/clusters/"+ t_links.get((int) i); p.putString("clusterName",
	 * c.get((int) i).getName()); p.putString("id", t_links.get((int) i));
	 * con.startActivity(new Intent(con, VolumeHostsActivity.class).addFlags(
	 * Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(p)); } });
	 * lists.setOnItemLongClickListener(new OnItemLongClickListener() { int
	 * positionOfClick;
	 * 
	 * @Override public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	 * int arg2, long arg3) { // TODO Auto-generated method stub positionOfClick
	 * = (int) arg3; PopupMenu p = new PopupMenu(con, lists);
	 * p.getMenuInflater().inflate(R.menu.popupmenu, p.getMenu()); p.show();
	 * p.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	 * 
	 * @Override public boolean onMenuItemClick(MenuItem arg0) { // TODO
	 * Auto-generated method stub switch (arg0.toString().charAt(0)) { case 'P':
	 * Bundle nPParams = new Bundle(); nPParams.putString( "url", "http://" +
	 * ClusterDisplayActivity.uuu + "/api/clusters/" + t_links
	 * .get(positionOfClick)); Intent nextPage = new Intent(con,
	 * ClusterPropertiesActivity.class); nextPage.addFlags(
	 * Intent.FLAG_ACTIVITY_NEW_TASK) .putExtras(nPParams);
	 * con.startActivity(nextPage); break; case 'D':
	 * System.out.println("Delete"); break; } return false; } }); return false;
	 * }
	 * 
	 * }); System.out.println("no adapter error");
	 * 
	 * } catch (Exception e) { System.out.println(e.toString()); } //
	 * setListView(); this.cancel(true); }
	 * 
	 * private void setDisplay() { // TODO Auto-generated method stub
	 * System.out.println("In setDisplay"); System.out.println(c.size()); for
	 * (int i = 0; i < c.size(); i++) {
	 * t_res.add(c.get(i).getName().toString());
	 * t_links.add(c.get(i).getClusterId()); } System.out.println(t_res.size());
	 * // tv.setText(t_res); }
	 * 
	 * private void getPage(String urlString) { // TODO Auto-generated method
	 * stub ConnectionUtil connUtil = ConnectionUtil.getInstance(); //
	 * connUtil.initClient("10.70.1.136", "admin@internal", "redhat");
	 * 
	 * HttpGet request = new HttpGet(urlString);
	 * System.out.println("get request done!!!!"); HttpResponse response =
	 * connUtil.get(request); System.out.println("response      ");
	 * System.out.println("connutil no problem!!!!!!!!!!!1"); HttpEntity entity
	 * = response.getEntity();
	 * 
	 * try { xmlData = EntityUtils.toString(entity); } catch (ParseException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } System.out.println(xmlData); SerializerClass getData = new
	 * SerializerClass(xmlData); c = (ArrayList<Cluster>) getData.getResults(1);
	 * System.out.println(c.size() + "in http page getter");
	 * 
	 * }
	 * 
	 * }
	 * 
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.activity_cluster_display, menu); return
	 * true; }
	 */
}
