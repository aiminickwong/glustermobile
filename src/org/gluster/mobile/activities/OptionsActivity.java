package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.model.entities.Option;
import org.gluster.mobile.params.SettingsHandler;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class OptionsActivity extends GlusterActivity<Option> {
	private List<HashMap<String, String>> listViewParams;
	private String[] options;
	private ListView lists;
	private int[] column_ids = { R.id.glusterElement };
	private String[] column_tags = { "optionName" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		init();
		new ListDisplay(lists, getApplicationContext(), listViewParams, column_ids, column_tags).display();

	}

	private void init() {
		options = getIntent().getExtras().getStringArray("options");
		setListViewParameter();
		lists = (ListView) findViewById(R.id.listView1);

	}

	private void setListViewParameter() {
		listViewParams = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < options.length; i++) {
			HashMap<String, String> option = new HashMap<String, String>();
			option.put(column_tags[0], options[i]);
			listViewParams.add(option);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(OptionsActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_options, menu);
		return true;
	}

}
