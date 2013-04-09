package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetListView;
import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Bricks;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class BrickDisplayActivity extends GlusterActivity<Brick> {
	public static ListView lists;
	public static String url;
	public static String[] names;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brick_display);
		// url =
		// "http://10.70.1.136/api/clusters/d0d8b6f8-8001-11e2-81ba-14feb5be1dfc/glustervolumes/5dc92482-c945-4b19-ad5e-78223d1ede41/bricks";
		url = getIntent().getExtras().getString("url") + "/bricks";
		System.out
				.println(url
						+ "in brickDisplayActivity onCreate()"
						+ "\n"
						+ "it is supposed to be : http://10.70.1.136/api/clusters/d0d8b6f8-8001-11e2-81ba-14feb5be1dfc/glustervolumes/5dc92482-c945-4b19-ad5e-78223d1ede41/bricks");
		lists = (ListView) findViewById(R.id.listView1);
		AsyncTaskParameters<Bricks> atp = new AsyncTaskParameters<Bricks>();
		atp.setClassNames(Bricks.class);
		atp.setChoice(2);
		atp.setContext(getApplicationContext());
		/*
		 * atp.setContext(getApplicationContext()); atp.setDisplay(lists);
		 * atp.setListOrText(true);
		 */
		atp.setActivity(BrickDisplayActivity.this);
		atp.setUrl(url);
		new HttpPageGetter<Bricks, Brick>().execute(atp);
	}

	@Override
	public void after_get(List<Brick> objectList) {
		System.out.println(objectList.size());
		new SetListView(getApplicationContext(), lists, objectList, url,
				ConnectionUtil.getInstance().getHost(), 5, null, 2, null)
				.setDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_brick_display, menu);
		return true;
	}
}
/*
 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
 * String[], ArrayList<String>> { ArrayList<String> t_res = new
 * ArrayList<String>(); ArrayList<Brick> b; Context con;
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
 * String[t_res.size()]; t_res.toArray(names); System.out.println(names.length +
 * "in onPost size of name"); ArrayAdapter<String> stringAdapter = new
 * ArrayAdapter<String>( con, R.layout.list_view_style, names); if
 * (stringAdapter.equals(null)) { System.out.println("Adapter is null!!!!"); }
 * lists.setAdapter(stringAdapter); System.out.println("no adapter error");
 * 
 * } catch (Exception e) { System.out.println(e.toString()); } // setListView();
 * this.cancel(true); }
 * 
 * private void setDisplay() { // TODO Auto-generated method stub if (b.size()
 * == 0) { return; } //
 * System.out.println("In setDisplay() HostsDisplayActivity" + // clusterId);
 * for (int i = 0; i < b.size(); i++) { //
 * System.out.println("Current Cluster id:"+ // b.get(i).getClusterId());
 * System.out.println(t_res.size() + "in setDisplay before if");
 * System.out.println("In if setDisplay()");
 * t_res.add(b.get(i).getBrick_name().toString()); System.out.println("Host " +
 * i + b.get(i).getBrick_name()); } System.out.println(t_res.size()); //
 * tv.setText(t_res); }
 * 
 * private void getPage(String urlString) { // TODO Auto-generated method stub
 * ConnectionUtil connUtil = ConnectionUtil.getInstance(); // connUtil.clear();
 * // connUtil.initClient("10.70.1.136", "admin@internal", "redhat");
 * SerializerClass getData = null;
 * System.out.println("Url String in hostDisplay" + urlString); HttpGet request
 * = new HttpGet(urlString); System.out.println("get request done!!!!");
 * HttpResponse response = connUtil.get(request);
 * System.out.println("response      ");
 * System.out.println("connutil no problem!!!!!!!!!!!1"); HttpEntity entity =
 * response.getEntity(); String xmlData = null; try { xmlData =
 * EntityUtils.toString(entity); } catch (ParseException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * System.out.println(xmlData); try { getData = new SerializerClass(xmlData); }
 * catch (Exception e) { System.out.println(e.toString() +
 * "from serializer class"); } try { b = new ArrayList<Brick>(); b =
 * (ArrayList<Brick>) getData.getResults(5);
 * System.out.println("in get page size is : " + b.size()); } catch (Exception
 * e) { System.out.println(e.toString() + "problem!!!!!!!!!!!!!!!!!!!!!!"); } }
 * 
 * } }
 */