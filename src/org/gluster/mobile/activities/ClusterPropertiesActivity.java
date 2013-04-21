package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.Clusters;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ClusterPropertiesActivity extends GlusterActivity<Cluster> {
	public static TextView display;
	public static String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cluster_properties);
		setTitle(getIntent().getExtras().getString("title"));
		url = getIntent().getExtras().getString("url");
		System.out.println(url);
		display = (TextView) findViewById(R.id.textView1);
		AsyncTaskParameters<Clusters> atp = new AsyncTaskParameters<Clusters>();
		atp.setClassName(Cluster.class);
		atp.setChoice(1);
		atp.setContext(getApplicationContext());
		atp.setActivity(ClusterPropertiesActivity.this);
		// atp.setPropDisplay(display);
		// atp.setListOrText(false);
		atp.setUrl(url);
		// atp.setContext(getApplicationContext());
		new HttpPageGetter<Clusters, Cluster>().execute(atp);
	}

	@Override
	public void after_getObject(Cluster object) {
		ArrayList<Cluster> objectList = new ArrayList<Cluster>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(display, objectList, 3).display(3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cluster_properties, menu);
		return true;
	}
}
/*
 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
 * String[], ArrayList<String>> { ArrayList<String> t_res; ArrayList<Cluster> c;
 * Context con;
 * 
 * @Override protected ArrayList<String> doInBackground(AsyncTaskParameters...
 * arg0) { // TODO Auto-generated method stub con = arg0[0].getContext();
 * getPage(ClusterPropertiesActivity.url); return t_res; }
 * 
 * @Override protected void onPostExecute(ArrayList<String> result) { // TODO
 * Auto-generated method stub try {
 * ClusterPropertiesActivity.display.setText("Name : " + c.get(0).getName() +
 * "\n"); if (c.get(0).getGluster_service()) { ClusterPropertiesActivity.display
 * .setText(ClusterPropertiesActivity.display .getText() + "\n" + "Virt enabled"
 * + "\n"); } else { ClusterPropertiesActivity.display
 * .setText(ClusterPropertiesActivity.display .getText() + "\n" +
 * "Virt disabled" + "\n"); } if (c.get(0).getVirt_service()) {
 * ClusterPropertiesActivity.display .setText(ClusterPropertiesActivity.display
 * .getText() + "Gluster Service enabled" + "\n"); } else {
 * ClusterPropertiesActivity.display .setText(ClusterPropertiesActivity.display
 * .getText() + "Gluster Service disabled" + "\n"); }
 * 
 * } catch (Exception e) { System.out.println(e.toString()); } // setListView();
 * this.cancel(true); }
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
 * "from serializer class"); } try { c = (ArrayList<Cluster>)
 * getData.getResults(3); System.out.println("in get page size is : " +
 * c.size()); } catch (Exception e) { System.out.println(e.toString() +
 * "problem!!!!!!!!!!!!!!!!!!!!!!"); } }
 * 
 * } }
 */