package org.gluster.mobile.activities;

import org.gluster.mobile.gactivity.GlusterActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends GlusterActivity {
	private EditText userName;
	private EditText brickDir;
	private EditText hostName;
	private SharedPreferences loginCred;
	private Button done;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		init();
		done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor prefEditor = loginCred.edit();
				prefEditor.putString("url", hostName.getText().toString());
				prefEditor.putString("userName", userName.getText().toString());
				prefEditor.putString("brickDir", brickDir.getText().toString());
				prefEditor.commit();
				finish();
			}
		});
	}

	private void init() {
		userName = (EditText) findViewById(R.id.autoCompleteTextView1);
		brickDir = (EditText) findViewById(R.id.editText2);
		hostName = (EditText) findViewById(R.id.editText1);
		loginCred = PreferenceManager.getDefaultSharedPreferences(this);
		done = (Button) findViewById(R.id.button1);
		setPage();
	}

	private void setPage() {
		// TODO Auto-generated method stub
		userName.setText(loginCred.getString("userName", ""));
		brickDir.setText(loginCred.getString("brickDir", ""));
		hostName.setText(loginCred.getString("url", ""));
	}

}
