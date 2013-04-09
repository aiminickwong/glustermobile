package org.gluster.mobile.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class BrickOptionActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brick_option);
		String url = getIntent().getExtras().getString("url");
		setTitle("Volume : " + getIntent().getExtras().getString("volumeName"));
		String[] options_name = getIntent().getExtras().getStringArray(
				"options");
		System.out.println("fine here also after collecting bundles!!!");
		TabHost tabHost = getTabHost();
		if (tabHost.equals(null)) {
			System.out.println("Null!!");
		}
		TabSpec bricks = tabHost.newTabSpec("Bricks");
		bricks.setIndicator("Bricks");
		Bundle volumeBrickUrl = new Bundle();
		volumeBrickUrl.putString("url", url);
		System.out.println("");
		Intent volumesIntent = new Intent(BrickOptionActivity.this,
				BrickDisplayActivity.class);
		volumesIntent.putExtras(volumeBrickUrl);
		bricks.setContent(volumesIntent);

		TabSpec options = tabHost.newTabSpec("Options");
		options.setIndicator("Options");
		Bundle volumeOptions = new Bundle();

		volumeOptions.putStringArray("options", options_name);
		Intent volumeOptionIntent = new Intent(BrickOptionActivity.this,
				OptionsActivity.class);
		volumeOptionIntent.putExtras(volumeOptions);
		options.setContent(volumeOptionIntent);

		tabHost.addTab(bricks);
		tabHost.addTab(options);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_brick_option, menu);
		return true;
	}

}
