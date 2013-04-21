package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.AddError;
import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Bricks;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Hosts;
import org.gluster.mobile.model.VolumeCreate;
import org.gluster.mobile.model.Volumes;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.params.AsyncTaskPostParameters;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;
import org.gluster.mobile.web.HttpPostRequests;
import org.gluster.mobile.xml.EntitiesDeSerializer;
import org.gluster.mobile.xml.EntitySerializer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class BrickAddActivity extends GlusterActivity<Host> {
	private EditText brickDir;
	private Spinner host;
	private Button add;
	private ArrayList<Brick> bricks;
	private Bricks b;
	private VolumeCreate newVolume;
	private SharedPreferences loginCred;
	private ListView addBricks;
	private ArrayList<String> listViewElements;
	private String[] server_ids;
	private String[] server_names;

	private VolumeCreate getResultObject(String xmlData) {
		VolumeCreate tObj = null;
		EntitiesDeSerializer<Volumes> get = null;
		try {
			get = new EntitiesDeSerializer<Volumes>(xmlData);
		} catch (Exception error) {
			System.out.println(error.toString() + "in HttpPageGetter");
		}
		try {
			tObj = get.<VolumeCreate> getResult(VolumeCreate.class);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString() + "in HttpPageGetter");
		}
		return tObj;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brick_add);
		init();

		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int position = 0;
				Brick volumeBrick = new Brick();
				for (int j = 0; j < server_names.length; j++) {
					if (server_names[j].equals(host.getSelectedItem()
							.toString())) {
						position = j;
						break;
					}
				}

				volumeBrick.setServer_id(server_ids[position]);
				volumeBrick.setName(brickDir.getText().toString());
				listViewElements.add(host.getSelectedItem().toString() + " "
						+ brickDir.getText().toString());
				ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.list_view_style,
						listViewElements);
				addBricks.setAdapter(stringAdapter);
				bricks.add(volumeBrick);
			}
		});
	}

	private void createVolume() {
		String paramPage = getIntent().getExtras().getString("page");

		newVolume = new VolumeCreate();

		newVolume = getResultObject(paramPage);
		newVolume.setBricks(bricks);

		AsyncTaskPostParameters params = new AsyncTaskPostParameters();
		params.setActivity(BrickAddActivity.this);
		params.setContext(getApplicationContext());
		params.setRequestBody(new EntitySerializer().deSerialize(newVolume,
				"VolumeCreate.class"));
		params.setUrl(getIntent().getExtras().getString("url"));
		params.setChoice(3);
		new HttpPostRequests().execute(params);
	}

	private void init() {
		brickDir = (EditText) findViewById(R.id.editText1);
		loginCred = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		brickDir.setText(loginCred.getString("brickDir", null));
		add = (Button) findViewById(R.id.button1);
		host = (Spinner) findViewById(R.id.spinner1);
		bricks = new ArrayList<Brick>();
		addBricks = (ListView) findViewById(R.id.listView1);
		listViewElements = new ArrayList<String>();
		AsyncTaskParameters<Hosts> atp = new AsyncTaskParameters<Hosts>();
		atp.setActivity(BrickAddActivity.this);
		atp.setContext(getApplicationContext());
		atp.setHost(ConnectionUtil.getInstance().getHost());
		atp.setUrl(getIntent().getExtras().getString("clusterHostUrl"));
		atp.setClassNames(Hosts.class);
		atp.setChoice(2);
		// atp.setChoice(4);
		new HttpPageGetter<Hosts, Host>().execute(atp);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.brick_add, menu);
		return true;
	}

	@Override
	public void after_get(List<Host> objectList) {
		server_ids = new String[objectList.size()];
		server_names = new String[objectList.size()];
		ArrayList<Host> hostList = new ArrayList<Host>(
				(ArrayList<Host>) objectList);
		for (int i = 0; i < objectList.size(); i++) {
			server_ids[i] = hostList.get(i).getId();
			server_names[i] = hostList.get(i).getName();
		}
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, server_names);
		host.setAdapter(adapter);

	}

	@Override
	public void after_post(String message) {
		String error;
		if (message.contains("fault")) {
			AddError ae = new EntitiesDeSerializer<AddError>(message)
					.getResults(AddError.class);
			error = ae.getDetail();
			System.out.println(error);
		} else {
			error = "Successful";
		}
		new SetAlertBox(error, BrickAddActivity.this, 1).showDialog();
		// finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.done:
			createVolume();
			break;
		case R.id.cancel:
			finish();
		}
		return true;
	}
}
