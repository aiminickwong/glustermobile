package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetListView;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Hosts;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class HostsDisplayActivity extends GlusterActivity<Host> {
	public static String url;
	public static String[] names;
	public static ListView lists;
	public static String clusterId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hosts_display);
		url = "http://" + ConnectionUtil.getInstance().getHost()
				+ "/api/hosts/";
		clusterId = getIntent().getExtras().getString("clusterid");
		lists = (ListView) findViewById(R.id.listView1);
		AsyncTaskParameters<Hosts> atp = new AsyncTaskParameters<Hosts>();
		atp.setClassNames(Hosts.class);
		atp.setChoice(2);
		atp.setUrl(url);
		atp.setContext(getApplicationContext());
		atp.setActivity(HostsDisplayActivity.this);
		// atp.setContext(getApplicationContext());
		// atp.setDisplay(lists);
		// atp.setListOrText(true);
		new HttpPageGetter<Hosts, Host>().execute(atp);
	}

	@Override
	public void after_get(List<Host> objectList) {
		System.out.println(objectList.size());
		new SetListView(getApplicationContext(), lists, objectList, url,
				ConnectionUtil.getInstance().getHost(), 4, null, 2, null)
				.setDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hosts_display, menu);
		return true;
	}
}
/*
 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
 * String[], ArrayList<String>> { ArrayList<String> t_res = new
 * ArrayList<String>(); ArrayList<Host> h; Context con;
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
 * "No hosts in the selected cluster"; ArrayAdapter<String> stringAdapter = new
 * ArrayAdapter<String>( con, R.layout.list_view_style, noResult);
 * lists.setAdapter(stringAdapter); return; } try { names = new
 * String[t_res.size()]; t_res.toArray(names); ArrayAdapter<String>
 * stringAdapter = new ArrayAdapter<String>( con, R.layout.list_view_style,
 * names); lists.setAdapter(stringAdapter); lists.setOnItemLongClickListener(new
 * OnItemLongClickListener() {
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
 * h.get((int) arg3).getHostId()); System.out
 * .println("url passed in hostDisplay" + url + "/" + h.get((int) arg3)
 * .getHostId()); nPParams.putString("title", h.get((int) arg3).getName());
 * Intent nextPage = new Intent(con, HostPropertiesActivity.class);
 * nextPage.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK) .putExtras(nPParams);
 * con.startActivity(nextPage); break; case 'D': System.out.println("Delete");
 * break; } return false; } });
 * 
 * return false; } }); System.out.println("no adapter error");
 * 
 * } catch (Exception e) { System.out.println(e.toString()); } // setListView();
 * this.cancel(true); }
 * 
 * private void setDisplay() { // TODO Auto-generated method stub if (h.size()
 * == 0) { return; } System.out.println("In setDisplay() HostsDisplayActivity" +
 * clusterId); for (int i = 0; i < h.size(); i++) {
 * System.out.println("Current Cluster id:" + h.get(i).getClusterId());
 * System.out.println(t_res.size() + "in setDisplay before if"); if
 * (h.get(i).getClusterId().equals(clusterId)) {
 * System.out.println("In if setDisplay()");
 * t_res.add(h.get(i).getName().toString()); System.out.println("Host " + i +
 * h.get(i).getName()); } } System.out.println(t_res.size()); //
 * tv.setText(t_res); }
 * 
 * private void getPage(String urlString) { // TODO Auto-generated method stub
 * ConnectionUtil connUtil = ConnectionUtil.getInstance(); SerializerClass
 * getData = null; System.out.println("Url String in hostDisplay" + urlString);
 * HttpGet request = new HttpGet(urlString);
 * System.out.println("get request done!!!!"); HttpResponse response =
 * connUtil.get(request); System.out.println("response      ");
 * System.out.println("connutil no problem!!!!!!!!!!!1"); HttpEntity entity =
 * response.getEntity(); String xmlData = null; try { xmlData =
 * EntityUtils.toString(entity); } catch (ParseException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * System.out.println(xmlData); try { getData = new SerializerClass(xmlData); }
 * catch (Exception e) { System.out.println(e.toString() +
 * "from serializer class"); } try { h = new ArrayList<Host>(); h =
 * (ArrayList<Host>) getData.getResults(4);
 * System.out.println("in get page size is : " + h.size()); } catch (Exception
 * e) { System.out.println(e.toString() + "problem!!!!!!!!!!!!!!!!!!!!!!"); } }
 * 
 * }
 * 
 * }
 */