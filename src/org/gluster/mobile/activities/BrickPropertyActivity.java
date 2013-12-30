package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetTextView;
import org.gluster.mobile.model.entities.Brick;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.GlusterHttpGetApi;

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

        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(BrickPropertyActivity.this, getIntent().getExtras().getString("url"), Brick.class);
        asyncTaskParameter.setActivity(BrickPropertyActivity.this);
        new GlusterHttpGetApi<Brick>().execute(asyncTaskParameter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
