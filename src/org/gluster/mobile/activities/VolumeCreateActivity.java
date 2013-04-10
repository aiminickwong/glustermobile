package org.gluster.mobile.activities;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.Host;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class VolumeCreateActivity extends GlusterActivity<Host> {
	private EditText name;
	private Spinner volume_type;
	private String url;
	private String names;
	private int count;
	private Button addBricks;
	private CheckBox nfs;
	private CheckBox gluster;
	private int access_protocol = 0;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_create);
		init();
		volume_type
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0,
							View itemSelected, int position, long arg3) {
						// TODO Auto-generated method stub
						System.out.println("You selected : "
								+ itemSelected.toString());
						System.out.println("You have selected : " + position);
						if (position > 0) {
							AlertDialog.Builder alert = new AlertDialog.Builder(
									VolumeCreateActivity.this);

							alert.setTitle("Title");
							alert.setMessage("Enter Count");

							// Set an EditText view to get user input
							final EditText input = new EditText(
									VolumeCreateActivity.this);
							alert.setView(input);

							alert.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											count = Integer.parseInt(input
													.getText().toString());
											count = 0;
											// System.out.println(value);
											// Do something with value!
										}
									});

							alert.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											// Canceled.
										}
									});

							alert.show();

						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		addBricks.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle nextPageParams = new Bundle();
				nextPageParams.putInt("count", count);
				nextPageParams.putString("url", url);
				nextPageParams.putString("name", name.getText().toString());
				nextPageParams.putString("volume_type", volume_type
						.getSelectedItem().toString());
				if (gluster.isChecked()) {
					access_protocol += 2;
				}
				if (nfs.isChecked()) {
					access_protocol += 1;
				}
				nextPageParams.putInt("access_protocol", access_protocol);
				nextPageParams.putString("clusterHostUrl", getIntent()
						.getExtras().getString("clusterHostUrl"));
				Intent nextPage = new Intent(VolumeCreateActivity.this,
						BrickAddActivity.class);
				nextPage.putExtras(nextPageParams);
				startActivity(nextPage);
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void after_post(String message) {
		new SetAlertBox(message, VolumeCreateActivity.this, 4).showDialog();
	}

	private void init() {
		name = (EditText) findViewById(R.id.editText1);
		url = getIntent().getExtras().getString("url");
		cancel = (Button) findViewById(R.id.button1);
		names = getIntent().getExtras().getString("name");
		addBricks = (Button) findViewById(R.id.button2);
		gluster = (CheckBox) findViewById(R.id.checkBox1);
		nfs = (CheckBox) findViewById(R.id.checkBox2);
		System.out.println("In Volumecreate" + name);
		String[] volumeTypes = { "DISTRIBUTE", "REPLICATE",
				"DISTRIBUTED REPLICATE", "STRIPE", "DISTRIBUTED STRIPE" };
		volume_type = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				VolumeCreateActivity.this,
				android.R.layout.simple_spinner_dropdown_item, volumeTypes);
		volume_type.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_volume_create, menu);
		return true;
	}

}
