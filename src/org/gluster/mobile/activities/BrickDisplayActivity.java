package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Bricks;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.GlusterHttpGetApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class BrickDisplayActivity extends GlusterActivity<Brick> {
	public static ListView lists;
	public static String url;
	public static String[] names;
	private List<Brick> brickList;
	private PopupMenu p;
	private List<HashMap<String, String>> listViewParams;
	private int[] brick_ids = { R.id.glusterElement, R.id.elementProperty2 };
	private String[] column_tags = { "Directory", "State" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brick_display);
		init();
		lists.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, final long positionOfClick) {
				setPopUp();
				p.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case 0:
							onPropertiesSelected((int) positionOfClick);
							break;
						}
						return false;
					}
				});
				return false;
			}
		});
	}

	private void setPopUp() {
		p = new PopupMenu(getApplicationContext(), lists);
		p.getMenu().add(0, 0, 0, "Properties");
		p.show();

	}

	private void onPropertiesSelected(int positionOfClick) {
		Bundle nPParams = new Bundle();
		nPParams.putString("url", url + "/" + brickList.get((int) positionOfClick).getId());
		nPParams.putString("title", brickList.get((int) positionOfClick).getName());
		Intent nextPage = new Intent(getApplicationContext(), BrickPropertyActivity.class);
		nextPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(nPParams);
		getApplicationContext().startActivity(nextPage);
	}

	private void init() {
		lists = (ListView) findViewById(R.id.listView1);
        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(BrickDisplayActivity.this, getIntent().getExtras().getString("url") + "/bricks", Bricks.class);
        asyncTaskParameter.setActivity(BrickDisplayActivity.this);
		new GlusterHttpGetApi<Bricks>().execute(asyncTaskParameter);
	}

	private List<HashMap<String, String>> setListViewParams() {
		listViewParams = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < brickList.size(); i++) {
			HashMap<String, String> volume = new HashMap<String, String>();
			volume.put(column_tags[0], brickList.get(i).getBrickName());
			volume.put(column_tags[1], brickList.get(i).getState());
			listViewParams.add(volume);
		}
		return listViewParams;
	}

	@Override
	public void after_get(List<Brick> objectList) {
		brickList = objectList;
		new ListDisplay(lists, this, setListViewParams(), brick_ids,column_tags).display();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(BrickDisplayActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_brick_display, menu);
		return true;
	}

}