package org.gluster.mobile.xml;

import java.io.Reader;
import java.io.StringReader;

import org.gluster.mobile.model.GlusterEntities;
import org.gluster.mobile.model.GlusterEntity;
import org.simpleframework.xml.core.Persister;

public class EntitiesDeSerializer<G extends GlusterEntities> {

	/* snippet */

	/* snippet */
	private String xmlData;
	private int numberElements;

	public int getNumberElements() {
		return numberElements;
	}

	public void setNumberElements(int numberElements) {
		this.numberElements = numberElements;
	}

	public EntitiesDeSerializer(String xmlData) {
		super();
		this.xmlData = xmlData;
	}

	public G getResults(Class<? extends G> className) {
		G ex = null;
		System.out.println("I am in serializer class");
		Persister persister = new Persister();
		Reader xmlStringReader = new StringReader(xmlData);
		try {
			System.out.println(className + "\n" + xmlData + "\n"
					+ xmlStringReader.toString() + "\n");
			// String cName = className + ".class";
			ex = persister.read(className, xmlStringReader, false);
			System.out.println(persister
					.read(className, xmlStringReader, false).getObjects()
					.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("In Serializer class" + e.toString());
		}
		return ex;

	}

	public <E extends GlusterEntity> E getResult(
			Class<? extends GlusterEntity> className) {
		E ex = null;
		System.out.println("I am in serializer class");
		Persister persister = new Persister();
		Reader xmlStringReader = new StringReader(xmlData);
		try {
			System.out.println(className + "\n" + xmlData + "\n"
					+ xmlStringReader.toString() + "\n");
			// String cName = className + ".class";
			ex = (E) persister.read(className, xmlStringReader, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("In Serializer class" + e.toString());
		}

		return ex;

	}
}
