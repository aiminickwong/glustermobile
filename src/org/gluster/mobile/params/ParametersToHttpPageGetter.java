package org.gluster.mobile.params;


public class ParametersToHttpPageGetter {
	// class to kind of interface b/w ui and HttpPageGetter
	String url;
	String host;
	String userName;
	String password;
	int choice;

	public ParametersToHttpPageGetter(String host, String pass, String uName) {
		this.host = host;
		this.password = pass;
		this.url = "http://" + host + "/api";
		this.userName = uName;
		this.choice = 1;
	}

	public ParametersToHttpPageGetter(String host, String pass, String uName,
			int choice) {
		this.host = host;
		this.password = pass;
		this.userName = uName;
		this.setURL(choice, this);
	}

	public ParametersToHttpPageGetter setURL(int choice,
			ParametersToHttpPageGetter object) {
		switch (choice) {
		case 1:
			object.url = "http://" + host + "/api/clusters";
			this.choice = 1;
			break;
		case 2:
			object.url = "http://" + host + "/api/hosts";
			this.choice = 2;
			break;
		case 3:
			object.url = "http://" + host + "/api/bricks";
			this.choice = 3;
			break;
		case 4:
			object.url = "http://" + host + "/api/options";
			this.choice = 4;
			break;
		}
		return object;
	}

	public String getUrl(ParametersToHttpPageGetter object) {
		return object.url;
	}

	public String getPassword(ParametersToHttpPageGetter object) {
		return object.password;
	}

	public String getUserName(ParametersToHttpPageGetter object) {
		return object.userName;
	}

	public String getHost(ParametersToHttpPageGetter object) {
		return object.host;
	}

	public int getChoice(ParametersToHttpPageGetter object) {
		return object.choice;
	}
}
