package org.gluster.mobile.web;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.model.Bricks;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.GlusterEntities;
import org.gluster.mobile.model.GlusterEntity;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.xml.EntitiesDeSerializer;

import android.os.AsyncTask;

public class HttpPageGetter<G extends GlusterEntities, E extends GlusterEntity>
		extends AsyncTask<AsyncTaskParameters<G>, String[], Void> {
	private String url;
	private String xmlData = null;
	private GlusterActivity<E> activity;
	private Class<G> classNames;
	private Class<? extends GlusterEntity> className;
	private G object;
	private int choice;
	private E tObj;

	@Override
	protected Void doInBackground(AsyncTaskParameters<G>... arg0) {
		// TODO Auto-generated method stub
		// objectList = arg0[0].getObjectList();
		// choice = arg0[0].getChoice();
		url = arg0[0].getUrl();
		activity = arg0[0].getActivity();
		classNames = arg0[0].getClassNames();
		className = arg0[0].getClassName();
		choice = arg0[0].getChoice();
		getPage(url);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub }
		/*
		 * if (listOrPropdisplay) { getResultObjects(); new SetListView(con,
		 * lists, objectList, url, host, choice) .setDisplay(); } else {
		 * getResultObjects(); new SetTextView(propsDisplay, objectList,
		 * choice).display(choice); }
		 */
		if (choice != 1) {
			getResultObjects();
			activity.after_get((List<E>) object.getObjects());
		} else {
			getResultObject();
			activity.after_getObject(tObj);
		}
		// System.out.println(objectList.size());
		// objectList = (ArrayList<GlusterEntity>) objectList;

	}

	private void getResultObject() {
		EntitiesDeSerializer<G> get = null;
		try {
			get = new EntitiesDeSerializer<G>(xmlData);
		} catch (Exception error) {
			System.out.println(error.toString() + "in HttpPageGetter");
		}
		try {
			tObj = get.<E> getResult(className);
			System.out.println("Error could be in setObject"
					+ tObj.equals(null));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString() + "in HttpPageGetter");
		}

	}

	private void getPage(String urlString) {
		// TODO Auto-generated method stub
		ConnectionUtil connUtil = ConnectionUtil.getInstance();
		System.out.println("Url String is : " + urlString);
		HttpGet request = new HttpGet(urlString);
		HttpResponse response = connUtil.get(request);
		HttpEntity entity = response.getEntity();
		try {
			xmlData = EntityUtils.toString(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(xmlData);

	}

	private void getResultObjects() {
		EntitiesDeSerializer<G> get = null;
		try {
			get = new EntitiesDeSerializer<G>(xmlData);
		} catch (Exception error) {
			System.out.println(error.toString() + "in HttpPageGetter");
		}
		try {
			object = get.getResults(classNames);
			if (classNames.equals(Bricks.class)) {
				System.out.println(object.getObjects().size()
						+ "in HttpPageGetter size");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString() + "in HttpPageGetter");
		}

	}
	/*
	 * private void getResultObjects() { try { getData = new
	 * SerializerClass(xmlData); switch (choice) { case 1: objectList = new
	 * ArrayList<Cluster>(); objectList = (ArrayList<Cluster>)
	 * getData.getResults(choice); break; case 2: objectList = new
	 * ArrayList<Volume>(); objectList = (ArrayList<Volume>)
	 * getData.getResults(choice); break; case 4: objectList = new
	 * ArrayList<Host>(); objectList = (ArrayList<Host>)
	 * getData.getResults(choice); System.out.println("In page getter :  \n" +
	 * objectList.size()); break; case 5: objectList = new ArrayList<Brick>();
	 * objectList = (ArrayList<Brick>) getData.getResults(choice); break; case
	 * 3: objectList = new ArrayList<Brick>(); objectList = (ArrayList<Brick>)
	 * getData.getResults(choice); break; case 7: objectList = new
	 * ArrayList<Volume>(); objectList = (ArrayList<Volume>)
	 * getData.getResults(choice); break; case 6: objectList = new
	 * ArrayList<Host>(); objectList = (ArrayList<Host>)
	 * getData.getResults(choice); break; case 8: objectList = new
	 * ArrayList<Brick>(); objectList = (ArrayList<Brick>)
	 * getData.getResults(choice); break; } } catch (Exception e) {
	 * System.out.println(e.toString() + "from serializer class"); } return; }
	 */
}