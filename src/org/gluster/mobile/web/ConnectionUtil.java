package org.gluster.mobile.web;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class ConnectionUtil {
	private static final ConnectionUtil instance = new ConnectionUtil();
	private AbstractHttpClient client;
	private static String hostp;

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    private int portId;

	private ConnectionUtil() {
		client = null;
	}

	public static ConnectionUtil getInstance() {
		return instance;
	}

	public void initClient(String host, String userName, String password,
			int port) {
		if (client != null) {
			throw new IllegalStateException("Client is already initialized!");
		}
		hostp = host;
		client = new DefaultHttpClient();
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				userName, password);
		portId = port;
		client.getCredentialsProvider().setCredentials(
				new AuthScope(host, port), cred);
	}

	public void clear() {
		client = null;
	}

	public HttpResponse get(HttpGet request) {
		if (client == null) {
			throw new IllegalStateException("Client is already initialized!");
		}

		try {
			return client.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public HttpResponse post(String postString, HttpPost request) {
		try {
			StringEntity se = new StringEntity(postString, HTTP.UTF_8);
			se.setContentType("application/xml");
			request.setEntity(se);
			return client.execute(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("In connection util post request : "
					+ e.toString());
		}

		return null;
	}

	public HttpResponse delete(String requestBody, HttpDelete request) {
		try {
			StringEntity se = new StringEntity(requestBody, HTTP.UTF_8);
			se.setContentType("application/xml");
			return client.execute(request);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public String getHost() {
		return this.hostp + ":" + portId;
	}

	public void setHost(String host) {
		this.hostp = host;
	}
}
