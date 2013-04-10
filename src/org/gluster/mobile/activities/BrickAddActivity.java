package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.model.Access_Protocol;
import org.gluster.mobile.model.AddError;
import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Hosts;
import org.gluster.mobile.model.VolumeCreate;
import org.gluster.mobile.params.AsyncTaskParameters;
import org.gluster.mobile.params.AsyncTaskPostParameters;
import org.gluster.mobile.web.ConnectionUtil;
import org.gluster.mobile.web.HttpPageGetter;
import org.gluster.mobile.web.HttpPostRequests;
import org.gluster.mobile.xml.EntitiesDeSerializer;
import org.gluster.mobile.xml.EntitySerializer;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class BrickAddActivity extends GlusterActivity<Host> {
	private EditText brickDir;
	private Spinner host;
	private Button create;
	private Button add;
	private Button cancel;
	private ArrayList<Brick> bricks;
	private VolumeCreate newVolume;
	ArrayList<Access_Protocol> ap = new ArrayList<Access_Protocol>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brick_add);
		init();
		create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("You clicked on button");
				newVolume.setName(getIntent().getExtras().getString("name"));
				String volume_type = getIntent().getExtras().getString(
						"volume_type");
				if (volume_type.equalsIgnoreCase("DISTRIBUTED REPLICATE")) {
					volume_type = "DISTRIBUTED_REPLICATE";
				} else if (volume_type.equalsIgnoreCase("DISTRIBUTED STRIPE")) {
					volume_type = "DISTRIBUTED_STRIPE";
				}
				newVolume.setVolume_type(volume_type);
				if (newVolume.getVolume_type().contains("REPLICATE")) {
					newVolume.setReplica_count(getIntent().getExtras().getInt(
							"count"));
				} else if (newVolume.getVolume_type().contains("STRIPE")) {
					newVolume.setStripe_count(getIntent().getExtras().getInt(
							"count"));
				}

				setAccessProtocols();
				newVolume.setBricks(bricks);
				newVolume.setAccess_protocols(ap);
				AsyncTaskPostParameters params = new AsyncTaskPostParameters();
				params.setActivity(BrickAddActivity.this);
				params.setContext(getApplicationContext());
				params.setRequestBody(new EntitySerializer().deSerialize(
						newVolume, "VolumeCreate.class"));
				params.setUrl(getIntent().getExtras().getString("url"));
				params.setChoice(3);
				new HttpPostRequests().execute(params);

			}
		});
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Brick volumeBrick = new Brick();
				volumeBrick.setServer_id(host.getSelectedItem().toString());
				volumeBrick.setName(brickDir.getText().toString());
				bricks.add(volumeBrick);
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

	private void setAccessProtocols() {
		int access_protocol = getIntent().getExtras().getInt("access_protocol");
		Access_Protocol a = new Access_Protocol();
		switch (access_protocol) {
		case 0:
			break;
		case 1:
			a.setAccess_protocol("NFS");
			ap.add(a);
			break;
		case 2:
			a.setAccess_protocol("GLUSTER");
			ap.add(a);
			break;
		case 3:
			a.setAccess_protocol("GLUSTER");
			ap.add(a);
			a.setAccess_protocol("NFS");
			ap.add(a);
			break;
		}
	}

	private void init() {
		brickDir = (EditText) findViewById(R.id.editText1);
		create = (Button) findViewById(R.id.button2);
		add = (Button) findViewById(R.id.button1);
		host = (Spinner) findViewById(R.id.spinner1);
		bricks = new ArrayList<Brick>();
		cancel = (Button) findViewById(R.id.button3);
		newVolume = new VolumeCreate();
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
		String[] server_ids = new String[objectList.size()];
		ArrayList<Host> hostList = new ArrayList<Host>(
				(ArrayList<Host>) objectList);
		for (int i = 0; i < objectList.size(); i++) {
			server_ids[i] = hostList.get(i).getId();
		}
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, server_ids);
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

}
