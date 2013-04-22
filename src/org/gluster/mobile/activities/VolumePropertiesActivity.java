package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.Volume;
import org.gluster.mobile.model.Volumes;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class VolumePropertiesActivity extends GlusterActivity<Volume> {
	static String url;
	static TextView props;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_properties);
		setTitle(getIntent().getExtras().getString("title"));
		props = (TextView) findViewById(R.id.properties);
		url = getIntent().getExtras().getString("url");
		AsyncTaskParameters<Volumes> p = new AsyncTaskParameters<Volumes>();
		p.setClassName(Volume.class);
		p.setChoice(1);
		p.setActivity(VolumePropertiesActivity.this);
		p.setContext(getApplicationContext());
		// p.setContext(this);
		// p.setListOrText(false);
		// p.setPropDisplay(props);
		p.setUrl(url);
		// p.setDisplay(this.props);
		new HttpPageGetter<Volumes, Volume>().execute(p);
	}

	@Override
	public void after_getObject(Volume object) {
		ArrayList<Volume> objectList = new ArrayList<Volume>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(props, objectList, 7).display(7);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(VolumePropertiesActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_volume_properties, menu);
		return true;
	}
}
/*
 * public static class HttpPageGetter extends AsyncTask<AsyncTaskParameters,
 * String[], ArrayList<String>> { // ArrayList<String> t_res; ArrayList<Volume>
 * v; Context con;
 * 
 * @Override protected ArrayList<String> doInBackground(AsyncTaskParameters...
 * arg0) { // TODO Auto-generated method stub con = arg0[0].getContext();
 * getPage(VolumePropertiesActivity.url); return null; }
 * 
 * @Override protected void onPostExecute(ArrayList<String> result) { // TODO
 * Auto-generated method stub VolumePropertiesActivity.props.setText("");
 * VolumePropertiesActivity.props
 * .setText(VolumePropertiesActivity.props.getText() + "Volume Name : " +
 * v.get(0).getVolumeName() + "\n" + "Type : " + v.get(0).getVolume_type() +
 * "\n" + "State : " + v.get(0).getState());
 * 
 * }
 * 
 * private void getPage(String urlString) { // TODO Auto-generated method stub
 * ConnectionUtil connUtil = ConnectionUtil.getInstance(); //
 * connUtil.initClient("10.70.1.136", "admin@internal", "redhat");
 * System.out.println("In Volumeprops" + urlString); SerializerClass getData =
 * null; HttpGet request = new HttpGet(urlString);
 * System.out.println("get request done!!!!"); HttpResponse response =
 * connUtil.get(request); System.out.println("response      ");
 * System.out.println("connutil no problem!!!!!!!!!!!1"); HttpEntity entity =
 * response.getEntity(); String xmlData = null; try { xmlData =
 * EntityUtils.toString(entity); } catch (ParseException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * System.out.println("In Volumeprops"); System.out.println(xmlData); try {
 * getData = new SerializerClass(xmlData); } catch (Exception e) {
 * System.out.println(e.toString() + "from serializer class"); } try {
 * System.out.println("In Volumeprops"); v = (ArrayList<Volume>)
 * getData.getResults(7); System.out.println("in get page size is : " +
 * v.size()); } catch (Exception e) { System.out.println(e.toString() +
 * "problem!!!!!!!!!!!!!!!!!!!!!!"); } }
 * 
 * }
 * 
 * }
 */