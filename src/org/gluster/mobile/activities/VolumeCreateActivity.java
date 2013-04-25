package org.gluster.mobile.activities;

import java.util.ArrayList;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Option;
import org.gluster.mobile.model.Options;
import org.gluster.mobile.model.VolumeCreate;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.xml.EntitySerializer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class VolumeCreateActivity extends GlusterActivity<Host> {
	private EditText name;
	private Spinner volume_type;
	private String url;
	private String names;
	private int count;
	private CheckBox nfs;
	private CheckBox gluster;
	private CheckBox smb;
	private EditText access_permissions;

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
											// count = 0;
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

	}

	@Override
	public void after_post(String message) {
		new SetAlertBox(message, VolumeCreateActivity.this, 4,
				VolumeCreateActivity.this).showDialog();
	}

	private void init() {
		name = (EditText) findViewById(R.id.editText1);
		access_permissions = (EditText) findViewById(R.id.editText2);
		access_permissions.setText("*");
		url = getIntent().getExtras().getString("url");
		// cancel = (Button) findViewById(R.id.button1);
		setNames(getIntent().getExtras().getString("name"));
		// addBricks = (Button) findViewById(R.id.button2);
		gluster = (CheckBox) findViewById(R.id.checkBox1);
		gluster.setChecked(true);
		gluster.setClickable(false);
		nfs = (CheckBox) findViewById(R.id.checkBox2);
		smb = (CheckBox) findViewById(R.id.checkBox3);
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

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(VolumeCreateActivity.this).handle();
			break;
		case R.id.Next:
			nextAddBricks();
			break;
		case R.id.cancel:
			finish();
			break;

		}
		return true;
	}

	private void nextAddBricks() {
		// TODO Auto-generated method stub
		VolumeCreate newVolume = new VolumeCreate();
		Option option;
		Options options = new Options();
		ArrayList<Option> optionList = new ArrayList<Option>();
		String permissions = access_permissions.getText().toString();
		String[] permissionList = permissions.split(",");
		for (int i = 0; i < permissionList.length; i++) {
			option = new Option();
			option.setName("auth.allow");
			option.setValue(permissionList[i]);
			optionList.add(option);
		}

		if (smb.isChecked()) {
			option = new Option();
			option.setName("user.cifs");
			option.setValue("true");
			optionList.add(option);
		} else if (!smb.isChecked()) {
			option = new Option();
			option.setName("user.cifs");
			option.setValue("true");
			optionList.add(option);
		}
		if (nfs.isChecked()) {
			option = new Option();
			option.setName("nfs.disable");
			option.setValue("off");
			optionList.add(option);
		} else if (!nfs.isChecked()) {
			option = new Option();
			option.setName("nfs.disable");
			option.setValue("on");
			optionList.add(option);
		}
		options.setOptions(optionList);
		newVolume.setOptionList(options);
		Bundle nextPageParams = new Bundle();
		newVolume.setName(name.getText().toString());
		String volume_typeS = volume_type.getSelectedItem().toString();
		if (volume_typeS.equalsIgnoreCase("DISTRIBUTED REPLICATE")) {
			volume_typeS = "DISTRIBUTED_REPLICATE";
		} else if (volume_typeS.equalsIgnoreCase("DISTRIBUTED STRIPE")) {
			volume_typeS = "DISTRIBUTED_STRIPE";
		}
		newVolume.setVolume_type(volume_typeS);
		if (volume_type.getSelectedItem().toString().contains("REPLICATE")) {
			newVolume.setReplica_count(count);
		} else if (volume_type.getSelectedItem().toString().contains("STRIPE")) {
			newVolume.setStripe_count(count);
		}
		count = 0;
		// setAccessProtocols();

		// newVolume.setAccess_protocols((ArrayList<Access_Protocol>)
		// ap.getAp());
		String page = new EntitySerializer().deSerialize(newVolume,
				"VolumeCreate.class");
		nextPageParams.putString("url", url);
		nextPageParams.putString("page", page);
		nextPageParams.putString("clusterHostUrl", getIntent().getExtras()
				.getString("clusterHostUrl"));
		Intent nextPage = new Intent(VolumeCreateActivity.this,
				BrickAddActivity.class);
		nextPage.putExtras(nextPageParams);
		startActivity(nextPage);

	}
}
