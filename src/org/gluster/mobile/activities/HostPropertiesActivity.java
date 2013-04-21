package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Hosts;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class HostPropertiesActivity extends GlusterActivity<Host> {
	static String url;
	static TextView hostProperties;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_properties);
		setTitle("Host : " + getIntent().getExtras().getString("title"));
		url = getIntent().getExtras().getString("url");
		System.out.println("In hostProps : " + url);
		hostProperties = (TextView) findViewById(R.id.hostProps);
		AsyncTaskParameters<Hosts> atp = new AsyncTaskParameters<Hosts>();
		atp.setClassName(Host.class);
		atp.setChoice(1);
		atp.setUrl(url);
		atp.setContext(getApplicationContext());
		atp.setActivity(HostPropertiesActivity.this);
		// atp.setPropDisplay(hostProperties);
		// atp.setContext(getApplicationContext());
		new HttpPageGetter<Hosts, Host>().execute(atp);
	}

	public void after_get(ArrayList<?> objectList) {
		System.out.println(objectList.size());
		new SetTextView(hostProperties, objectList, 6).display(6);
	}

	@Override
	public void after_getObject(Host object) {
		ArrayList<Host> objectList = new ArrayList<Host>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(hostProperties, objectList, 6).display(6);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_host_properties, menu);
		return true;
	}
}
/*
 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
 * String[], ArrayList<String>> { ArrayList<Host> h; Context con;
 * 
 * @Override protected ArrayList<String> doInBackground(AsyncTaskParameters...
 * arg0) { // TODO Auto-generated method stub con = arg0[0].getContext();
 * getPage(HostPropertiesActivity.url); return null; }
 * 
 * @Override protected void onPostExecute(ArrayList<String> result) { // TODO
 * Auto-generated method stub HostPropertiesActivity.hostProperties.setText("");
 * HostPropertiesActivity.hostProperties .setText("Name : " + h.get(0).getName()
 * + "\n" + "Address : " + h.get(0).getAddress() + "\n" + " : " +
 * h.get(0).getMemory() + "\n" + "Port : " + h.get(0).getPort() + "\n" +
 * "Type: " + h.get(0).getType()); }
 * 
 * private void getPage(String urlString) { // TODO Auto-generated method stub
 * ConnectionUtil connUtil = ConnectionUtil.getInstance(); //
 * connUtil.initClient("10.70.1.136", "admin@internal", "redhat");
 * SerializerClass getData = null; System.out.println("In host Props : " +
 * urlString); HttpGet request = new HttpGet(urlString);
 * System.out.println("get request done!!!!"); HttpResponse response =
 * connUtil.get(request); System.out.println("response      ");
 * System.out.println("connutil no problem!!!!!!!!!!!1"); HttpEntity entity =
 * response.getEntity(); String xmlData = null; try { xmlData =
 * EntityUtils.toString(entity); } catch (ParseException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * System.out.println("In hostProps : \n" + xmlData); try { getData = new
 * SerializerClass(xmlData); } catch (Exception e) {
 * System.out.println(e.toString() + "from serializer class"); } try { h =
 * (ArrayList<Host>) getData.getResults(6);
 * System.out.println("in get page size is : " + h.get(0).getMemory()); } catch
 * (Exception e) { System.out.println(e.toString() +
 * "problem!!!!!!!!!!!!!!!!!!!!!!"); } }
 * 
 * } }
 */