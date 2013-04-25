package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Bricks;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.HttpPageGetter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BrickPropertyActivity extends GlusterActivity<Brick> {
	TextView propDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brick_property);
		setTitle(getIntent().getExtras().getString("title"));
		propDisplay = (TextView) findViewById(R.id.brickProps);
		String url = getIntent().getExtras().getString("url");
		AsyncTaskParameters<Bricks> atp = new AsyncTaskParameters<Bricks>();
		// atp.setContext(getApplicationContext());
		atp.setClassName(Brick.class);
		atp.setUrl(url);
		atp.setContext(getApplicationContext());
		atp.setChoice(1);
		atp.setActivity(BrickPropertyActivity.this);
		// atp.setListOrText(false);
		// atp.setPropDisplay(propDisplay);
		new HttpPageGetter<Bricks, Brick>().execute(atp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_brick_property, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(BrickPropertyActivity.this).handle();
			break;
		}
		return true;
	}

	@Override
	public void after_getObject(Brick object) {
		ArrayList<Brick> objectList = new ArrayList<Brick>(1);
		objectList.add(object);
		System.out.println(objectList.size());
		new SetTextView(propDisplay, objectList, 8).display(8);
	}

}
