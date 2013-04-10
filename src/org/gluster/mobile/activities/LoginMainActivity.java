package org.gluster.mobile.activities;

import org.gluster.mobile.gdisplays.SetAlertBox;
import org.gluster.mobile.params.ParametersToHttpPageGetter;
import org.gluster.mobile.web.LoginAuthentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginMainActivity extends Activity {
	private EditText userName;
	private EditText password;
	private EditText hostname;
	private Button sign_in;
	private String sUser;
	private String sPassword;
	private String sHostName;
	private SharedPreferences loginCred;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_main);
		init();
		setEntries();
		setLogin();
		sign_in.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				setEntries();
				System.out.println(sHostName + "\n" + sPassword + "\n" + sUser);
				ParametersToHttpPageGetter params = new ParametersToHttpPageGetter(
						sHostName, sPassword, sUser);
				String error = check_fields();
				if (error.length() > 1) {
					new SetAlertBox(error
							+ "Click Ok to get back to Login screen",
							LoginMainActivity.this, 2).showDialog();

				} else {
					try {
						new LoginAuthentication(LoginMainActivity.this)
								.execute(params);
						// startActivity(intent);
					} catch (Exception e) {
						setErrorMessage();
						// finish();
					}
				}

			}
		});

	}

	public String check_fields() {
		String error = "";
		if (userName.getText().toString().length() <= 1) {
			error = "UserName Should Not Be Blank\n";
		}
		if (!userName.getText().toString().contains("@")) {
			error += "Invalid UserName\n";
		}
		if (password.getText().toString().length() <= 1) {
			error += "Password Should Not Be Blank\n";
		}
		if ((hostname).getText().toString().length() <= 1) {
			error += "HostName Should Not Be Blank\n";
		}
		return error;
	}

	public void afterLoginPageChange(int id) {
		Bundle nextPageParams = new Bundle();
		nextPageParams.putString("url", sHostName);
		SharedPreferences.Editor prefEditor = loginCred.edit();
		prefEditor.putString("url", sHostName);
		prefEditor.putString("password", sPassword);
		prefEditor.putString("userName", sUser);
		prefEditor.commit();
		Intent nextPage = new Intent(LoginMainActivity.this,
				ClusterDisplayActivity.class);
		nextPage.putExtras(nextPageParams);
		startActivityForResult(nextPage, 0);
	}

	private void setPage() {
		System.out.println("\n" + loginCred.getString("userName", null) + "\n"
				+ loginCred.getString("password", null) + "\n"
				+ loginCred.getString("url", null) + "\n");
		userName.setText(loginCred.getString("userName", null));
		password.setText(loginCred.getString("password", null));
		hostname.setText(loginCred.getString("url", null));
	}

	private void setLogin() {
		setPage();
	}

	public void setErrorMessage() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				LoginMainActivity.this);
		alertDialogBuilder.setTitle("Invalid Credentials");
		alertDialogBuilder
				.setMessage(
						"Click Ok to get back to Login screen or Close to exit the application")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				})
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								LoginMainActivity.this.finish();
							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void setEntries() {
		sUser = getsUser();
		sPassword = getsPassword();
		sHostName = getsHostName();
	}

	private void init() {
		userName = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.password);
		hostname = (EditText) findViewById(R.id.hostname);
		sign_in = (Button) findViewById(R.id.sign_in_button);
		loginCred = PreferenceManager.getDefaultSharedPreferences(this);
	}

	public String getsUser() {
		return userName.getText().toString();
	}

	public String getsPassword() {
		return password.getText().toString();
	}

	public String getsHostName() {
		return hostname.getText().toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.a, menu);
		return true;
	}

}
