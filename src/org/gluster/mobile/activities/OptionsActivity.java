package org.gluster.mobile.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		String[] options = getIntent().getExtras().getStringArray("options");
		System.out.println(options.length + "inoptions activity!!!");
		ListView lists = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.list_view_style, options);
		lists.setAdapter(stringAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_options, menu);
		return true;
	}

}
