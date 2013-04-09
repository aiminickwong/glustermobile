package org.gluster.mobile.gdisplays;

import java.util.ArrayList;
import java.util.List;

import org.gluster.mobile.model.Brick;
import org.gluster.mobile.model.Cluster;
import org.gluster.mobile.model.GlusterEntity;
import org.gluster.mobile.model.Host;
import org.gluster.mobile.model.Volume;

import android.widget.TextView;

public class SetTextView<G extends GlusterEntity> {
	TextView propsDisplay;
	List<G> objectList;
	int choice;

	public SetTextView(TextView propsDisplay, List<G> objectList, int choice) {
		super();
		this.propsDisplay = propsDisplay;
		this.objectList = (List<G>) ((ArrayList<G>) objectList).clone();
		this.choice = choice;
	}

	public void display(int choice) {
		switch (choice) {
		case 3:
			ArrayList<Cluster> clusterList = new ArrayList<Cluster>(
					(ArrayList<Cluster>) objectList);
			propsDisplay.setText("name : " + clusterList.get(0).getName()
					+ "\n");
			if (clusterList.get(0).getGluster_service()) {
				propsDisplay.setText(propsDisplay.getText() + "\n"
						+ "Gluster service enabled");
			} else {
				propsDisplay.setText(propsDisplay.getText() + "\n"
						+ "Gluster service not enabled");
			}
			if (clusterList.get(0).getVirt_service()) {
				propsDisplay.setText(propsDisplay.getText() + "\n"
						+ "Virt service enabled");
			} else {
				propsDisplay.setText(propsDisplay.getText() + "\n"
						+ "Virt service not enabled");
			}
			// propsDisplay.setText("Abracadabra");
			break;
		case 6:
			ArrayList<Host> hostList = new ArrayList<Host>(
					(ArrayList<Host>) objectList);
			System.out.println("name : " + hostList.get(0).getName() + "\n"
					+ "Address : " + hostList.get(0).getAddress() + "\n"
					+ "Memory : " + hostList.get(0).getMemory() + "\n"
					+ "Port : " + hostList.get(0).getPort() + "\n" + "Type : "
					+ hostList.get(0).getType());

			propsDisplay.setText("name : " + hostList.get(0).getName() + "\n"
					+ "Address : " + hostList.get(0).getAddress() + "\n"
					+ "Memory : " + hostList.get(0).getMemory() + "\n"
					+ "Port : " + hostList.get(0).getPort() + "\n" + "Type : "
					+ hostList.get(0).getType());

			// propsDisplay.setText("Abracadabra");
			break;

		case 7:
			ArrayList<Volume> volumeList = new ArrayList<Volume>(
					(ArrayList<Volume>) objectList);
			System.out.println(volumeList.get(0).getName() + "\n"
					+ volumeList.get(0).getState() + "\n"
					+ volumeList.get(0).getVolume_type() + "\n");

			propsDisplay.setText("name : " + volumeList.get(0).getName() + "\n"
					+ "State : " + volumeList.get(0).getState() + "\n"
					+ "Type : " + volumeList.get(0).getVolume_type() + "\n");
			break;
		case 8:
			ArrayList<Brick> brickList = new ArrayList<Brick>(
					(ArrayList<Brick>) objectList);

			propsDisplay.setText("Name : " + brickList.get(0).getName() + "\n"
					+ "Server id : " + brickList.get(0).getServer_id() + "\n"
					+ "State : " + brickList.get(0).getState() + "\n");
			break;

		}

	}
}