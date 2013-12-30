package org.gluster.mobile.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.gactivity.GlusterActivity;
import org.gluster.mobile.gdisplays.ListDisplay;
import org.gluster.mobile.model.errors.AddError;
import org.gluster.mobile.model.entities.Brick;
import org.gluster.mobile.model.entities.Bricks;
import org.gluster.mobile.model.entities.Host;
import org.gluster.mobile.model.entities.Hosts;
import org.gluster.mobile.model.entities.VolumeCreate;
import org.gluster.mobile.params.AsyncTaskParameter;
import org.gluster.mobile.params.SettingsHandler;
import org.gluster.mobile.web.GlusterHttpGetApi;
import org.gluster.mobile.web.GlusterHttpPostApi;
import org.gluster.mobile.parsers.EntitySerializer;
import org.gluster.mobile.parsers.XmlDeSerializer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;

public class BrickAddActivity extends GlusterActivity<Host> {
	private EditText brickDir;
	private Spinner host;
	private Button add;
	private ArrayList<Brick> bricks;
	private Bricks b;
	private VolumeCreate newVolume;
	private SharedPreferences sharedPreferences;
	private ListView addBricks;
	private List<HashMap<String, String>> listViewElements;
	private String[] server_ids;
	private String[] server_names;
	private PopupMenu pop;
	int[] column_ids = { R.id.glusterElement, R.id.elementProperty1 };
	String[] column_tags = { "bricks", "server" };

	private VolumeCreate getResultObject(String xmlData) {
		VolumeCreate tObj = null;
		XmlDeSerializer<VolumeCreate> get = null;
		try {
			get = new XmlDeSerializer<VolumeCreate>(xmlData);
            tObj = get.deSerialize(VolumeCreate.class);
		} catch (Exception error) {
            error.printStackTrace();
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
					if (server_names[j].equals(host.getSelectedItem().toString())) {
						position = j;
						break;
					}
				}

				volumeBrick.setServer_id(server_ids[position]);
				volumeBrick.setName(brickDir.getText().toString());
				HashMap<String, String> brickMap = new HashMap<String, String>();
				brickMap.put(column_tags[0], host.getSelectedItem().toString());
				brickMap.put(column_tags[1], brickDir.getText().toString());
				listViewElements.add(brickMap);
				new ListDisplay(addBricks, getApplicationContext(),
						listViewElements, column_ids, column_tags).display();
				bricks.add(volumeBrick);

			}
		});
		addBricks
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, final long position) {

						setPopUp((int) position);
						pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem item) {
								switch (item.getItemId()) {
								case 0:
									deleteSelectedBrick((int) position);
									new ListDisplay(addBricks, getApplicationContext(), listViewElements, column_ids, column_tags).display();
									break;
								}
								return false;
							}
						});
						return false;
					}
				});
	}

	private void deleteSelectedBrick(int position) {
		listViewElements.remove(position);
		bricks.remove(position);
	}

	private void setPopUp(int position) {
		pop = new PopupMenu(getApplicationContext(), addBricks);
		pop.getMenu().add(0, 0, 0, "Remove");
		pop.show();
	}

	private void createVolume() {
		String paramPage = getIntent().getExtras().getString("page");

		newVolume = new VolumeCreate();

		newVolume = getResultObject(paramPage);
		b = new Bricks();
		b.setBricks(bricks);
		newVolume.setBricks(b);

        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(BrickAddActivity.this, getIntent().getExtras().getString("url"), AddError.class);
        asyncTaskParameter.setRequestBody(new EntitySerializer().deSerialize(newVolume, "VolumeCreate.class"));
        asyncTaskParameter.setActivity(BrickAddActivity.this);
		new GlusterHttpPostApi<AddError>().execute(asyncTaskParameter);
	}

	private void init() {
		brickDir = (EditText) findViewById(R.id.editText1);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		brickDir.setText(sharedPreferences.getString("brickDir", null));
		add = (Button) findViewById(R.id.button1);
		host = (Spinner) findViewById(R.id.spinner1);
		bricks = new ArrayList<Brick>();
		addBricks = (ListView) findViewById(R.id.listView1);
		listViewElements = new ArrayList<HashMap<String, String>>();

        AsyncTaskParameter asyncTaskParameter = new AsyncTaskParameter(BrickAddActivity.this, getIntent().getExtras().getString("clusterHostUrl"), Hosts.class);
        asyncTaskParameter.setActivity(BrickAddActivity.this);
        new GlusterHttpGetApi<Hosts>().execute(asyncTaskParameter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.brick_add, menu);
		return true;
	}

	@Override
	public void after_get(List<Host> objectList) {
		server_ids = new String[objectList.size()];
		server_names = new String[objectList.size()];
		ArrayList<Host> hostList = new ArrayList<Host>((ArrayList<Host>) objectList);
		for (int i = 0; i < objectList.size(); i++) {
			server_ids[i] = hostList.get(i).getId();
			server_names[i] = hostList.get(i).getName();
		}
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, server_names);
		host.setAdapter(adapter);
	}

	@Override
	public void after_post(String message) {

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Settings:
			new SettingsHandler(BrickAddActivity.this).handle();
			break;
		case R.id.done:
			createVolume();
			break;
		case R.id.cancel:
			finish();
		}
		return true;
	}
}
